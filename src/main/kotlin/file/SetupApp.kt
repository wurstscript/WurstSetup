package file

import config.CONFIG_FILE_NAME
import config.ScriptMode
import config.Wc3Patch
import config.WurstProjectConfig
import config.WurstProjectConfigData
import global.InstallationManager
import global.Log
import logging.KotlinLogging
import net.ConnectionManager
import net.NetStatus
import org.eclipse.jgit.api.Git
import java.awt.GraphicsEnvironment
import java.lang.ProcessBuilder.Redirect
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import java.util.jar.JarFile
import javax.swing.JOptionPane


object SetupApp {
	val DEFAULT_DIR: Path = Paths.get(".")
    private val log = KotlinLogging.logger {}
    lateinit var setup: SetupMain

    fun handleArgs(setup: SetupMain) {
        this.setup = setup
        updateGrillJar()
        if (setup.isGUILaunch) {
            val helpText = """
                Grill is now CLI-first. Use the command line to interact with Grill.

                Example commands:
                  grill generate MyProject              Generate a new Wurst project
                  grill generate MyProject --with-ci    Include GitHub Actions workflow
                  grill generate MyProject --script-mode jass --wc3-patch pre1.29
                  grill install                         Install/update project dependencies
                  grill install wurstscript             Install the WurstScript compiler
                  grill build ExampleMap.w3x            Build your project map
                  grill test                            Run project unit tests
                  grill help                            Show all available commands
            """.trimIndent()
            if (GraphicsEnvironment.isHeadless()) {
                log.info(helpText)
            } else {
                JOptionPane.showMessageDialog(null, helpText, "Grill — CLI First", JOptionPane.INFORMATION_MESSAGE)
            }
            ExitHandler.exit(0)
        } else {
            log.info("🔥 Grill warming up..")
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
        log.info("🔥 Ready. Version: <{}>", CompileTimeInfo.version)
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
                log.info("""
                    |Available commands:
                    |  install [dep|wurstscript|grill]  Install/update dependencies, WurstScript compiler, or grill itself
                    |  remove  [dep|wurstscript]        Remove a dependency or uninstall WurstScript
                    |  generate <name>                  Generate a new Wurst project in a subfolder
                    |    --script-mode lua|jass         Script mode (default: lua)
                    |    --wc3-patch reforged|pre1.29   WC3 patch target (default: reforged)
                    |    --with-agents / --no-agents    Include AGENTS.md (default: no)
                    |    --with-ci / --no-ci            Include GitHub Actions workflow (default: no)
                    |  test [filter]                    Run unit tests, optionally filtered by package/function name
                    |  typecheck                        Typecheck the project without building a map
                    |
                    |Global options:
                    |  --quiet                          Suppress wurst output; only print errors and final result
                    |  outdated                         Check whether project dependencies are up to date
                    |  build <mapfile>                  Build the project using the given input map
                """.trimMargin())
            }
			setup.command == CLICommand.INSTALL -> {
                if (setup.commandArg.isBlank()) {
                    if (configData != null) {
                        handleUpdateProject(configData)
                    }
                } else if (setup.commandArg.lowercase() == "wurstscript") {
					handleInstallWurst()
				} else if (setup.commandArg.lowercase() == "grill") {
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
				if (setup.commandArg.lowercase() == "wurstscript") {
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
                    runWizard(setup)
                    val projectDir = DEFAULT_DIR.resolve(setup.commandArg)
                    val stdlibUrl = if (setup.wc3Patch == Wc3Patch.PRE_129)
                        "https://github.com/wurstscript/wurstStdlib2:pre1.29"
                    else
                        "https://github.com/wurstscript/wurstStdlib2"
                    val projectConfig = WurstProjectConfigData(
                        projectName = setup.commandArg,
                        dependencies = ArrayList(mutableListOf(stdlibUrl)),
                        scriptMode = setup.scriptMode,
                        wc3Patch = setup.wc3Patch
                    )
                    WurstProjectConfig.handleCreate(projectDir, null, projectConfig)
                    if (Files.exists(projectDir)) {
                        if (setup.addAgents) downloadAgentsMd(projectDir)
                        if (setup.addGithubWorkflow) writeCiWorkflow(projectDir)
                    }
				}
			}
            setup.command == CLICommand.TEST -> {
                log.info("⚗️ Testing project..")
                if (!setup.quiet) log.info("💡 Tip: run with --quiet to suppress compiler output and only show errors (useful for AI agents).")
                if (InstallationManager.status != InstallationManager.InstallationStatus.NOT_INSTALLED && configData != null) {
                    testProject(configData)
                }
            }
            setup.command == CLICommand.TYPECHECK -> {
                log.info("🔍 Typechecking project..")
                if (!setup.quiet) log.info("💡 Tip: run with --quiet to suppress compiler output and only show errors (useful for AI agents).")
                if (InstallationManager.status != InstallationManager.InstallationStatus.NOT_INSTALLED && configData != null) {
                    typecheckProject(configData)
                }
            }
            setup.command == CLICommand.OUTDATED -> {
                if (configData == null) {
                    log.error("No wurst.build configuration found.")
                    ExitHandler.exit(1)
                }
                checkProjectOutdated(configData)
            }
            setup.command == CLICommand.BUILD -> {
                log.info("🔨 Building project..")
                val mapArg = if (setup.commandArg.isBlank()) {
                    val maps = Files.list(setup.projectRoot).use { stream ->
                        stream.filter { p -> p.fileName.toString().let { it.endsWith(".w3x") || it.endsWith(".w3m") } }.toList()
                    }
                    when (maps.size) {
                        0 -> { log.error("\t❌ No input map specified and no .w3x/.w3m found in project root."); null }
                        1 -> { log.info("\t📦 Auto-detected map: ${maps[0].fileName}"); maps[0].fileName.toString() }
                        else -> { log.error("\t❌ Multiple maps found (${maps.joinToString { it.fileName.toString() }}), please specify one."); null }
                    }
                } else setup.commandArg
                if (mapArg != null) {
                    if (!Files.exists(setup.projectRoot.resolve(mapArg))) {
                        log.error("\t❌ Input map cannot be found at project root.")
                    } else if (InstallationManager.status != InstallationManager.InstallationStatus.NOT_INSTALLED && configData != null) {
                        setup.commandArg = mapArg
                        buildProject(configData)
                    }
                }
            }
            setup.command == CLICommand.SELF_UPDATE -> {
                log.info("🔄 Updating..")
                try {
                    log.info("✔ Updated succeeded.")
	                    InstallationManager.ensureGrillJarInstalled()
                    ExitHandler.exit(0)
                } catch(e: Exception) {
                    log.error("Grill update failed. Original files might still be in use.")
                }
            }
		}

	}

    private fun runWizard(setup: SetupMain) {
        val console = System.console() ?: return  // non-interactive: use flags/defaults as-is
        fun prompt(message: String, default: String): String {
            console.writer().print("$message [$default]: ")
            console.writer().flush()
            val input = console.readLine()?.trim() ?: ""
            return input.ifEmpty { default }
        }
        val scriptModeDefault = setup.scriptMode.name.lowercase()
        val scriptModeInput = prompt("Script mode (lua/jass)", scriptModeDefault)
        setup.scriptMode = if (scriptModeInput.lowercase() == "jass") ScriptMode.JASS else ScriptMode.LUA

        val patchDefault = if (setup.wc3Patch == Wc3Patch.PRE_129) "pre1.29" else "reforged"
        val patchInput = prompt("WC3 patch target (reforged/pre1.29)", patchDefault)
        setup.wc3Patch = if (patchInput.lowercase() == "pre1.29") Wc3Patch.PRE_129 else Wc3Patch.REFORGED

        val agentsDefault = if (setup.addAgents) "Y" else "N"
        val agentsInput = prompt("Add AGENTS.md?", agentsDefault)
        setup.addAgents = agentsInput.lowercase() == "y"

        val ciDefault = if (setup.addGithubWorkflow) "Y" else "N"
        val ciInput = prompt("Add GitHub Actions CI?", ciDefault)
        setup.addGithubWorkflow = ciInput.lowercase() == "y"
    }

    private fun downloadAgentsMd(projectDir: Path) {
        try {
            val content = URL("https://raw.githubusercontent.com/wurstscript/WurstSetup/master/templates/AGENTS.md").readText()
            Files.write(projectDir.resolve("AGENTS.md"), content.toByteArray())
            log.info("✔ AGENTS.md written.")
        } catch (e: Exception) {
            log.warn("⚠️ Could not download AGENTS.md: ${e.message}. Continuing without it.")
        }
    }

    fun writeCiWorkflow(projectDir: Path) {
        val workflowDir = projectDir.resolve(".github/workflows")
        Files.createDirectories(workflowDir)
        Files.write(workflowDir.resolve("grill.yml"), CI_WORKFLOW.toByteArray())
        log.info("✔ GitHub Actions workflow written.")
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
            0 -> { log.info("🗺️ Map has been built!"); ExitHandler.exit(0) }
            else -> {
                log.info("❌ There was an issue with the wurst build process.")
                ExitHandler.exit(1)
            }
        }
    }

    private fun testProject(configData: WurstProjectConfigData) {
        val args = commonArgs(configData)

        args.add("-runtests")
        if (setup.commandArg.isNotBlank()) {
            args.add("-testFilter")
            args.add(setup.commandArg.removeSuffix(".wurst"))
        }

        val result = startWurstProcess(args)
        when (result) {
            0 -> { log.info("✔ All tests succeeded."); ExitHandler.exit(0) }
            else -> {
                log.info("❌ Tests did not execute successfully.")
                ExitHandler.exit(1)
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
            0 -> { log.info("✔ Typecheck succeeded."); ExitHandler.exit(0) }
            else -> {
                log.info("❌ Typecheck failed.")
                ExitHandler.exit(1)
            }
        }
    }

    private fun checkProjectOutdated(configData: WurstProjectConfigData) {
        val outdated = DependencyManager.hasOutdatedDependencies(setup.projectRoot, configData)
        if (outdated) {
            log.info("Project dependencies are outdated. Run `grill install`.")
            ExitHandler.exit(1)
        }
        log.info("Project dependencies are up to date.")
    }

    private fun startWurstProcess(args: ArrayList<String>): Int {
        val pb = ProcessBuilder(args)
        if (setup.quiet) {
            pb.redirectErrorStream(true)
            val p = pb.start()
            val output = p.inputStream.bufferedReader().readLines()
            val exitCode = p.waitFor()
            val errorLines = output.filter { line ->
                line.contains("error", ignoreCase = true) ||
                line.contains("Error", ignoreCase = true) ||
                line.contains("warning", ignoreCase = true) ||
                line.contains("FAILED", ignoreCase = true) ||
                line.contains("Exception", ignoreCase = true)
            }
            errorLines.forEach { println(it) }
            return exitCode
        }
        pb.redirectOutput(Redirect.INHERIT)
        pb.redirectError(Redirect.INHERIT)
        val p = pb.start()
        return p.waitFor()
    }

	    private fun commonArgs(configData: WurstProjectConfigData): ArrayList<String> {
	        val args = ArrayList(InstallationManager.compilerLaunchCommand().toList())

        if (configData.scriptMode == ScriptMode.LUA) {
            args.add("-lua")
        }

        val buildFolder = setup.projectRoot.resolve("_build")
        val jassdoc = buildFolder.resolve("dependencies").resolve("jassdoc")
        if (Files.exists(jassdoc)) {
            for (f in jassdoc.toFile().listFiles()!!) {
                if (f.name.endsWith(".j") && !f.name.startsWith("builtin-types")) {
                    args.add(f.absolutePath.toString())
                }
            }
	        } else {
	            val common = resolveCoreJassFile(buildFolder, "common.j")
	            val blizzard = resolveCoreJassFile(buildFolder, "blizzard.j")
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

    private fun resolveCoreJassFile(buildFolder: Path, fileName: String): Path {
        val projectCopy = buildFolder.resolve(fileName)
        if (Files.exists(projectCopy)) {
            return projectCopy
        }

        return extractCoreJassFile(buildFolder, fileName)
    }

    private fun extractCoreJassFile(buildFolder: Path, fileName: String): Path {
        val target = buildFolder.resolve("grill").resolve(fileName)
        if (Files.exists(target)) {
            return target
        }

        try {
            Files.createDirectories(target.parent)
            JarFile(InstallationManager.getCompilerPath()).use { jar ->
                val entry = jar.getEntry(fileName) ?: return target
                jar.getInputStream(entry).use { input ->
                    Files.copy(input, target)
                }
            }
        } catch (e: Exception) {
            log.warn("Could not extract $fileName from compiler jar: ${e.message}")
        }
        return target
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

    val REPO_REGEX = Regex("(https?://)([\\w.@-]+)(/)([\\w,-_]+)/([\\w,-_]+)(.git)?((/)?)")

	private fun handleInstallDep(configData: WurstProjectConfigData) {
        val resolvedName = DependencyManager.resolveName(setup.commandArg)
        if (!REPO_REGEX.matches(resolvedName.first)) {
            log.info("<${setup.commandArg}> does not appear to be a supported git repo link (e.g. https://github.com/user/repo). SSH repo URLs are not bundled in the slim CLI.")
            ExitHandler.exit(1)
        }
		log.info("🔹 Installing ${resolvedName.second}")
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
		log.info("🌭 Installing WurstScript..")
		if (InstallationManager.status != InstallationManager.InstallationStatus.INSTALLED_UPTODATE) {
			log.info("\tUpdate available!")
			if (setup.requireConfirmation) {
                log.info("Do you want to update your wurst installation? (y/n)")
                val sc = Scanner(System.`in`)
                val line = sc.nextLine()
                if (line == "y") {
                    InstallationManager.handleUpdate()
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

    private val CI_WORKFLOW = """
        name: Grill CI

        on:
          push:
            branches:
              - master
              - main
          pull_request:

        jobs:
          grill:
            runs-on: ubuntu-latest
            container:
              image: frotty/wurstscript

            steps:
              - name: Check out repository
                uses: actions/checkout@v4

              - name: Install project dependencies
                run: grill install

              - name: Build map
                run: grill build ExampleMap.w3x
    """.trimIndent()
}
