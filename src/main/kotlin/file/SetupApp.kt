package file

import config.CONFIG_FILE_NAME
import config.WurstProjectConfig
import config.WurstProjectConfigData
import global.InstallationManager
import global.Log
import mu.KotlinLogging
import net.ConnectionManager
import org.eclipse.jgit.api.Git
import ui.UiManager
import ui.UpdateFoundDialog
import java.awt.GraphicsEnvironment
import java.lang.ProcessBuilder.Redirect
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*
import kotlin.system.exitProcess


object SetupApp {
	val DEFAULT_DIR: Path = Paths.get(".")
    private val log = KotlinLogging.logger {}
    lateinit var setup: SetupMain

    fun handleArgs(setup: SetupMain) {
        this.setup = setup
        copyJar()
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
				} else {
					if (configData != null) {
						handleInstallDep(configData)
						WurstProjectConfig.saveProjectConfig(setup.projectRoot, configData)
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
					WurstProjectConfig.handleCreate(DEFAULT_DIR.resolve(setup.commandArg), null, WurstProjectConfigData())
				}
			}
            setup.command == CLICommand.TEST -> {
                log.info("⚗️ Testing project..")
                if (InstallationManager.status != InstallationManager.InstallationStatus.NOT_INSTALLED && configData != null) {
                    testProject(configData)
                }
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
		}

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

    private fun startWurstProcess(args: ArrayList<String>): Int {
        val pb = ProcessBuilder(args)
        pb.redirectOutput(Redirect.INHERIT)
        pb.redirectError(Redirect.INHERIT)
        val p = pb.start()
        return p.waitFor()
    }

    private fun commonArgs(configData: WurstProjectConfigData): ArrayList<String> {
        val args = arrayListOf("java", "-jar",
            InstallationManager.installDir.resolve("wurstscript.jar").toAbsolutePath().toString())

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
            args.add(common.toAbsolutePath().toString())
            args.add(blizzard.toAbsolutePath().toString())
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

	private fun handleInstallDep(configData: WurstProjectConfigData) {
		log.info("\uD83D\uDD39 Installing ${setup.commandArg}")
		if (configData.dependencies.contains(setup.commandArg)) {
			log.info("Dependency is already installed.")
			return
		}
		try {
			val result = Git.lsRemoteRepository()
				.setRemote(setup.commandArg)
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

    private fun copyJar() {
        val url = InstallationManager::class.java.protectionDomain.codeSource.location
        val ownFile = Paths.get(url.toURI())
        if (ownFile.endsWith(".2.jar")) {
            log.debug("copy jar from own")
            Files.copy(ownFile, ownFile.resolveSibling("WurstSetup.jar"), StandardCopyOption.REPLACE_EXISTING)
        }
        log.debug("path: $url")
        log.debug("file: " + ownFile.toAbsolutePath())
        if (ownFile != null && Files.exists(ownFile) && ownFile.toString().endsWith(".jar") &&
                (ownFile.parent == null || ownFile.parent?.fileName?.toString() != ".wurst")) {
            log.debug("copy jar")
            Files.copy(ownFile, Paths.get(InstallationManager.installDir.toString(), "WurstSetup.jar"), StandardCopyOption.REPLACE_EXISTING)
        }
    }
}
