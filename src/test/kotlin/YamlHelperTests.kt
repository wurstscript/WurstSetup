import config.WurstProjectConfigData
import file.YamlHelper
import org.testng.Assert
import org.testng.annotations.Test
import java.nio.file.Files

class YamlHelperTests {

    @Test
    fun testDumpDoesNotProduceEmptyConfig() {
        val dumped = YamlHelper.dumpProjectConfig(WurstProjectConfigData())
        Assert.assertFalse(dumped.trim() == "--- {}" || dumped.trim() == "{}" || dumped.trim() == "---")
        Assert.assertTrue(dumped.contains("projectName:"))
        Assert.assertTrue(dumped.contains("dependencies:"))
    }

    @Test
    fun testLoadMalformedConfigRecoversWithDefaults() {
        val dir = Files.createTempDirectory("wurstsetup-yaml-test")
        val buildFile = dir.resolve("wurst.build")
        Files.writeString(buildFile, ":\n  - [broken")

        val loaded = YamlHelper.loadProjectConfig(buildFile)
        Assert.assertEquals(loaded.projectName, dir.fileName.toString())
        Assert.assertTrue(Files.exists(buildFile))
        Assert.assertTrue(Files.exists(dir.resolve("wurst.build.bak")))
    }
}
