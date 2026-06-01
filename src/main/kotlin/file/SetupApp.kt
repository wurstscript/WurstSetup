package file

import config.CONFIG_FILE_NAME
import config.ScriptMode
import config.WurstProjectConfig
import config.WurstProjectBuildMapData
import config.WurstProjectConfigData
import config.newProjectConfig
import config.withAddedDependency
import config.withRemovedDependency
import config.withWc3Patch
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
import java.util.*
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
                    |  --wc3-patch <patch>              WC3 patch target: reforged, pre1.29, or jass-history version
                    |  --wc3-path <dir>                 Warcraft III install folder for VS Code/run
                    |  --with-agents / --no-agents      Include AGENTS.md (default: no)
                    |  --with-ci / --no-ci              Include GitHub Actions workflow (default: no)
                """.trimMargin())
            }
			setup.command == CLICommand.INSTALL -> {
                if (setup.commandArg.isBlank()) {
                    if (configData != null) {
                        configData = ensureProjectPatchRecorded(configData)
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
						configData = handleInstallDep(configData)
                        configData = ensureProjectPatchRecorded(configData)
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
						configData = handleRemoveDep(configData)
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
                val projectName = projectDir.fileName?.toString() ?: setup.commandArg
                val stdlibUrl = stdlibDependencyForPatch(setup.wc3Patch)
                val projectConfig = newProjectConfig(
                    projectName = projectName,
                    dependencies = listOf(stdlibUrl),
                    buildMapData = generatedBuildMapData(projectName),
                    scriptMode = setup.scriptMode,
                    wc3Patch = setup.wc3Patch
                )
                val gameRoot = resolveGenerateGamePath(setup, projectConfig.wc3Patch)
                WurstProjectConfig.handleCreate(projectDir, gameRoot, projectConfig)
                ensureCoreJassFiles(projectDir, projectConfig.wc3Patch)
                if (Files.exists(projectDir)) {
                    if (setup.addAgents) downloadAgentsMd(projectDir)
                    if (setup.addGithubWorkflow) writeCiWorkflow(projectDir)
                    printGenerateNextSteps(projectDir, projectConfig, setup.addAgents, setup.addGithubWorkflow, gameRoot)
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

    private fun printGenerateNextSteps(
        projectDir: Path,
        projectConfig: WurstProjectConfigData,
        addAgents: Boolean,
        addGithubWorkflow: Boolean,
        gameRoot: Path?
    ) {
        log.info("""
            |✅ Created ${projectDir.fileName}
            |
            |Choices:
            |  Script mode: ${(projectConfig.scriptMode ?: ScriptMode.LUA).name.lowercase()}
            |  WC3 patch: ${CoreJassProvider.describePatch(projectConfig.wc3Patch ?: CoreJassProvider.DEFAULT_PATCH)}
            |  Warcraft III: ${gameRoot?.toAbsolutePath()?.normalize() ?: "not configured"}
            |  Stdlib: ${if (projectConfig.dependencies.any { it.endsWith(":pre1.29") }) "pre1.29" else "current"}
            |  AGENTS.md: ${yesNo(addAgents)}
            |  GitHub Actions CI: ${yesNo(addGithubWorkflow)}
            |
            |Next:
            |  code ./${projectDir.fileName}
        """.trimMargin())
    }

    private fun resolveGenerateGamePath(setup: SetupMain, wc3Patch: String?): Path? {
        val gameRoot = setup.gamePath ?: Wc3ClientDetector.detectGameRoot()
        val clientInfo = Wc3ClientDetector.inspectGameRoot(gameRoot)
        if (clientInfo == null) {
            if (setup.gamePathExplicit) {
                log.warn("Warcraft III path was set to ${setup.gamePath}, but no supported executable was found there.")
            } else {
                log.info("Warcraft III path: not detected. Set it later in VS Code or rerun generate with --wc3-path <dir>.")
            }
            return null
        }
        log.info("Warcraft III path: ${Wc3ClientDetector.describe(clientInfo)}")
        Wc3ClientDetector.mismatchMessage(wc3Patch, clientInfo)?.let { log.warn(it) }
        return clientInfo.root
    }

    private fun yesNo(value: Boolean): String {
        return if (value) "yes" else "no"
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

    private fun isNoisyCompilerVersionLine(line: String): Boolean {
        val trimmed = line.trim()
        return trimmed == "Warning: Ignoring unknown wc3Patch in wurst.build: ${CoreJassProvider.DEFAULT_PATCH}" ||
            trimmed.startsWith("Warning: Ignoring unknown wc3Patch in wurst.build:") ||
            trimmed.startsWith("Warning: Wurst compiler failed to determine game version") ||
            trimmed.contains("VersionExtractionException: Failed to extract executable version data") ||
            trimmed.startsWith("at net.moonlightflower.wc3libs.misc.exeversion.") ||
            trimmed.startsWith("at net.moonlightflower.wc3libs.bin.GameExe.") ||
            trimmed.startsWith("at net.moonlightflower.wc3libs.port.") ||
            trimmed.startsWith("at de.peeeq.wurstio.utils.W3InstallationData.discoverVersion") ||
            trimmed.startsWith("at de.peeeq.wurstio.utils.W3InstallationData.<init>") ||
            trimmed.startsWith("at de.peeeq.wurstio.languageserver.requests.MapRequest.getBestW3InstallationData") ||
            trimmed.startsWith("at de.peeeq.wurstio.languageserver.requests.MapRequest.<init>") ||
            trimmed.startsWith("at de.peeeq.wurstio.languageserver.requests.CliBuildMap.<init>") ||
            trimmed.startsWith("at de.peeeq.wurstio.Main.main") ||
            trimmed.startsWith("at dorkbox.peParser.PE") ||
            trimmed.startsWith("Caused by: java.lang.NullPointerException") ||
            trimmed.matches(Regex("""\.\.\. \d+ more"""))
    }

    internal var generatePrompt: ((String, String?) -> String?)? = null
    internal var installPatchPrompt: ((String, String?) -> String?)? = null

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

        runWizard(setup, prompt, useInteractiveMenus = generatePrompt == null)
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

    private fun runWizard(setup: SetupMain, prompt: (String, String?) -> String?, useInteractiveMenus: Boolean) {
        setup.scriptMode = selectScriptMode(prompt, setup.scriptMode, useInteractiveMenus)
        setup.wc3Patch = selectPatchVersion(
            prompt,
            intro = "WC3 patch choices:",
            useInteractiveMenus = useInteractiveMenus,
            currentPatch = setup.wc3Patch
        )
        setup.gamePath = selectGamePath(prompt, setup.wc3Patch, setup.gamePath)

        val agentsDefault = if (setup.addAgents) "Y" else "N"
        val agentsInput = prompt("Add AGENTS.md?", agentsDefault)
        setup.addAgents = agentsInput?.lowercase() == "y"

        val ciDefault = if (setup.addGithubWorkflow) "Y" else "N"
        val ciInput = prompt("Add GitHub Actions CI?", ciDefault)
        setup.addGithubWorkflow = ciInput?.lowercase() == "y"
    }

    private fun selectGamePath(
        prompt: (String, String?) -> String?,
        wc3Patch: String?,
        currentPath: Path?
    ): Path? {
        val detected = currentPath ?: Wc3ClientDetector.detectGameRoot()
        val detectedInfo = Wc3ClientDetector.inspectGameRoot(detected)
        if (detectedInfo != null) {
            log.info("Detected Warcraft III: ${Wc3ClientDetector.describe(detectedInfo)}")
            Wc3ClientDetector.mismatchMessage(wc3Patch, detectedInfo)?.let { log.warn(it) }
        } else {
            log.info("No Warcraft III installation was detected automatically.")
        }

        val default = detected?.toAbsolutePath()?.normalize()?.toString() ?: "none"
        val answer = prompt("Warcraft III directory (or none)", default)?.trim() ?: return detected
        if (answer.equals("none", ignoreCase = true) || answer.equals("skip", ignoreCase = true)) {
            return null
        }
        val selected = Paths.get(answer).toAbsolutePath().normalize()
        val selectedInfo = Wc3ClientDetector.inspectGameRoot(selected)
        if (selectedInfo == null) {
            log.warn("No supported Warcraft III executable found in $selected. You can fix wurst.wc3path later in .vscode/settings.json.")
            return null
        }
        Wc3ClientDetector.mismatchMessage(wc3Patch, selectedInfo)?.let { log.warn(it) }
        return selectedInfo.root
    }

    internal fun stdlibDependencyForPatch(wc3Patch: String?): String {
        return if (CoreJassProvider.isPre129Patch(wc3Patch)) {
            "https://github.com/wurstscript/wurstStdlib2:pre1.29"
        } else {
            "https://github.com/wurstscript/wurstStdlib2"
        }
    }

    internal fun generatedBuildMapData(projectName: String): WurstProjectBuildMapData {
        val mapName = projectName.trim().ifBlank { "Unnamed" }
        return WurstProjectBuildMapData(
            mapName,
            "$mapName.w3x",
            defaultAuthorName(),
            null,
            null,
            emptyList(),
            emptyList()
        )
    }

    private fun defaultAuthorName(): String {
        val systemUser = System.getProperty("user.name").orEmpty().trim()
        if (systemUser.isNotBlank()) {
            return systemUser
        }
        return Paths.get(System.getProperty("user.home").orEmpty()).fileName?.toString()?.ifBlank { "Unknown" } ?: "Unknown"
    }

    private fun selectScriptMode(
        prompt: (String, String?) -> String?,
        defaultMode: ScriptMode,
        useInteractiveMenus: Boolean
    ): ScriptMode {
        if (useInteractiveMenus) {
            TerminalMenu.choose(
                title = "Script mode",
                choices = listOf(
                    TerminalMenu.Choice(ScriptMode.LUA, "lua"),
                    TerminalMenu.Choice(ScriptMode.JASS, "jass")
                ),
                defaultIndex = if (defaultMode == ScriptMode.JASS) 1 else 0
            )?.let { return it }
        }

        log.info("Script mode choices:")
        log.info("  1. lua")
        log.info("  2. jass")

        while (true) {
            val defaultValue = defaultMode.name.lowercase()
            val answer = prompt("Script mode (number/name)", defaultValue)?.trim()
            if (answer.isNullOrBlank()) {
                return defaultMode
            }
            when (answer.lowercase()) {
                "1", "lua" -> return ScriptMode.LUA
                "2", "jass" -> return ScriptMode.JASS
                else -> log.error("Unsupported script mode: $answer. Choose 1/lua or 2/jass.")
            }
        }
    }

    private fun ensureProjectPatchRecorded(configData: WurstProjectConfigData): WurstProjectConfigData {
        val currentPatch = configData.wc3Patch
        if (currentPatch.isNullOrBlank()) {
            val selectedPatch = selectPatchVersionForInstall()
            log.info("WC3 patch recorded in wurst.build: $selectedPatch")
            return configData.withWc3Patch(selectedPatch)
        }

        val normalizedPatch = CoreJassProvider.normalizePatchInput(currentPatch)
        return if (normalizedPatch != currentPatch) {
            configData.withWc3Patch(normalizedPatch)
        } else {
            configData
        }
    }

    internal fun selectPatchVersionForInstall(): String {
        return selectPatchVersion(
            installPatchPrompt ?: terminalPrompt(),
            intro = "No WC3 patch is recorded in wurst.build yet.",
            useInteractiveMenus = installPatchPrompt == null,
            currentPatch = null
        )
    }

    private fun selectPatchVersion(
        prompt: (String, String?) -> String?,
        intro: String,
        useInteractiveMenus: Boolean,
        currentPatch: String?
    ): String {
        val versions = CoreJassProvider.fetchJassHistoryVersions()
        val recommended = CoreJassProvider.recommendedPatchOptions(versions)
        val patchTargets = CoreJassProvider.supportedPatches
        val normalizedCurrentPatch = currentPatch?.let(CoreJassProvider::normalizePatchInput)
        val defaultPatch = when {
            normalizedCurrentPatch != null && CoreJassProvider.isSupportedPatch(normalizedCurrentPatch) -> normalizedCurrentPatch
            else -> recommended.firstOrNull() ?: CoreJassProvider.DEFAULT_PATCH
        }
        val browseAll = "__browse_all__"

        if (useInteractiveMenus) {
            while (true) {
                val choices = recommended.map { TerminalMenu.Choice(it, CoreJassProvider.describePatch(it)) } +
                    TerminalMenu.Choice(browseAll, "Browse all supported patch targets...") +
                    TerminalMenu.Choice("__browse_exact__", "Advanced: browse exact jass-history dumps...")
                val selection = TerminalMenu.choose(
                    title = intro,
                    choices = choices,
                    defaultIndex = recommended.indexOf(defaultPatch).takeIf { it >= 0 } ?: 0
                )
                when {
                    selection == null -> return defaultPatch
                    selection == browseAll -> browsePatchVersionsInteractive("WC3 patch targets", patchTargets)?.let { return it }
                    selection == "__browse_exact__" -> browsePatchVersionsInteractive("Exact jass-history dumps", versions)?.let { return it }
                    else -> return selection
                }
            }
        }

        log.info(intro)
        val visibleRecommended = (listOf(defaultPatch) + recommended).distinct()
        log.info("Recommended patch choices:")
        visibleRecommended.forEachIndexed { index, patch ->
            log.info("  ${index + 1}. ${CoreJassProvider.describePatch(patch)}")
        }
        if (patchTargets.isNotEmpty()) {
            log.info("Type `more` to browse supported patch targets.")
        }
        if (versions.isNotEmpty()) {
            log.info("Type `exact` to browse raw jass-history dump folders.")
        }
        log.info("Enter a listed number, press Enter for the default, or type `more`.")

        while (true) {
            val answer = prompt("WC3 patch version (number/version/more)", defaultPatch)?.trim()
            if (answer.isNullOrBlank()) {
                return defaultPatch
            }
            val topIndex = answer.toIntOrNull()
            if (topIndex != null && topIndex in 1..visibleRecommended.size) {
                return visibleRecommended[topIndex - 1]
            }
            when (answer.lowercase()) {
                "more", "list", "all" -> browsePatchVersions(
                    title = "WC3 patch targets",
                    versions = patchTargets,
                    prompt = prompt,
                    exactVersions = versions
                )?.let { return it }
                "exact", "raw", "dumps" -> browsePatchVersions(
                    title = "Exact jass-history dumps",
                    versions = versions,
                    prompt = prompt,
                    exactVersions = emptyList()
                )?.let { return it }
                "q", "quit", "cancel" -> return defaultPatch
                else -> {
                    val normalized = CoreJassProvider.normalizePatchInput(answer)
                    val directSelection = visibleRecommended.firstOrNull {
                        it.equals(normalized, ignoreCase = true) ||
                            CoreJassProvider.normalizePatchInput(it).equals(normalized, ignoreCase = true)
                    }
                    if (directSelection != null) {
                        return directSelection
                    }
                    log.error("Unsupported patch selection: $answer")
                    log.info("Choose one of the listed numbers, type `more`, or type `exact` for raw dump folders.")
                }
            }
        }
    }

    private fun browsePatchVersions(
        title: String,
        versions: List<String>,
        prompt: (String, String?) -> String?,
        exactVersions: List<String>
    ): String? {
        if (versions.isEmpty()) {
            log.info("Could not load jass-history versions right now. You can still type a version folder manually.")
            return null
        }

        val pageSize = 20
        var page = 0
        while (true) {
            val start = page * pageSize
            val visibleVersions = versions.drop(start).take(pageSize)
            if (visibleVersions.isEmpty()) {
                page = 0
                continue
            }

            log.info("$title ${start + 1}-${start + visibleVersions.size} of ${versions.size}:")
            visibleVersions.forEachIndexed { index, version ->
                log.info("  ${index + 1}. ${CoreJassProvider.describePatch(version)}")
            }
            if (exactVersions.isNotEmpty()) {
                log.info("Type `exact` for raw jass-history dump folders.")
            }
            val answer = prompt("Select version (number/version, n next, p previous, q back)", null)?.trim()
            if (answer.isNullOrBlank()) {
                return null
            }
            val pageIndex = answer.toIntOrNull()
            if (pageIndex != null && pageIndex in 1..visibleVersions.size) {
                return visibleVersions[pageIndex - 1]
            }
            when (answer.lowercase()) {
                "n", "next" -> page = if (start + pageSize >= versions.size) 0 else page + 1
                "p", "prev", "previous" -> page = if (page == 0) (versions.size - 1) / pageSize else page - 1
                "q", "back", "cancel" -> return null
                "exact", "raw", "dumps" -> browsePatchVersions(
                    title = "Exact jass-history dumps",
                    versions = exactVersions,
                    prompt = prompt,
                    exactVersions = emptyList()
                )?.let { return it }
                else -> {
                    val normalized = CoreJassProvider.normalizePatchInput(answer)
                    val directSelection = versions.firstOrNull {
                        it.equals(normalized, ignoreCase = true) ||
                            CoreJassProvider.normalizePatchInput(it).equals(normalized, ignoreCase = true)
                    }
                    if (directSelection != null) {
                        return directSelection
                    }
                    log.error("Unsupported patch selection: $answer")
                    log.info("Choose a number from the current page, use `n`/`p`, type `exact`, or type `q` to go back.")
                }
            }
        }
    }

    private fun browsePatchVersionsInteractive(title: String, versions: List<String>): String? {
        if (versions.isEmpty()) {
            return null
        }
        return TerminalMenu.choose(
            title = title,
            choices = versions.map { TerminalMenu.Choice(it, CoreJassProvider.describePatch(it)) },
            defaultIndex = 0
        )
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
            if (!setup.debug && isNoisyCompilerVersionLine(line)) {
                return@forEachLine
            }
            if (!setup.quiet) {
                println(line)
            }
        }
        val exitCode = p.waitFor()
        if (setup.quiet && exitCode != 0) {
            val printableOutput = if (setup.debug) output else output.filterNot(::isNoisyCompilerVersionLine)
            val linesToPrint = if (compactFallback) printableOutput.filter(::isImportantCompilerLine) else printableOutput
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
                ensureCoreJassFiles(setup.projectRoot, configData.wc3Patch)
                    .filter { Files.exists(it) }
                    .forEach { args.add(it.toAbsolutePath().toString()) }
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

    internal fun ensureCoreJassFiles(projectRoot: Path, wc3Patch: String?): List<Path> {
        return CoreJassProvider.ensureFiles(projectRoot, wc3Patch)
    }

	private fun handleRemoveDep(configData: WurstProjectConfigData): WurstProjectConfigData {
		log.info("🧹 Removing ${setup.commandArg}")
		if (configData.dependencies.contains(setup.commandArg)) {
            log.info("✅ Dependency removed.")
            return configData.withRemovedDependency(setup.commandArg)
		} else {
			log.error("❌ Dependency is not listed in wurst.build: ${setup.commandArg}")
		}
        return configData
	}

	private fun handleRemoveWurst() {
		if (!setup.requireConfirmation) {
			InstallationManager.handleRemove()
		}
	}

	private fun handleUpdateProject(configData: WurstProjectConfigData) {
		WurstProjectConfig.handleUpdate(setup.projectRoot, null, configData)
        ensureCoreJassFiles(setup.projectRoot, configData.wc3Patch)
	}

    val REPO_REGEX = Regex("(https?://)([\\w.@-]+)(/)([\\w,-_]+)/([\\w,-_]+)(.git)?((/)?)")

	private fun handleInstallDep(configData: WurstProjectConfigData): WurstProjectConfigData {
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
			return configData
		}
		try {
			val result = Git.lsRemoteRepository()
				.setRemote(resolvedName.first)
				.call()
			if (!result.isEmpty()) {
				Log.print("valid!\n")
                return configData.withAddedDependency(setup.commandArg)
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
        return configData
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
