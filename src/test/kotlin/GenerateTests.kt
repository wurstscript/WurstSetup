import config.ScriptMode
import file.CLICommand
import file.CoreJassProvider
import file.CuratedDependencies
import file.ExitHandler
import file.SetupApp
import file.SetupMain
import org.testng.Assert
import org.testng.annotations.Test
import java.nio.file.Files
import java.nio.file.Paths
import java.util.Comparator

private class ExitException2(val code: Int) : RuntimeException("exit $code")

private fun catchExit2(block: () -> Unit): Int {
    val prev = ExitHandler.handler
    var code = -1
    try {
        ExitHandler.handler = { throw ExitException2(it) }
        block()
    } catch (e: ExitException2) {
        code = e.code
    } finally {
        ExitHandler.handler = prev
    }
    return code
}

private fun bundledCoreJassText(folder: String, fileName: String): String {
    return CoreJassProvider::class.java.classLoader
        .getResourceAsStream("core-jass/$folder/$fileName")!!
        .bufferedReader()
        .use { it.readText() }
}

class GenerateTests {

    @Test(priority = 10)
    fun testNonInteractiveDefaults() {
        val setup = SetupMain()
        setup.parseArgs(listOf("generate", "myproject"))
        Assert.assertEquals(setup.command, CLICommand.GENERATE)
        Assert.assertEquals(setup.commandArg, "myproject")
        Assert.assertEquals(setup.scriptMode, ScriptMode.LUA)
        Assert.assertEquals(setup.wc3Patch, CoreJassProvider.DEFAULT_PATCH)
        Assert.assertFalse(setup.addAgents)
        Assert.assertFalse(setup.addGithubWorkflow)
    }

    @Test(priority = 10)
    fun testScriptModeJassFlag() {
        val setup = SetupMain()
        setup.parseArgs(listOf("generate", "myproject", "--script-mode", "jass"))
        Assert.assertEquals(setup.scriptMode, ScriptMode.JASS)
        Assert.assertEquals(setup.wc3Patch, CoreJassProvider.DEFAULT_PATCH)
    }

    @Test(priority = 10)
    fun testWc3PatchPre129Flag() {
        val setup = SetupMain()
        setup.parseArgs(listOf("generate", "myproject", "--wc3-patch", "pre1.29"))
        Assert.assertEquals(setup.scriptMode, ScriptMode.LUA)
        Assert.assertEquals(setup.wc3Patch, CoreJassProvider.PRE_129_PATCH)
    }

    @Test(priority = 10)
    fun testWc3PatchJassHistoryVersionFlag() {
        val setup = SetupMain()
        setup.parseArgs(listOf("generate", "myproject", "--wc3-patch", "Reforged-v1.36.1.20719-w3-51d40ee"))
        Assert.assertEquals(setup.wc3Patch, "v1.36")

        val letterPatch = SetupMain()
        letterPatch.parseArgs(listOf("generate", "myproject", "--wc3-patch", "1.27b"))
        Assert.assertEquals(letterPatch.wc3Patch, "v1.27b")
    }

    @Test(priority = 10)
    fun testPatchAliasNormalizationAndLegacyDetection() {
        Assert.assertEquals(CoreJassProvider.normalizePatchInput("reforged"), CoreJassProvider.DEFAULT_PATCH)
        Assert.assertEquals(CoreJassProvider.normalizePatchInput("pre1.29"), CoreJassProvider.PRE_129_PATCH)
        Assert.assertFalse(CoreJassProvider.isSupportedPatch("v9.99"))
        Assert.assertTrue(CoreJassProvider.isPre129Patch("TFT-v1.28.2.7395"))
        Assert.assertFalse(CoreJassProvider.isPre129Patch("TFT-v1.31.1.12173"))
        Assert.assertEquals(
            CoreJassProvider.jassHistoryFolderForPatch("v1.36"),
            "Reforged-v1.36.1.20719-w3-51d40ee"
        )
        Assert.assertEquals(
            CoreJassProvider.describePatch("v1.36"),
            "v1.36 (Reforged)"
        )
        Assert.assertEquals(
            CoreJassProvider.jassHistoryFolderForPatch("v2.0"),
            "Reforged-v2.0.4.23745"
        )
        Assert.assertEquals(
            CoreJassProvider.describePatch("v2.0"),
            "v2.0 (latest Reforged / WC3 2.x core JASS)"
        )
    }

    @Test(priority = 10)
    fun testJassHistoryVersionListSplitsWhitespaceSeparatedTokens() {
        val parsed = CoreJassProvider.parseJassHistoryVersionList(
            """
            Beta-ROC-v1.21 TFT-v1.27b-ru Reforged-v2.0.4.23745
            # comments and blank chunks should be ignored
            not-a-version   ROC-v1.06-ru
            """.trimIndent()
        )

        Assert.assertEquals(
            parsed,
            listOf("Beta-ROC-v1.21", "TFT-v1.27b-ru", "Reforged-v2.0.4.23745", "ROC-v1.06-ru")
        )
        Assert.assertFalse(parsed.any { it.contains(" ") })
    }

    @Test(priority = 10)
    fun testStdlibDependencyFollowsPatchEra() {
        val pre124Stdlib = "https://github.com/wurstscript/wurstStdlib2:pre1.24"
        val legacyStdlib = "https://github.com/wurstscript/wurstStdlib2:pre1.29"
        val currentStdlib = "https://github.com/wurstscript/wurstStdlib2"

        for (patch in CoreJassProvider.supportedPatches) {
            val minor = Regex("""v1\.(\d+)""").find(patch)?.groupValues?.get(1)?.toIntOrNull()
            val expected = when {
                minor != null && minor < 24 -> pre124Stdlib
                minor != null && minor < 29 -> legacyStdlib
                else -> currentStdlib
            }
            Assert.assertEquals(SetupApp.stdlibDependencyForPatch(patch), expected, "stdlib dependency for $patch")
        }

        Assert.assertEquals(SetupApp.stdlibDependencyForPatch("v1.23a"), pre124Stdlib)
        Assert.assertEquals(SetupApp.stdlibDependencyForPatch("TFT-v1.27b-ru"), legacyStdlib)
        Assert.assertEquals(SetupApp.stdlibDependencyForPatch("pre1.29"), legacyStdlib)
        Assert.assertEquals(SetupApp.stdlibDependencyForPatch("v1.29"), currentStdlib)
    }

    @Test(priority = 10)
    fun testGeneratedBuildMapDataSeedsOnlyKnownFields() {
        val dumped = file.YamlHelper.dumpProjectConfig(
            config.newProjectConfig(
                projectName = "fsa",
                buildMapData = SetupApp.generatedBuildMapData("fsa"),
                scriptMode = ScriptMode.JASS,
                wc3Patch = "v1.27b"
            )
        )

        Assert.assertTrue(dumped.contains("buildMapData:"))
        Assert.assertTrue(dumped.contains("name: fsa"))
        Assert.assertTrue(dumped.contains("fileName: fsa.w3x"))
        Assert.assertTrue(dumped.contains("author:"))
        Assert.assertFalse(dumped.contains("scenarioData:"))
        Assert.assertFalse(dumped.contains("optionsFlags:"))
        Assert.assertFalse(dumped.contains("loadingScreen: null"))
        Assert.assertFalse(dumped.contains("players: []"))
        Assert.assertFalse(dumped.contains("forces: []"))
    }

    @Test(priority = 10)
    fun testGenerateParsesWc3PathOption() {
        val setup = SetupMain()
        setup.parseArgs(listOf("generate", "map", "--wc3-path", "C:\\Games\\Warcraft III"))

        Assert.assertEquals(setup.gamePath, java.nio.file.Paths.get("C:\\Games\\Warcraft III"))
        Assert.assertTrue(setup.gamePathExplicit)
    }

    @Test(priority = 10)
    fun testInstallPatchSelectionRejectsUnsupportedFreeText() {
        val answers = java.util.ArrayDeque(listOf("totally-not-a-patch", "2"))
        val prevPrompt = SetupApp.installPatchPrompt
        try {
            SetupApp.installPatchPrompt = { _, _ -> answers.removeFirst() }
            Assert.assertEquals(SetupApp.selectPatchVersionForInstall(), "v1.31")
        } finally {
            SetupApp.installPatchPrompt = prevPrompt
        }
    }

    @Test(priority = 10)
    fun testInstallPatchSelectionRequiresBrowseForNonRecommendedVersions() {
        val answers = java.util.ArrayDeque(listOf("v1.32", "more", "v1.32"))
        val prevPrompt = SetupApp.installPatchPrompt
        try {
            SetupApp.installPatchPrompt = { _, _ -> answers.removeFirst() }
            Assert.assertEquals(SetupApp.selectPatchVersionForInstall(), "v1.32")
        } finally {
            SetupApp.installPatchPrompt = prevPrompt
        }
    }

    @Test(priority = 10)
    fun testInstallPatchSelectionKeepsExactDumpsAdvanced() {
        val answers = java.util.ArrayDeque(listOf("exact", "Reforged-v1.32.10.19202"))
        val prevPrompt = SetupApp.installPatchPrompt
        try {
            SetupApp.installPatchPrompt = { _, _ -> answers.removeFirst() }
            Assert.assertEquals(SetupApp.selectPatchVersionForInstall(), "Reforged-v1.32.10.19202")
        } finally {
            SetupApp.installPatchPrompt = prevPrompt
        }
    }

    @Test(priority = 10)
    fun testAllGenerateFlagsTogether() {
        val setup = SetupMain()
        setup.parseArgs(
            listOf(
                "generate", "myproject",
                "--script-mode", "jass",
                "--wc3-patch", "pre1.29",
                "--with-agents",
                "--with-ci"
            )
        )
        Assert.assertEquals(setup.scriptMode, ScriptMode.JASS)
        Assert.assertEquals(setup.wc3Patch, CoreJassProvider.PRE_129_PATCH)
        Assert.assertTrue(setup.addAgents)
        Assert.assertTrue(setup.addGithubWorkflow)
    }

    @Test(priority = 10)
    fun testMissingScriptModeValueExitsCleanly() {
        val code = catchExit2 {
            SetupMain().parseArgs(listOf("generate", "myproject", "--script-mode"))
        }
        Assert.assertEquals(code, 1, "Missing option values should be reported as CLI errors")
    }

    @Test(priority = 10)
    fun testMissingProjectDirValueExitsCleanly() {
        val code = catchExit2 {
            SetupMain().parseArgs(listOf("install", "-projectDir"))
        }
        Assert.assertEquals(code, 1, "Missing option values should be reported as CLI errors")
    }

    @Test(priority = 10)
    fun testWithAndNoAgentsFlagOrder() {
        val setup = SetupMain()
        setup.parseArgs(listOf("generate", "myproject", "--with-agents"))
        Assert.assertTrue(setup.addAgents)

        val setup2 = SetupMain()
        setup2.parseArgs(listOf("generate", "myproject", "--no-agents"))
        Assert.assertFalse(setup2.addAgents)
    }
    @Test(priority = 10)
    fun testAgentsTemplateMarkerAndWarnings() {
        val templateFirstLine = Files.readAllLines(Paths.get("templates", "AGENTS.md")).first()
        Assert.assertEquals(
            templateFirstLine,
            "<!-- WURST_AGENTS_TEMPLATE_VERSION: ${SetupApp.AGENTS_TEMPLATE_VERSION} -->"
        )

        val marked = SetupApp.withAgentsTemplateMarker("# AGENTS.md\n")
        Assert.assertTrue(marked.startsWith("<!-- WURST_AGENTS_TEMPLATE_VERSION: ${SetupApp.AGENTS_TEMPLATE_VERSION} -->"))
        Assert.assertNull(SetupApp.agentsTemplateWarning(marked))

        val oldMarked = "<!-- WURST_AGENTS_TEMPLATE_VERSION: 2026-01-01 -->\n# AGENTS.md\n"
        Assert.assertTrue(SetupApp.agentsTemplateWarning(oldMarked)!!.contains("older WurstSetup template"))

        val unmarkedGenerated = "# AGENTS.md - WurstScript Map Project Notes\n\nWurstScript Warcraft III map project notes"
        Assert.assertTrue(SetupApp.agentsTemplateWarning(unmarkedGenerated)!!.contains("without a version marker"))

        Assert.assertNull(SetupApp.agentsTemplateWarning("# Custom project notes\n"))
    }

    @Test(priority = 10)
    fun testDebugFlag() {
        val setup = SetupMain()
        setup.parseArgs(listOf("test", "--debug"))
        Assert.assertTrue(setup.debug)
    }

    @Test(priority = 10)
    fun testQuietFlag() {
        val setup = SetupMain()
        setup.parseArgs(listOf("test", "--quiet"))
        Assert.assertTrue(setup.quiet)
    }

    @Test(priority = 10)
    fun testQuietCompilerDiagnosticsSuppressGeneratedJassNoise() {
        val output = listOf(
            "Warnings: 3",
            "Warning: Error:  e:Could not find variable silverGladeCounter.",
            "Warning: Error:  e:Could not find a function with name eg",
            "Error Broken.wurst:12: Could not find variable realUserTypo.",
            "compilation finished (errors: 1, warnings: 3)",
            "Errors: 1"
        )

        Assert.assertEquals(
            SetupApp.quietCompilerDiagnostics(output),
            listOf("Error Broken.wurst:12: Could not find variable realUserTypo.")
        )
        Assert.assertEquals(SetupApp.quietCompilerErrorCount(output), 1)
    }

    @Test(priority = 10)
    fun testQuietCompilerDiagnosticsKeepFailedTestDetails() {
        val output = listOf(
            "Running tests",
            "Tests: 1/2 passed",
            "FAILED MyPkg.testExplodes",
            "\tFAILED assertion:",
            "\tTest failed: expected 1 but got 2",
            "\t    ╚ MyTest.wurst:9 inside call assertEquals(1, 2)",
            "\t... when calling MyPkg.testExplodes(MyTest.wurst:12)",
            "Errors: 1",
            "Error MyTest.wurst:9: expected 1 but got 2",
            "Finished running tests"
        )

        Assert.assertEquals(
            SetupApp.quietCompilerDiagnostics(output),
            listOf(
                "FAILED MyPkg.testExplodes",
                "\tFAILED assertion:",
                "\tTest failed: expected 1 but got 2",
                "\t    ╚ MyTest.wurst:9 inside call assertEquals(1, 2)",
                "\t... when calling MyPkg.testExplodes(MyTest.wurst:12)",
                "Error MyTest.wurst:9: expected 1 but got 2"
            )
        )
        Assert.assertEquals(SetupApp.quietCompilerErrorCount(output), 1)
    }

    @Test(priority = 10)
    fun testQuietCompilerDiagnosticsNormalizeVerboseFallbackErrors() {
        val output = listOf(
            "Error in File Broken.wurst:12:",
            " Could not find variable realUserTypo.",
            "Warning in File war3map.j:44:",
            " Error:  e:Could not find variable silverGladeCounter."
        )

        Assert.assertEquals(
            SetupApp.quietCompilerDiagnostics(output),
            listOf("Error Broken.wurst:12: Could not find variable realUserTypo.")
        )
    }

    @Test(priority = 10)
    fun testDevBuildFlag() {
        val setup = SetupMain()
        setup.parseArgs(listOf("build", "ExampleMap.w3x", "--dev"))
        Assert.assertEquals(setup.command, CLICommand.BUILD)
        Assert.assertEquals(setup.commandArg, "ExampleMap.w3x")
        Assert.assertTrue(setup.devBuild)
    }

    @Test(priority = 10)
    fun testGenerateWithoutNameUsesWizardPrompt() {
        val setup = SetupMain()
        setup.parseArgs(listOf("generate"))
        val answers = java.util.ArrayDeque(listOf("wizardproject", "jass", "pre1.29", "none", "y", "y", "n"))
        val prevPrompt = SetupApp.generatePrompt
        try {
            SetupApp.generatePrompt = { _, _ -> answers.removeFirst() }
            Assert.assertTrue(SetupApp.prepareGenerate(setup))
        } finally {
            SetupApp.generatePrompt = prevPrompt
        }

        Assert.assertEquals(setup.commandArg, "wizardproject")
        Assert.assertEquals(setup.scriptMode, ScriptMode.JASS)
        Assert.assertEquals(setup.wc3Patch, CoreJassProvider.PRE_129_PATCH)
        Assert.assertTrue(setup.addAgents)
        Assert.assertTrue(setup.addGithubWorkflow)
        Assert.assertTrue(setup.curatedDependencyIds.isEmpty())
    }

    @Test(priority = 10)
    fun testGenerateWizardRejectsUnsupportedScriptModeAndPatchInput() {
        val setup = SetupMain()
        setup.parseArgs(listOf("generate"))
        val answers = java.util.ArrayDeque(listOf("wizardproject", "t", "jass", "t", "2", "none", "n", "n", "n"))
        val prevPrompt = SetupApp.generatePrompt
        try {
            SetupApp.generatePrompt = { _, _ -> answers.removeFirst() }
            Assert.assertTrue(SetupApp.prepareGenerate(setup))
        } finally {
            SetupApp.generatePrompt = prevPrompt
        }

        Assert.assertEquals(setup.commandArg, "wizardproject")
        Assert.assertEquals(setup.scriptMode, ScriptMode.JASS)
        Assert.assertEquals(setup.wc3Patch, "v1.31")
        Assert.assertFalse(setup.addAgents)
        Assert.assertFalse(setup.addGithubWorkflow)
    }

    @Test(priority = 10)
    fun testGenerateWizardPreservesCliPatchDefault() {
        val setup = SetupMain()
        setup.parseArgs(listOf("generate", "--wc3-patch", "pre1.29"))
        val answers = java.util.ArrayDeque(listOf("wizardproject", "", "", "none", "n", "n", "n"))
        val prevPrompt = SetupApp.generatePrompt
        try {
            SetupApp.generatePrompt = { _, _ -> answers.removeFirst() }
            Assert.assertTrue(SetupApp.prepareGenerate(setup))
        } finally {
            SetupApp.generatePrompt = prevPrompt
        }

        Assert.assertEquals(setup.commandArg, "wizardproject")
        Assert.assertEquals(setup.scriptMode, ScriptMode.LUA)
        Assert.assertEquals(setup.wc3Patch, CoreJassProvider.PRE_129_PATCH)
        Assert.assertFalse(setup.addAgents)
        Assert.assertFalse(setup.addGithubWorkflow)
    }

    @Test(priority = 10)
    fun testGenerateWithoutNameReturnsWithoutGeneratingWhenPromptCannotReadName() {
        val setup = SetupMain()
        setup.parseArgs(listOf("generate"))
        val prevPrompt = SetupApp.generatePrompt
        val prepared = try {
            SetupApp.generatePrompt = { _, _ -> null }
            SetupApp.prepareGenerate(setup)
        } finally {
            SetupApp.generatePrompt = prevPrompt
        }

        Assert.assertFalse(prepared, "Blank generate without a readable name must not generate into the current directory")
        Assert.assertTrue(setup.commandArg.isBlank())
    }

    @Test(priority = 10)
    fun testCuratedDependencyCatalogResolvesTableLayout() {
        val dep = CuratedDependencies.findById("table-layout")
        Assert.assertNotNull(dep)
        Assert.assertEquals(dep!!.url, "https://github.com/Frotty/wurst-table-layout")
        Assert.assertEquals(dep.summary, "wurst-table-layout (AI ready UI toolkit)")
        Assert.assertEquals(CuratedDependencies.findById("TABLE-LAYOUT")?.id, "table-layout")
        Assert.assertEquals(
            CuratedDependencies.matching(listOf(dep.url)).map { it.id },
            listOf("table-layout")
        )
    }

    @Test(priority = 10)
    fun testGenerateWithDepFlagSelectsCuratedDependency() {
        val setup = SetupMain()
        setup.parseArgs(listOf("generate", "myproject", "--with-dep", "table-layout"))
        Assert.assertEquals(setup.curatedDependencyIds, listOf("table-layout"))
    }

    @Test(priority = 10)
    fun testGenerateWithDepFlagIsDeduplicated() {
        val setup = SetupMain()
        setup.parseArgs(listOf("generate", "myproject", "--with-dep", "table-layout", "--with-dep", "table-layout"))
        Assert.assertEquals(setup.curatedDependencyIds, listOf("table-layout"))
    }

    @Test(priority = 10)
    fun testGenerateWithUnknownDepFlagExitsCleanly() {
        val code = catchExit2 {
            SetupMain().parseArgs(listOf("generate", "myproject", "--with-dep", "does-not-exist"))
        }
        Assert.assertEquals(code, 1, "An unknown curated dependency id should be reported as a CLI error")
    }

    @Test(priority = 10)
    fun testGenerateWizardSelectsCuratedDependency() {
        val setup = SetupMain()
        setup.parseArgs(listOf("generate"))
        val answers = java.util.ArrayDeque(listOf("wizardproject", "lua", "", "none", "n", "n", "y"))
        val prevPrompt = SetupApp.generatePrompt
        try {
            SetupApp.generatePrompt = { _, _ -> answers.removeFirst() }
            Assert.assertTrue(SetupApp.prepareGenerate(setup))
        } finally {
            SetupApp.generatePrompt = prevPrompt
        }

        Assert.assertEquals(setup.curatedDependencyIds, listOf("table-layout"))
    }

    @Test(priority = 10)
    fun testNoArgsExitsZeroWithoutOldUi() {
        val code = catchExit2 { SetupMain().doMain(arrayOf()) }
        Assert.assertEquals(code, 0, "No-args launch should exit 0 (CLI-first message), not open the old Swing UI")
    }

    @Test(priority = 10)
    fun testCiWorkflowContainsExpectedContent() {
        val tmpDir = Files.createTempDirectory("grill-ci-test")
        try {
            SetupApp.writeCiWorkflow(tmpDir)
            val workflow = Files.readString(tmpDir.resolve(".github/workflows/grill.yml"))

            Assert.assertTrue(workflow.contains("name: Grill CI"))
            Assert.assertTrue(workflow.contains("container:"))
            Assert.assertTrue(workflow.contains("image: frotty/wurstscript"))
            Assert.assertTrue(workflow.contains("uses: actions/checkout@v4"))
            Assert.assertTrue(workflow.contains("run: grill install"))
            Assert.assertTrue(workflow.contains("run: grill build ExampleMap.w3x"))
            Assert.assertFalse(workflow.contains("uses: frotty/wurstscript@master"))
        } finally {
            Files.walk(tmpDir).sorted(Comparator.reverseOrder()).forEach {
                try {
                    Files.deleteIfExists(it)
                } catch (_: Exception) {
                }
            }
        }
    }

    @Test(priority = 10)
    fun testCoreJassFilesAreEmittedToBuildDir() {
        val tmpDir = Files.createTempDirectory("grill-core-jass-test")
        try {
            SetupApp.ensureCoreJassFiles(tmpDir, CoreJassProvider.DEFAULT_PATCH)

            val common = tmpDir.resolve("_build/common.j")
            val blizzard = tmpDir.resolve("_build/blizzard.j")
            Assert.assertTrue(Files.exists(common), "common.j should be emitted directly into _build")
            Assert.assertTrue(Files.exists(blizzard), "blizzard.j should be emitted directly into _build")
            Assert.assertTrue(Files.readString(common).contains("native ConvertRace"))
            Assert.assertTrue(Files.readString(blizzard).contains("Blizzard.j"))
        } finally {
            Files.walk(tmpDir).sorted(Comparator.reverseOrder()).forEach {
                try {
                    Files.deleteIfExists(it)
                } catch (_: Exception) {
                }
            }
        }
    }

    @Test(priority = 10)
    fun testDefaultPatchDownloadFailureUsesBundledV2CoreJass() {
        val tmpDir = Files.createTempDirectory("grill-core-jass-v2-fallback-test")
        val previousDownloader = CoreJassProvider.jassHistoryFileDownloader
        try {
            CoreJassProvider.jassHistoryFileDownloader = { _, _ -> throw RuntimeException("offline") }

            SetupApp.ensureCoreJassFiles(tmpDir, CoreJassProvider.DEFAULT_PATCH)

            val buildDir = tmpDir.resolve("_build")
            val common = Files.readString(buildDir.resolve("common.j"))
            val blizzard = Files.readString(buildDir.resolve("blizzard.j"))
            Assert.assertEquals(common, bundledCoreJassText("v2.0", "common.j"))
            Assert.assertEquals(blizzard, bundledCoreJassText("v2.0", "blizzard.j"))
            Assert.assertEquals(CoreJassProvider.bundledCoreJassFolderForPatch(CoreJassProvider.DEFAULT_PATCH), "v2.0")
            Assert.assertEquals(CoreJassProvider.bundledCoreJassFolderForPatch("v1.36"), "reforged")
            Assert.assertTrue(
                Files.readString(buildDir.resolve("core-jass.provenance"))
                    .contains("jassHistoryFolder: Reforged-v2.0.4.23745")
            )
        } finally {
            CoreJassProvider.jassHistoryFileDownloader = previousDownloader
            Files.walk(tmpDir).sorted(Comparator.reverseOrder()).forEach {
                try {
                    Files.deleteIfExists(it)
                } catch (_: Exception) {
                }
            }
        }
    }

    @Test(priority = 10)
    fun testNonBundledPatchDownloadFailureDoesNotUseWrongBundle() {
        val tmpDir = Files.createTempDirectory("grill-core-jass-no-wrong-fallback-test")
        val previousDownloader = CoreJassProvider.jassHistoryFileDownloader
        try {
            CoreJassProvider.jassHistoryFileDownloader = { _, _ -> throw RuntimeException("offline") }

            try {
                SetupApp.ensureCoreJassFiles(tmpDir, "v1.35")
                Assert.fail("Expected v1.35 without a download to fail instead of using an unrelated bundled fallback")
            } catch (e: RuntimeException) {
                Assert.assertTrue(e.message!!.contains("v1.35"), e.message)
            }
        } finally {
            CoreJassProvider.jassHistoryFileDownloader = previousDownloader
            Files.walk(tmpDir).sorted(Comparator.reverseOrder()).forEach {
                try {
                    Files.deleteIfExists(it)
                } catch (_: Exception) {
                }
            }
        }
    }

    @Test(priority = 10)
    fun testLegacyPatchDownloadTriesCapitalizedBlizzardFile() {
        val tmpDir = Files.createTempDirectory("grill-core-jass-legacy-case-test")
        val previousDownloader = CoreJassProvider.jassHistoryFileDownloader
        val attemptedUrls = mutableListOf<String>()
        try {
            CoreJassProvider.jassHistoryFileDownloader = { urls, target ->
                attemptedUrls.addAll(urls)
                val acceptedUrl = urls.firstOrNull {
                    it.endsWith("/Scripts/common.j") || it.endsWith("/Scripts/Blizzard.j")
                } ?: throw RuntimeException("No legacy URL candidate matched")
                val body = "// downloaded from $acceptedUrl\n" + "x".repeat(2048)
                Files.writeString(target, body)
            }

            SetupApp.ensureCoreJassFiles(tmpDir, "v1.27b")

            Assert.assertTrue(
                attemptedUrls.any { it.endsWith("/Scripts/Blizzard.j") },
                "Legacy jass-history dumps use Scripts/Blizzard.j; Grill must try that casing."
            )
            Assert.assertTrue(Files.exists(tmpDir.resolve("_build/common.j")))
            Assert.assertTrue(Files.exists(tmpDir.resolve("_build/blizzard.j")))
        } finally {
            CoreJassProvider.jassHistoryFileDownloader = previousDownloader
            Files.walk(tmpDir).sorted(Comparator.reverseOrder()).forEach {
                try {
                    Files.deleteIfExists(it)
                } catch (_: Exception) {
                }
            }
        }
    }

    @Test(priority = 10)
    fun testCoreJassFilesFollowConfiguredPatch() {
        val tmpDir = Files.createTempDirectory("grill-core-jass-patch-test")
        try {
            SetupApp.ensureCoreJassFiles(tmpDir, CoreJassProvider.DEFAULT_PATCH)
            val reforgedCommonSize = Files.size(tmpDir.resolve("_build/common.j"))

            SetupApp.ensureCoreJassFiles(tmpDir, CoreJassProvider.PRE_129_PATCH)
            val pre129CommonSize = Files.size(tmpDir.resolve("_build/common.j"))

            Assert.assertNotEquals(
                pre129CommonSize,
                reforgedCommonSize,
                "install/generate should refresh _build/common.j when wc3Patch changes"
            )
        } finally {
            Files.walk(tmpDir).sorted(Comparator.reverseOrder()).forEach {
                try {
                    Files.deleteIfExists(it)
                } catch (_: Exception) {
                }
            }
        }
    }

    @Test(priority = 10)
    fun testExistingCoreJassWithoutProvenanceIsPreserved() {
        val tmpDir = Files.createTempDirectory("grill-core-jass-preserve-test")
        try {
            val buildDir = tmpDir.resolve("_build")
            Files.createDirectories(buildDir)
            val customCommon = "// custom common.j\n"
            val customBlizzard = "// custom blizzard.j\n"
            Files.writeString(buildDir.resolve("common.j"), customCommon)
            Files.writeString(buildDir.resolve("blizzard.j"), customBlizzard)

            SetupApp.ensureCoreJassFiles(tmpDir, CoreJassProvider.DEFAULT_PATCH)

            Assert.assertEquals(Files.readString(buildDir.resolve("common.j")), customCommon)
            Assert.assertEquals(Files.readString(buildDir.resolve("blizzard.j")), customBlizzard)
            Assert.assertFalse(
                Files.exists(buildDir.resolve("core-jass.provenance")),
                "Grill must not claim ownership of pre-existing project-local core JASS files"
            )
        } finally {
            Files.walk(tmpDir).sorted(Comparator.reverseOrder()).forEach {
                try {
                    Files.deleteIfExists(it)
                } catch (_: Exception) {
                }
            }
        }
    }

    @Test(priority = 10)
    fun testMatchingProvenanceUsesCachedCoreJass() {
        val tmpDir = Files.createTempDirectory("grill-core-jass-cache-test")
        try {
            val buildDir = tmpDir.resolve("_build")
            Files.createDirectories(buildDir)
            val cachedCommon = "// cached common.j\n" + "x".repeat(2048)
            val cachedBlizzard = "// cached blizzard.j\n" + "y".repeat(2048)
            Files.writeString(buildDir.resolve("common.j"), cachedCommon)
            Files.writeString(buildDir.resolve("blizzard.j"), cachedBlizzard)
            Files.writeString(
                buildDir.resolve("core-jass.provenance"),
                "wc3Patch: ${CoreJassProvider.DEFAULT_PATCH}\n" +
                    "jassHistoryFolder: ${CoreJassProvider.jassHistoryFolderForPatch(CoreJassProvider.DEFAULT_PATCH)}\n"
            )

            SetupApp.ensureCoreJassFiles(tmpDir, CoreJassProvider.DEFAULT_PATCH)

            Assert.assertEquals(Files.readString(buildDir.resolve("common.j")), cachedCommon)
            Assert.assertEquals(Files.readString(buildDir.resolve("blizzard.j")), cachedBlizzard)
        } finally {
            Files.walk(tmpDir).sorted(Comparator.reverseOrder()).forEach {
                try {
                    Files.deleteIfExists(it)
                } catch (_: Exception) {
                }
            }
        }
    }

    @Test(priority = 10)
    fun testUnsupportedPatchFallsBackToDefaultCoreJass() {
        val tmpDir = Files.createTempDirectory("grill-core-jass-unknown-patch-test")
        try {
            val buildDir = tmpDir.resolve("_build")
            Files.createDirectories(buildDir)
            val cachedCommon = "// cached default common.j\n" + "x".repeat(2048)
            val cachedBlizzard = "// cached default blizzard.j\n" + "y".repeat(2048)
            Files.writeString(buildDir.resolve("common.j"), cachedCommon)
            Files.writeString(buildDir.resolve("blizzard.j"), cachedBlizzard)
            Files.writeString(
                buildDir.resolve("core-jass.provenance"),
                "wc3Patch: ${CoreJassProvider.DEFAULT_PATCH}\n" +
                    "jassHistoryFolder: ${CoreJassProvider.jassHistoryFolderForPatch(CoreJassProvider.DEFAULT_PATCH)}\n"
            )

            SetupApp.ensureCoreJassFiles(tmpDir, "some-old-custom-value")

            Assert.assertEquals(Files.readString(buildDir.resolve("common.j")), cachedCommon)
            Assert.assertEquals(Files.readString(buildDir.resolve("blizzard.j")), cachedBlizzard)
            Assert.assertTrue(
                Files.readString(buildDir.resolve("core-jass.provenance")).contains("wc3Patch: ${CoreJassProvider.DEFAULT_PATCH}")
            )
        } finally {
            Files.walk(tmpDir).sorted(Comparator.reverseOrder()).forEach {
                try {
                    Files.deleteIfExists(it)
                } catch (_: Exception) {
                }
            }
        }
    }
}
