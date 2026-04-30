package file

import config.CONFIG_FILE_NAME
import config.WurstProjectConfig
import config.WurstProjectConfigData
import global.InstallationManager
import global.Log
import mu.KotlinLogging
import net.ConnectionManager
import net.NetStatus
import org.eclipse.jgit.api.Git
import ui.UiManager
import ui.UpdateFoundDialog
import java.awt.GraphicsEnvironment
import java.lang.ProcessBuilder.Redirect
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import kotlin.system.exitProcess


object SetupApp {
	val DEFAULT_DIR: Path = Paths.get(".")
    private val log = KotlinLogging.logger {}
    lateinit var setup: SetupMain

    fun handleArgs(setup: SetupMain) {
        this.setup = setup
        updateGrillJar()
        if (setup.isGUILaunch) {
            log.info("\uD83D\uDDBC No arguments found. Launching Wurst Setup GUI..")
            if (GraphicsEnvironment.isHeadless()) {
                log.error("\uD83D\uDD25 Error: Can't run GUI in headless environment!")
                exitProcess(1)
            }
            UiManager.initUI()
        } else {
            log.info("\uD83D\uDD25 Grill warming up..")
            handleCMD()
        }
    }

	    private fun handleCMD() {
			ConnectionManager.checkConnectivity("http://google.com")
			ConnectionManager.checkWurstBuild()
			InstallationManager.verifyInstallation()
	        if (ConnectionManager.netStatus == NetStatus.ONLINE) {
	            val latestSetupBuild = ConnectionManager.getLatestSetupBuild()
	            val jenkinsBuildVer = InstallationManager.getJenkinsBuildVer(CompileTimeInfo.version)
	            if (latestSetupBuild > 0) {
	                log.debug("current setup ver: $jenkinsBuildVer latest Setup: $latestSetupBuild")
	            }
	        }
        log.info("\uD83D\uDD25 Ready. Version: <{}>", CompileTimeInfo.version)
		handleRunArgs()
    }

	private fun handleRunArgs() {
		log.debug("handle runargs")
		val configFile = setup.projectRoot.resolve(CONFIG_FILE_NAME)
		var configData: WurstProjectConfigData? = null
		if (Files.exists(configFile)) {
			configData = WurstProjectConfig.loadProject(configFile)!!
		} else {
			log.warn("⚠️ No wurst.build configuration file at current location.")
		}

		when {
            setup.command == CLICommand.HELP -> {
                log.info("Use one of the following commands: ${CLICommand.values().joinToString(", ")}")
            }
			setup.command == CLICommand.INSTALL -> {
                if (setup.commandArg.isBlank()) {
                    if (configData != null) {
                        handleUpdateProject(configData)
                    }
                } else if (setup.commandArg.toLowerCase() == "wurstscript") {
					handleInstallWurst()
				} else if (setup.commandArg.toLowerCase() == "grill") {
                    handleUpdateGrill()
                } else {
					if (configData != null) {
						handleInstallDep(configData)
						WurstProjectConfig.saveProjectConfig(setup.projectRoot, configData)
                        handleUpdateProject(configData)
					}
				}
			}
			setup.command == CLICommand.REMOVE -> {
				if (setup.commandArg.toLowerCase() == "wurstscript") {
					handleRemoveWurst()
				} else {
					if (configData != null) {
						handleRemoveDep(configData)
						WurstProjectConfig.saveProjectConfig(setup.projectRoot, configData)
					}
				}
			}
			setup.command == CLICommand.GENERATE -> {
                log.info("✈ Generating project..")
				if (configData == null) {
					WurstProjectConfig.handleCreate(DEFAULT_DIR.resolve(setup.commandArg), null,
                        WurstProjectConfigData(setup.commandArg, ArrayList(mutableListOf("https://github.com/wurstscript/wurstStdlib2"))))
				}
			}
            setup.command == CLICommand.TEST -> {
                log.info("⚗️ Testing project..")
                if (InstallationManager.status != InstallationManager.InstallationStatus.NOT_INSTALLED && configData != null) {
                    testProject(configData)
                }
            }
            setup.command == CLICommand.TYPECHECK -> {
                log.info("Typechecking project..")
                if (InstallationManager.status != InstallationManager.InstallationStatus.NOT_INSTALLED && configData != null) {
                    typecheckProject(configData)
                }
            }
            setup.command == CLICommand.OUTDATED -> {
                if (configData == null) {
                    log.error("No wurst.build configuration found.")
                    exitProcess(1)
                }
                checkProjectOutdated(configData)
            }
            setup.command == CLICommand.BUILD -> {
                log.info("\uD83D\uDD28 Building project..")
                if (setup.commandArg.isBlank()) {
                    log.error("\t❌ No input map specified.")
                } else if (!Files.exists(setup.projectRoot.resolve(setup.commandArg))) {
                    log.error("\t❌ Input map cannot be found at project root.")
                } else {
                    if (InstallationManager.status != InstallationManager.InstallationStatus.NOT_INSTALLED && configData != null) {
                        buildProject(configData)
                    }
                }
            }
            setup.command == CLICommand.SELF_UPDATE -> {
                log.info("\uD83D\uDD04 Updating..")
                try {
                    log.info("✔ Updated succeeded.")
	                    InstallationManager.ensureGrillJarInstalled()
                    exitProcess(0)
                } catch(e: Exception) {
                    log.error("Grill update failed. Original files might still be in use.")
                }
            }
		}

	}

	    private fun handleUpdateGrill() {
	        InstallationManager.ensureGrillJarInstalled()
	        log.info("Grill was refreshed from the running binary.")
	    }

    private fun buildProject(configData: WurstProjectConfigData) {
        val args = commonArgs(configData)

        args.add("-build")

        if (setup.measure) {
            args.add("-measure")
        }

        args.add("-workspaceroot")
        args.add(setup.projectRoot.toAbsolutePath().toString())

        args.add("-inputmap")
        args.add(setup.projectRoot.resolve(setup.commandArg).toAbsolutePath().toString())

        val result = startWurstProcess(args)
        when (result) {
            0 -> log.info("\uD83D\uDDFA️ Map has been built!")
            else -> {
                log.info("❌ There was an issue with the wurst build process.")
                exitProcess(1)
            }
        }
    }

    private fun testProject(configData: WurstProjectConfigData) {
        val args = commonArgs(configData)

        args.add("-runtests")

        val result = startWurstProcess(args)
        when (result) {
            0 -> log.info("✔ All tests succeeded.")
            else -> {
                log.info("❌ Tests did not execute successfully.")
                exitProcess(1)
            }
        }
    }

    private fun typecheckProject(configData: WurstProjectConfigData) {
        val args = commonArgs(configData)

        if (setup.measure) {
            args.add("-measure")
        }

        val result = startWurstProcess(args)
        when (result) {
            0 -> log.info("Typecheck succeeded.")
            else -> {
                log.info("Typecheck failed.")
                exitProcess(1)
            }
        }
    }

    private fun checkProjectOutdated(configData: WurstProjectConfigData) {
        val outdated = DependencyManager.hasOutdatedDependencies(setup.projectRoot, configData)
        if (outdated) {
            log.info("Project dependencies are outdated. Run `grill install`.")
            exitProcess(1)
        }
        log.info("Project dependencies are up to date.")
    }

    private fun startWurstProcess(args: ArrayList<String>): Int {
        val pb = ProcessBuilder(args)
        pb.redirectOutput(Redirect.INHERIT)
        pb.redirectError(Redirect.INHERIT)
        val p = pb.start()
        return p.waitFor()
    }

	    private fun commonArgs(configData: WurstProjectConfigData): ArrayList<String> {
	        val args = ArrayList(InstallationManager.compilerLaunchCommand().toList())

        val buildFolder = setup.projectRoot.resolve("_build")
        val jassdoc = buildFolder.resolve("dependencies").resolve("jassdoc")
        if (Files.exists(jassdoc)) {
            for (f in jassdoc.toFile().listFiles()!!) {
                if (f.name.endsWith(".j") && !f.name.startsWith("builtin-types")) {
                    args.add(f.absolutePath.toString())
                }
            }
	        } else {
	            val common = if (Files.exists(buildFolder.resolve("common.j"))) {
	                buildFolder.resolve("common.j")
	            } else {
	                InstallationManager.installDir.resolve("common.j")
            }
	            val blizzard = if (Files.exists(buildFolder.resolve("blizzard.j"))) {
	                buildFolder.resolve("blizzard.j")
	            } else {
	                InstallationManager.installDir.resolve("blizzard.j")
	            }
	            if (Files.exists(common)) {
	                args.add(common.toAbsolutePath().toString())
	            }
	            if (Files.exists(blizzard)) {
	                args.add(blizzard.toAbsolutePath().toString())
	            }
	        }

        args.add(setup.projectRoot.resolve("wurst").toAbsolutePath().toString())
        args.add("-runcompiletimefunctions")
        if (setup.noPJass) {
            args.add("-noPJass")
        }

        configData.dependencies.stream().forEach {
            args.add("-lib")
            val (_, dependencyName, _) = DependencyManager.resolveName(it)
            args.add(buildFolder.resolve("dependencies").resolve(dependencyName).toAbsolutePath().toString())
        }
        return args
    }

    private fun handleRemoveDep(configData: WurstProjectConfigData) {
		log.error("removing ${setup.commandArg}")
		if (configData.dependencies.contains(setup.commandArg)) {
			configData.dependencies.remove(setup.commandArg)
		} else {
			log.error("dependency does not exist in project")
		}
	}

	private fun handleRemoveWurst() {
		if (!setup.requireConfirmation) {
			InstallationManager.handleRemove()
		}
	}

	private fun handleUpdateProject(configData: WurstProjectConfigData) {
		WurstProjectConfig.handleUpdate(setup.projectRoot, null, configData)
	}

    val REPO_REGEX = Regex("((git@|http(s)?://)([\\w.@]+)([/:]))([\\w,\\-,_]+)/([\\w,\\-,_]+)(.git)?((/)?)")

	private fun handleInstallDep(configData: WurstProjectConfigData) {
        val resolvedName = DependencyManager.resolveName(setup.commandArg)
        if (!REPO_REGEX.matches(resolvedName.first)) {
            log.info("<${setup.commandArg}> does not appear to be a valid git repo link (e.g. https://github.com/user/repo)")
            exitProcess(1)
        }
		log.info("\uD83D\uDD39 Installing ${resolvedName.second}")
		if (configData.dependencies.contains(setup.commandArg)) {
			log.info("Dependency is already installed.")
			return
		}
		try {
			val result = Git.lsRemoteRepository()
				.setRemote(resolvedName.first)
				.call()
			if (!result.isEmpty()) {
				Log.print("valid!\n")
				configData.dependencies.add(setup.commandArg)
			} else {
				log.error("Entered invalid git repo.")
			}
		} catch (e: Exception) {
			log.error("Entered invalid git repo.")
			e.printStackTrace()
		}
	}

	private fun handleInstallWurst() {
		log.info("\uD83C\uDF2D Installing WurstScript..")
		if (InstallationManager.status != InstallationManager.InstallationStatus.INSTALLED_UPTODATE) {
			log.info("\tUpdate available!")
			if (setup.requireConfirmation) {
				if (setup.isGUILaunch) {
					UpdateFoundDialog("A Wurst compiler update has been found!")
				} else {
					log.info("Do you want to update your wurst installation? (y/n)")
					val sc = Scanner(System.`in`)
					val line = sc.nextLine()
					if (line == "y") {
						InstallationManager.handleUpdate()
					}
				}
			} else {
				InstallationManager.handleUpdate()
			}
		} else {
			log.info("Already up to date.")
		}
	}

	    private fun updateGrillJar() {
	        val url = InstallationManager::class.java.protectionDomain.codeSource.location
	        val ownFile = Paths.get(url.toURI())
	        log.debug("path: $url")
	        log.debug("file: " + ownFile.toAbsolutePath())
	        if (Files.exists(ownFile) && ownFile.toString().endsWith(".jar")) {
	            InstallationManager.ensureGrillJarInstalled()
	        }
	    }
}
