import file.Wc3ClientDetector
import org.testng.Assert
import org.testng.annotations.Test
import java.nio.file.Files

class Wc3ClientDetectorTests {
    @Test
    fun testClassifiesReforgedLayout() {
        val root = Files.createTempDirectory("wc3-reforged")
        val exe = Files.createDirectories(root.resolve("_retail_").resolve("x86_64")).resolve("Warcraft III.exe")
        Files.writeString(exe, "")

        val info = Wc3ClientDetector.inspectGameRoot(root)!!

        Assert.assertEquals(info.kind, Wc3ClientDetector.ClientKind.REFORGED)
        Assert.assertEquals(info.root, root.resolve("_retail_").toAbsolutePath().normalize())
        Assert.assertEquals(info.installationRoot, root.toAbsolutePath().normalize())
    }

    @Test
    fun testClassifiesPre129Layout() {
        val root = Files.createTempDirectory("wc3-pre129")
        Files.writeString(root.resolve("war3.exe"), "")

        val info = Wc3ClientDetector.inspectGameRoot(root)!!

        Assert.assertEquals(info.kind, Wc3ClientDetector.ClientKind.PRE_129)
    }

    @Test
    fun testClassifiesClassicLayout() {
        val root = Files.createTempDirectory("wc3-classic")
        val exe = Files.createDirectories(root.resolve("x86_64")).resolve("Warcraft III.exe")
        Files.writeString(exe, "")

        val info = Wc3ClientDetector.inspectGameRoot(root)!!

        Assert.assertEquals(info.kind, Wc3ClientDetector.ClientKind.CLASSIC)
    }

    @Test
    fun testRejectsUnsupportedExplicitFilePath() {
        val root = Files.createTempDirectory("wc3-explicit-file")
        val textFile = root.resolve("notes.txt")
        Files.writeString(textFile, "not a wc3 executable")

        val info = Wc3ClientDetector.inspectGameRoot(textFile)

        Assert.assertNull(info)
    }

    @Test
    fun testAcceptsSupportedExplicitFilePath() {
        val root = Files.createTempDirectory("wc3-explicit-exe")
        val exe = root.resolve("war3.exe")
        Files.writeString(exe, "")

        val info = Wc3ClientDetector.inspectGameRoot(exe)!!

        Assert.assertEquals(info.kind, Wc3ClientDetector.ClientKind.PRE_129)
        Assert.assertEquals(info.root, root.toAbsolutePath().normalize())
        Assert.assertEquals(info.installationRoot, root.toAbsolutePath().normalize())
    }

    @Test
    fun testWarnsWhenProjectPatchAndClientKindDiffer() {
        val root = Files.createTempDirectory("wc3-mismatch")
        Files.writeString(root.resolve("war3.exe"), "")
        val info = Wc3ClientDetector.inspectGameRoot(root)

        val warning = Wc3ClientDetector.mismatchMessage("v2.0", info)

        Assert.assertNotNull(warning)
        Assert.assertTrue(warning!!.contains("project targets Reforged"))
    }

    @Test
    fun testReadsExactReforgedVersionFromBuildInfo() {
        val root = Files.createTempDirectory("wc3-versioned-reforged")
        val exe = Files.createDirectories(root.resolve("_retail_").resolve("x86_64")).resolve("Warcraft III.exe")
        Files.writeString(exe, "")
        Files.writeString(
            root.resolve(".build.info"),
            "Branch!STRING:0|Active!DEC:1|Version!STRING:0|Product!STRING:0\n" +
                "eu|1|3.0.0.24268|w3\n"
        )

        val info = Wc3ClientDetector.inspectGameRoot(root)!!

        Assert.assertEquals(info.version, "3.0.0.24268")
        Assert.assertEquals(info.patchTarget, "v3.0")
        Assert.assertNull(Wc3ClientDetector.mismatchMessage("v3.0", info))
        Assert.assertTrue(Wc3ClientDetector.mismatchMessage("v2.0", info)!!.contains("grill patch align"))
    }

    @Test
    fun testFindsBuildInfoWhenConfiguredPathIsExecutableDirectory() {
        val root = Files.createTempDirectory("wc3-configured-bin")
        val executableDirectory = Files.createDirectories(root.resolve("_retail_").resolve("x86_64"))
        Files.writeString(executableDirectory.resolve("Warcraft III.exe"), "")
        Files.writeString(
            root.resolve(".build.info"),
            "Active!DEC:1|Version!STRING:0|Product!STRING:0\n" +
                "1|3.0.0.24268|w3\n"
        )

        val info = Wc3ClientDetector.inspectGameRoot(executableDirectory)!!

        Assert.assertEquals(info.root, root.resolve("_retail_").toAbsolutePath().normalize())
        Assert.assertEquals(info.installationRoot, root.toAbsolutePath().normalize())
        Assert.assertEquals(info.patchTarget, "v3.0")
    }

    @Test
    fun testIgnoresInactiveAndNonWarcraftBuildInfoRows() {
        val root = Files.createTempDirectory("wc3-multi-product")
        val exe = Files.createDirectories(root.resolve("_retail_").resolve("x86_64")).resolve("Warcraft III.exe")
        Files.writeString(exe, "")
        Files.writeString(
            root.resolve(".build.info"),
            "Active!DEC:1|Version!STRING:0|Product!STRING:0\n" +
                "1|99.0.0.1|other\n" +
                "0|2.0.4.23745|w3\n" +
                "1|3.0.0.24268|w3\n"
        )

        val info = Wc3ClientDetector.inspectGameRoot(root)!!

        Assert.assertEquals(info.version, "3.0.0.24268")
        Assert.assertEquals(info.patchTarget, "v3.0")
    }

    @Test
    fun testSelectsPtrBuildInfoRowForExplicitPtrPath() {
        val root = Files.createTempDirectory("wc3-retail-ptr")
        val retailExe = Files.createDirectories(root.resolve("_retail_").resolve("x86_64")).resolve("Warcraft III.exe")
        val ptrDirectory = Files.createDirectories(root.resolve("_ptr_").resolve("x86_64"))
        Files.writeString(retailExe, "")
        Files.writeString(ptrDirectory.resolve("Warcraft III.exe"), "")
        Files.writeString(
            root.resolve(".build.info"),
            "Active!DEC:1|Version!STRING:0|Product!STRING:0\n" +
                "1|3.0.0.24268|w3\n" +
                "1|2.0.4.23745|w3t\n"
        )

        val info = Wc3ClientDetector.inspectGameRoot(ptrDirectory)!!

        Assert.assertEquals(info.executable, ptrDirectory.resolve("Warcraft III.exe"))
        Assert.assertEquals(info.root, ptrDirectory.parent)
        Assert.assertEquals(info.installationRoot, root.toAbsolutePath().normalize())
        Assert.assertEquals(info.version, "2.0.4.23745")
        Assert.assertEquals(info.patchTarget, "v2.0")
    }

    @Test
    fun testSelectsRetailBuildInfoRowForInstallationRoot() {
        val root = Files.createTempDirectory("wc3-retail-ptr-root")
        val retailDirectory = Files.createDirectories(root.resolve("_retail_").resolve("x86_64"))
        val ptrDirectory = Files.createDirectories(root.resolve("_ptr_").resolve("x86_64"))
        Files.writeString(retailDirectory.resolve("Warcraft III.exe"), "")
        Files.writeString(ptrDirectory.resolve("Warcraft III.exe"), "")
        Files.writeString(
            root.resolve(".build.info"),
            "Active!DEC:1|Version!STRING:0|Product!STRING:0\n" +
                "1|2.0.4.23745|w3t\n" +
                "1|3.0.0.24268|w3\n"
        )

        val info = Wc3ClientDetector.inspectGameRoot(root)!!

        Assert.assertEquals(info.executable, retailDirectory.resolve("Warcraft III.exe"))
        Assert.assertEquals(info.root, retailDirectory.parent)
        Assert.assertEquals(info.installationRoot, root.toAbsolutePath().normalize())
        Assert.assertEquals(info.version, "3.0.0.24268")
        Assert.assertEquals(info.patchTarget, "v3.0")
    }
}
