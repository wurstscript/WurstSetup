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
import org.slf4j.LoggerFactory
import org.eclipse.jgit.api.Git
import java.awt.GraphicsEnvironment
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*
import java.util.jar.JarFile
import javax.swing.JOptionPane


object SetupApp {
	val DEFAULT_DIR: Path = Paths.get(".")
    private val log = KotlinLogging.logger {}
    lateinit var setup: SetupMain

    private data class WurstProcessResult(val exitCode: Int, val output: List<String>)

    fun handleArgs(setup: SetupMain) {
        this.setup = setup
        DependencyManager.debug = setup.debug
        configureQuietLogging()
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
            progress("🔥 Grill ${CompileTimeInfo.version}")
            handleCMD()
        }
    }

    private fun configureQuietLogging() {
        val rootLogger = LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME)
        if (rootLogger is ch.qos.logback.classic.Logger) {
            rootLogger.level = if (setup.quiet) ch.qos.logback.classic.Level.ERROR else ch.qos.logback.classic.Level.INFO
        }
    }

    private fun progress(message: String) {
        if (!setup.quiet) {
            log.info(message)
        }
    }

    private fun pass(message: String) {
        if (setup.quiet) {
            println(message)
        } else {
            log.info(message)
        }
    }

    private fun fail(message: String) {
        log.error(message)
    }

    private fun detail(message: String) {
        if (setup.quiet) {
            println(message)
        } else {
            log.info(message)
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
		handleRunArgs()
    }

	private fun handleRunArgs() {
		log.debug("handle runargs")
		val configFile = setup.projectRoot.resolve(CONFIG_FILE_NAME)
		var configData: WurstProjectConfigData? = null
		if (Files.exists(configFile)) {
			configData = WurstProjectConfig.loadProject(configFile)!!
		}

		when {
            setup.command == CLICommand.HELP -> {
                log.info("""
                    |Common:
                    |  grill generate MyProject
                    |  grill install
                    |  grill test
                    |  grill build ExampleMap.w3x
                    |
                    |Project commands:
                    |  install [dep|wurstscript|grill]  Install/update dependencies, WurstScript compiler, or Grill itself
                    |  remove  [dep|wurstscript]        Remove a dependency or uninstall WurstScript
                    |  generate <name>                  Generate a new Wurst project in a subfolder
                    |  test [filter]                    Run unit tests, optionally filtered by package/function name
                    |  typecheck                        Typecheck the project without building a map
                    |  outdated                         Check whether project dependencies are up to date
                    |  build <mapfile>                  Build the project using the given input map
                    |
                    |Global options:
                    |  --quiet                          Suppress wurst output; only print errors and final result
                    |  --debug                          Print full stack traces for troubleshooting
                    |
                    |Generate options:
                    |  --script-mode lua|jass           Script mode (default: lua)
                    |  --wc3-patch reforged|pre1.29     WC3 patch target (default: reforged)
                    |  --with-agents / --no-agents      Include AGENTS.md (default: no)
                    |  --with-ci / --no-ci              Include GitHub Actions workflow (default: no)
                """.trimMargin())
            }
			setup.command == CLICommand.INSTALL -> {
                if (setup.commandArg.isBlank()) {
                    if (configData != null) {
                        handleUpdateProject(configData)
                    } else {
                        missingProject()
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
					} else {
                        missingProject()
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
					} else {
                        missingProject()
					}
				}
			}
			setup.command == CLICommand.GENERATE -> {
                if (!prepareGenerate(setup)) {
                    return
                }
                log.info("✈ Generating project...")
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
                    printGenerateNextSteps(projectDir)
                }
			}
            setup.command == CLICommand.TEST -> {
                progress("⚗️ Running tests...")
                if (InstallationManager.status != InstallationManager.InstallationStatus.NOT_INSTALLED && configData != null) {
                    testProject(configData)
                } else if (configData == null) {
                    missingProject()
                }
            }
            setup.command == CLICommand.TYPECHECK -> {
                progress("🔍 Typechecking project...")
                if (InstallationManager.status != InstallationManager.InstallationStatus.NOT_INSTALLED && configData != null) {
                    typecheckProject(configData)
                } else if (configData == null) {
                    missingProject()
                }
            }
            setup.command == CLICommand.OUTDATED -> {
                if (configData == null) {
                    missingProject()
                }
                checkProjectOutdated(configData)
            }
            setup.command == CLICommand.BUILD -> {
                progress("🔨 Building project...")
                val mapArg = if (setup.commandArg.isBlank()) {
                    val maps = Files.list(setup.projectRoot).use { stream ->
                        stream.filter { p -> p.fileName.toString().let { it.endsWith(".w3x") || it.endsWith(".w3m") } }.toList()
                    }
                    when (maps.size) {
                        0 -> { missingMap(); null }
                        1 -> { log.info("📦 Auto-detected map: ${maps[0].fileName}"); maps[0].fileName.toString() }
                        else -> { multipleMaps(maps); null }
                    }
                } else setup.commandArg
                if (mapArg != null) {
                    if (!Files.exists(setup.projectRoot.resolve(mapArg))) {
                        missingMap(mapArg)
                    } else if (InstallationManager.status != InstallationManager.InstallationStatus.NOT_INSTALLED && configData != null) {
                        setup.commandArg = mapArg
                        buildProject(configData)
                    } else if (configData == null) {
                        missingProject()
                    }
                }
            }
            setup.command == CLICommand.SELF_UPDATE -> {
                log.info("🔄 Updating...")
                try {
                    log.info("✅ Update succeeded.")
	                    InstallationManager.ensureGrillJarInstalled()
                    ExitHandler.exit(0)
                } catch(e: Exception) {
                    log.error("Grill update failed. Original files might still be in use.")
                }
            }
		}

	}

    private fun missingProject(): Nothing {
        log.error("❌ This folder is not a Grill project.")
        log.info("Expected: ${setup.projectRoot.resolve(CONFIG_FILE_NAME).toAbsolutePath()}")
        log.info("Try: run `grill generate MyProject` to create a new project, or pass `-projectDir <path>`.")
        ExitHandler.exit(1)
    }

    private fun missingMap(requestedMap: String? = null): Nothing {
        if (requestedMap == null) {
            log.error("❌ No input map specified and no .w3x/.w3m file was found in the project root.")
            log.info("Try: put a map in the project root, or run `grill build YourMap.w3x`.")
        } else {
            log.error("❌ Map not found: $requestedMap")
            log.info("Expected: ${setup.projectRoot.resolve(requestedMap).toAbsolutePath()}")
            val maps = findMaps()
            if (maps.isNotEmpty()) {
                log.info("Available maps: ${maps.joinToString { it.fileName.toString() }}")
            }
        }
        ExitHandler.exit(1)
    }

    private fun multipleMaps(maps: List<Path>): Nothing {
        log.error("❌ Multiple maps found: ${maps.joinToString { it.fileName.toString() }}")
        log.info("Try: grill build ${maps.first().fileName}")
        ExitHandler.exit(1)
    }

    private fun findMaps(): List<Path> {
        return Files.list(setup.projectRoot).use { stream ->
            stream.filter { p -> p.fileName.toString().let { it.endsWith(".w3x") || it.endsWith(".w3m") } }.toList()
        }
    }

    private fun printGenerateNextSteps(projectDir: Path) {
        log.info("""
            |✅ Created ${projectDir.fileName}
            |
            |Next:
            |  cd ${projectDir.fileName}
            |  grill test
            |  grill build ExampleMap.w3x
        """.trimMargin())
    }

    private fun printCompilerFailure(commandName: String, result: WurstProcessResult) {
        if (printPjassFailure(result.output)) {
            return
        }
        fail("❌ Wurst $commandName failed.")
        detail("Exit code: ${result.exitCode}")
        if (setup.quiet) {
            detail("Next: rerun without `--quiet` only for the failed file/test.")
        } else {
            detail("Try: rerun with `--quiet` for a shorter error log, or `--debug` for troubleshooting details.")
        }
    }

    private fun printPjassFailure(output: List<String>): Boolean {
        val text = output.joinToString("\n")
        val isPjassFailure = text.contains("Pjass execution error", true) ||
            (text.contains("Cannot run program", true) && text.contains("pjass", true))
        if (!isPjassFailure) {
            return false
        }

        val tried = Regex("Cannot run program \"([^\"]+)\"").find(text)?.groupValues?.get(1)
        val reason = output.firstOrNull {
            it.contains("Permission denied", true) ||
                it.contains("posix_spawn failed", true) ||
                it.contains("Cannot run program", true)
        }?.trim()

        fail("❌ PJass failed to run.")
        if (tried != null) {
            detail("Tried: $tried")
        }
        if (reason != null) {
            detail("Reason: $reason")
        }
        detail("Try: check that the bundled pjass binary is executable and that its temp directory allows execution.")
        detail("Tip: rerun with `--debug` if you need the full Java error.")
        return true
    }

    private fun isImportantCompilerLine(line: String): Boolean {
        return line.contains("error", ignoreCase = true) ||
            line.contains("warning", ignoreCase = true) ||
            line.contains("FAILED", ignoreCase = true) ||
            line.contains("Exception", ignoreCase = true) ||
            line.contains("Pjass", ignoreCase = true)
    }

    internal var generatePrompt: ((String, String?) -> String?)? = null

    internal fun prepareGenerate(setup: SetupMain): Boolean {
        if (setup.commandArg.isNotBlank()) {
            return true
        }

        val prompt = generatePrompt ?: terminalPrompt()

        while (setup.commandArg.isBlank()) {
            val projectName = prompt("Project name", null)?.trim() ?: return false
            if (projectName.isBlank()) {
                log.error("Project name cannot be empty.")
            } else {
                setup.commandArg = projectName
            }
        }

        runWizard(setup, prompt)
        return true
    }

    private fun terminalPrompt(): (String, String?) -> String? {
        val console = System.console()
        if (console == null) {
            return prompt@ { message, default ->
                if (default == null) {
                    print("$message: ")
                } else {
                    print("$message [$default]: ")
                }
                val input = readlnOrNull()?.trim() ?: return@prompt null
                input.ifEmpty { default }
            }
        }

        return { message, default ->
            if (default == null) {
                console.writer().print("$message: ")
            } else {
                console.writer().print("$message [$default]: ")
            }
            console.writer().flush()
            val input = console.readLine()?.trim() ?: ""
            input.ifEmpty { default }
        }
    }

    private fun runWizard(setup: SetupMain, prompt: (String, String?) -> String?) {
        val scriptModeDefault = setup.scriptMode.name.lowercase()
        val scriptModeInput = prompt("Script mode (lua/jass)", scriptModeDefault)
        setup.scriptMode = if (scriptModeInput?.lowercase() == "jass") ScriptMode.JASS else ScriptMode.LUA

        val patchDefault = if (setup.wc3Patch == Wc3Patch.PRE_129) "pre1.29" else "reforged"
        val patchInput = prompt("WC3 patch target (reforged/pre1.29)", patchDefault)
        setup.wc3Patch = if (patchInput?.lowercase() == "pre1.29") Wc3Patch.PRE_129 else Wc3Patch.REFORGED

        val agentsDefault = if (setup.addAgents) "Y" else "N"
        val agentsInput = prompt("Add AGENTS.md?", agentsDefault)
        setup.addAgents = agentsInput?.lowercase() == "y"

        val ciDefault = if (setup.addGithubWorkflow) "Y" else "N"
        val ciInput = prompt("Add GitHub Actions CI?", ciDefault)
        setup.addGithubWorkflow = ciInput?.lowercase() == "y"
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
        when (result.exitCode) {
            0 -> { pass("✅ Map built."); ExitHandler.exit(0) }
            else -> {
                printCompilerFailure("build", result)
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
        when (result.exitCode) {
            0 -> { pass("✅ All tests succeeded."); ExitHandler.exit(0) }
            else -> {
                printCompilerFailure("test", result)
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
        when (result.exitCode) {
            0 -> { pass("✅ Typecheck succeeded."); ExitHandler.exit(0) }
            else -> {
                printCompilerFailure("typecheck", result)
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
        log.info("✅ Project dependencies are up to date.")
    }

    private fun startWurstProcess(args: ArrayList<String>): WurstProcessResult {
        val result = runWurstProcess(args, compactFallback = false)
        if (setup.quiet && result.output.any { it.contains("Unknown option: -compactOutput") }) {
            val fallbackArgs = ArrayList(args)
            fallbackArgs.remove("-compactOutput")
            return runWurstProcess(fallbackArgs, compactFallback = true)
        }
        return result
    }

    private fun runWurstProcess(args: ArrayList<String>, compactFallback: Boolean): WurstProcessResult {
        val pb = ProcessBuilder(args)
        val outputDir = compilerOutputDir()
        Files.createDirectories(outputDir)
        pb.directory(outputDir.toFile())
        pb.redirectErrorStream(true)
        val p = pb.start()
        val output = ArrayList<String>()
        p.inputStream.bufferedReader().forEachLine { line ->
            output.add(line)
            if (!setup.quiet) {
                println(line)
            }
        }
        val exitCode = p.waitFor()
        if (setup.quiet && exitCode != 0) {
            val linesToPrint = if (compactFallback) output.filter(::isImportantCompilerLine) else output
            linesToPrint.forEach { println(it) }
        }
        return WurstProcessResult(exitCode, output)
    }

	    private fun commonArgs(configData: WurstProjectConfigData): ArrayList<String> {
	        val args = ArrayList(InstallationManager.compilerLaunchCommand().toList())

        if (configData.scriptMode == ScriptMode.LUA) {
            args.add("-lua")
        }
        if (setup.quiet) {
            args.add("-compactOutput")
        }

        val buildFolder = setup.projectRoot.resolve("_build")
        val outputDir = compilerOutputDir()
        Files.createDirectories(outputDir)
        args.add("-out")
        args.add(outputDir.resolve(outputFileName(configData)).toAbsolutePath().toString())

        val jassdoc = buildFolder.resolve("dependencies").resolve("jassdoc")
        if (Files.exists(jassdoc)) {
            for (f in jassdoc.toFile().listFiles()!!) {
                if (f.name.endsWith(".j") && !f.name.startsWith("builtin-types")) {
                    args.add(f.absolutePath.toString())
                }
            }
	        } else {
	            val common = resolveCoreJassFile(buildFolder, "common.j", configData.wc3Patch)
	            val blizzard = resolveCoreJassFile(buildFolder, "blizzard.j", configData.wc3Patch)
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

    private fun compilerOutputDir(): Path {
        return setup.projectRoot.resolve("_build").resolve("grill")
    }

    private fun outputFileName(configData: WurstProjectConfigData): String {
        return if (configData.scriptMode == ScriptMode.LUA) "output.lua" else "output.j"
    }

    private fun resolveCoreJassFile(buildFolder: Path, fileName: String, wc3Patch: Wc3Patch?): Path {
        val projectCopy = buildFolder.resolve(fileName)
        if (Files.exists(projectCopy)) {
            return projectCopy
        }

        return extractCoreJassFile(buildFolder, fileName, wc3Patch ?: Wc3Patch.REFORGED)
    }

    private fun extractCoreJassFile(buildFolder: Path, fileName: String, wc3Patch: Wc3Patch): Path {
        val patchFolder = when (wc3Patch) {
            Wc3Patch.PRE_129 -> "pre1.29"
            Wc3Patch.REFORGED -> "reforged"
        }
        val target = buildFolder.resolve("grill").resolve("core-jass").resolve(patchFolder).resolve(fileName)
        if (Files.exists(target)) {
            return target
        }

        try {
            Files.createDirectories(target.parent)
            val resourcePath = "core-jass/$patchFolder/$fileName"
            javaClass.classLoader.getResourceAsStream(resourcePath)?.use { input ->
                Files.copy(input, target, StandardCopyOption.REPLACE_EXISTING)
                return target
            }

            JarFile(InstallationManager.getCompilerPath()).use { jar ->
                val entry = jar.getEntry(fileName) ?: return target
                jar.getInputStream(entry).use { input ->
                    Files.copy(input, target, StandardCopyOption.REPLACE_EXISTING)
                }
            }
        } catch (e: Exception) {
            log.warn("Could not extract $fileName for patch $wc3Patch: ${e.message}")
        }
        return target
    }

	private fun handleRemoveDep(configData: WurstProjectConfigData) {
		log.info("🧹 Removing ${setup.commandArg}")
		if (configData.dependencies.contains(setup.commandArg)) {
			configData.dependencies.remove(setup.commandArg)
            log.info("✅ Dependency removed.")
		} else {
			log.error("❌ Dependency is not listed in wurst.build: ${setup.commandArg}")
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
            log.error("❌ Unsupported dependency URL: ${setup.commandArg}")
            log.info("Accepted forms:")
            log.info("  https://github.com/user/repo")
            log.info("  https://github.com/user/repo:branch")
            log.info("SSH repo URLs are not bundled in the slim CLI.")
            ExitHandler.exit(1)
        }
		log.info("🔹 Installing ${resolvedName.second}")
		if (configData.dependencies.contains(setup.commandArg)) {
			log.info("✅ Dependency is already listed.")
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
				log.error("❌ Could not find repository: ${resolvedName.first}")
                ExitHandler.exit(1)
			}
		} catch (e: Exception) {
			log.error("❌ Could not read repository: ${resolvedName.first}")
            log.info("Reason: ${e.message ?: e.javaClass.simpleName}")
            if (setup.debug) {
                e.printStackTrace()
            } else {
                log.info("Try: check the URL, branch, and your git credentials. Rerun with --debug for details.")
            }
            ExitHandler.exit(1)
		}
	}

	private fun handleInstallWurst() {
		log.info("🌭 Installing WurstScript...")
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
			log.info("✅ Already up to date.")
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
