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
        Assert.assertEquals(info.root, root.toAbsolutePath().normalize())
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
    fun testWarnsWhenProjectPatchAndClientKindDiffer() {
        val root = Files.createTempDirectory("wc3-mismatch")
        Files.writeString(root.resolve("war3.exe"), "")
        val info = Wc3ClientDetector.inspectGameRoot(root)

        val warning = Wc3ClientDetector.mismatchMessage("v2.0", info)

        Assert.assertNotNull(warning)
        Assert.assertTrue(warning!!.contains("project targets Reforged"))
    }
}
