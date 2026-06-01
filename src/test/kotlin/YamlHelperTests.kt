import com.fasterxml.jackson.databind.ObjectMapper
import config.ScriptMode
import config.WurstProjectBuildMapData
import config.WurstProjectBuildOptionFlagsData
import config.WurstProjectBuildScenarioData
import config.newProjectConfig
import file.YamlHelper
import org.testng.Assert
import org.testng.annotations.Test
import java.nio.file.Files

class YamlHelperTests {

    @Test
    fun testDumpDoesNotProduceEmptyConfig() {
        val dumped = YamlHelper.dumpProjectConfig(newProjectConfig())
        Assert.assertFalse(dumped.trim() == "--- {}" || dumped.trim() == "{}" || dumped.trim() == "---")
        Assert.assertTrue(dumped.contains("projectName:"))
        Assert.assertTrue(dumped.contains("dependencies:"))
        Assert.assertFalse(dumped.contains("buildMapData:"))
        Assert.assertFalse(dumped.contains("loadingScreen: null"))
    }

    @Test
    fun testDumpOnlyEmitsConfiguredBuildMapData() {
        val dumped = YamlHelper.dumpProjectConfig(
            newProjectConfig(
                projectName = "mapped",
                buildMapData = WurstProjectBuildMapData(
                    "Known Map Name",
                    "",
                    "",
                    WurstProjectBuildScenarioData.empty(),
                    WurstProjectBuildOptionFlagsData.empty(),
                    emptyList(),
                    emptyList()
                )
            )
        )

        Assert.assertTrue(dumped.contains("buildMapData:"))
        Assert.assertTrue(dumped.contains("name: Known Map Name"))
        Assert.assertFalse(dumped.contains("fileName:"))
        Assert.assertFalse(dumped.contains("author:"))
        Assert.assertFalse(dumped.contains("scenarioData:"))
        Assert.assertFalse(dumped.contains("optionsFlags:"))
        Assert.assertFalse(dumped.contains("players: []"))
        Assert.assertFalse(dumped.contains("forces: []"))
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

    @Test
    fun testWc3PatchVersionNormalizesToSchemaValue() {
        val dumped = YamlHelper.dumpProjectConfig(
            newProjectConfig(projectName = "versioned", wc3Patch = "Reforged-v1.36.1.20719-w3-51d40ee")
        )
        val dir = Files.createTempDirectory("wurstsetup-yaml-version-test")
        val buildFile = dir.resolve("wurst.build")
        Files.writeString(buildFile, dumped)

        val loaded = YamlHelper.loadProjectConfig(buildFile)
        Assert.assertEquals(loaded.wc3Patch, "v1.36")
    }

    @Test
    fun testLoadIgnoresUnknownNestedFieldsAndEnumValues() {
        val dir = Files.createTempDirectory("wurstsetup-yaml-compat-test")
        val buildFile = dir.resolve("wurst.build")
        Files.writeString(
            buildFile,
            """
            projectName: compat
            scriptMode: lua
            obsoleteRootValue: true
            buildMapData:
              obsoleteNestedValue: true
              players:
                - id: 0
                  name: Player 1
                  race: SPACE_ORC
                  obsoletePlayerValue: true
            """.trimIndent()
        )

        val loaded = YamlHelper.loadProjectConfig(buildFile)

        Assert.assertEquals(loaded.projectName, "compat")
        Assert.assertEquals(loaded.scriptMode, ScriptMode.LUA)
        Assert.assertEquals(loaded.buildMapData.players.size, 1)
        Assert.assertNull(loaded.buildMapData.players[0].race)
    }

    @Test
    fun testWbSchemaIsValidJson() {
        val schema = javaClass.classLoader.getResource("wbschema.json")!!.readText()
        ObjectMapper().readTree(schema)
    }
}
