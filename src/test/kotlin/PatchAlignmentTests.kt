import config.newProjectConfig
import config.WurstProjectConfig
import file.CoreJassProvider
import file.CLICommand
import file.SetupApp
import file.SetupMain
import org.testng.Assert
import org.testng.annotations.Test
import java.nio.file.Files
import java.nio.file.Paths

class PatchAlignmentTests {
    @Test
    fun testParsesPatchAlignWithExplicitGamePath() {
        val setup = SetupMain()
        val gamePath = Paths.get("C:\\Games\\Warcraft III")

        setup.parseArgs(listOf("patch", "align", "--wc3-path", gamePath.toString()))

        Assert.assertEquals(setup.command, CLICommand.PATCH)
        Assert.assertEquals(setup.commandArg, "align")
        Assert.assertEquals(setup.gamePath, gamePath)
    }

    @Test
    fun testMapsClientVersionsToSupportedPatchLines() {
        Assert.assertEquals(CoreJassProvider.patchTargetForClientVersion("3.0.0.24268"), "v3.0")
        Assert.assertEquals(CoreJassProvider.patchTargetForClientVersion("2.0.4.23745"), "v2.0")
        Assert.assertEquals(CoreJassProvider.patchTargetForClientVersion("1.36.1.20719"), "v1.36")
        Assert.assertNull(CoreJassProvider.patchTargetForClientVersion("4.0.0.1"))
    }

    @Test
    fun testV2ProjectsUseTheMaintenanceStdlibBranch() {
        Assert.assertEquals(
            SetupApp.stdlibDependencyForPatch("v2.0"),
            "https://github.com/wurstscript/wurstStdlib2:v2.0"
        )
        Assert.assertEquals(
            SetupApp.stdlibDependencyForPatch("v3.0"),
            "https://github.com/wurstscript/wurstStdlib2"
        )
        Assert.assertEquals(
            SetupApp.stdlibDependencyForPatch("v1.36"),
            "https://github.com/wurstscript/wurstStdlib2:v2.0"
        )
    }

    @Test
    fun testAlignmentUpdatesPatchAndOfficialStdlibTogether() {
        val config = newProjectConfig(
            projectName = "migration-test",
            dependencies = listOf(
                "https://github.com/wurstscript/wurstStdlib2:v2.0",
                "https://github.com/example/custom-library"
            ),
            wc3Patch = "v2.0"
        )

        val aligned = SetupApp.alignedProjectConfig(config, "v3.0")

        Assert.assertEquals(aligned.wc3Patch, "v3.0")
        Assert.assertEquals(
            aligned.dependencies,
            listOf(
                "https://github.com/wurstscript/wurstStdlib2",
                "https://github.com/example/custom-library"
            )
        )
    }

    @Test
    fun testAlignmentDoesNotRewriteCustomStdlibForks() {
        val customFork = "https://github.com/example/wurstStdlib2:custom"
        val config = newProjectConfig(
            projectName = "custom-stdlib",
            dependencies = listOf(customFork),
            wc3Patch = "v2.0"
        )

        val aligned = SetupApp.alignedProjectConfig(config, "v3.0")

        Assert.assertEquals(aligned.dependencies, listOf(customFork))
    }

    @Test
    fun testExistingV2TargetPinsStdlibBeforeDependencyInstall() {
        val config = newProjectConfig(
            projectName = "safe-install",
            dependencies = listOf("https://github.com/wurstscript/wurstStdlib2"),
            wc3Patch = "v2.0"
        )

        val aligned = SetupApp.alignOfficialStdlibDependency(config, "v2.0")

        Assert.assertEquals(aligned.wc3Patch, "v2.0")
        Assert.assertEquals(
            aligned.dependencies,
            listOf("https://github.com/wurstscript/wurstStdlib2:v2.0")
        )
    }

    @Test
    fun testManagedCoreJassDetectsStalePatchAndInvalidFiles() {
        val projectRoot = Files.createTempDirectory("wurstsetup-core-jass-alignment")
        val buildFolder = Files.createDirectories(projectRoot.resolve("_build"))
        Files.writeString(buildFolder.resolve("core-jass.provenance"), "wc3Patch: v2.0\n")
        Files.writeString(buildFolder.resolve("common.j"), "x".repeat(2048))
        Files.writeString(buildFolder.resolve("blizzard.j"), "x".repeat(2048))

        Assert.assertTrue(CoreJassProvider.managedFilesNeedRefresh(projectRoot, "v3.0"))
        Assert.assertFalse(CoreJassProvider.managedFilesNeedRefresh(projectRoot, "v2.0"))

        Files.writeString(buildFolder.resolve("common.j"), "invalid")
        Assert.assertTrue(CoreJassProvider.managedFilesNeedRefresh(projectRoot, "v2.0"))
    }

    @Test
    fun testConfiguredGamePathSupportsJsonc() {
        val projectRoot = Files.createTempDirectory("wurstsetup-jsonc-settings")
        val vscodeFolder = Files.createDirectories(projectRoot.resolve(".vscode"))
        Files.writeString(
            vscodeFolder.resolve("settings.json"),
            """
            {
              // Warcraft III installation used by the Wurst extension.
              "wurst.wc3path": "C:\\Games\\Warcraft III",
            }
            """.trimIndent()
        )

        Assert.assertEquals(
            WurstProjectConfig.configuredGamePath(projectRoot),
            Paths.get("C:\\Games\\Warcraft III")
        )
    }
}
