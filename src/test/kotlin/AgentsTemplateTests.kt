import file.SetupApp
import org.testng.Assert
import org.testng.annotations.Test
import java.nio.file.Files
import java.nio.file.Paths

class AgentsTemplateTests {
    private val templatePath = Paths.get("templates", "AGENTS.md")

    @Test
    fun testTemplateStaysTokenLean() {
        val content = Files.readString(templatePath)
        val wordCount = Regex("""\S+""").findAll(content).count()

        // This is a ceiling, not a target. It prevents accidental manual-sized growth while leaving
        // enough room for a clear always-loaded baseline; advanced guidance belongs on demand.
        Assert.assertTrue(wordCount <= 1000, "AGENTS template grew to $wordCount words (limit: 1000)")
        Assert.assertTrue(content.length <= 8000, "AGENTS template grew to ${content.length} characters (limit: 8000)")
    }

    @Test
    fun testLanguageDocsPreferCompilerMatchedLocalReference() {
        val content = Files.readString(templatePath)
        val localReference = "~/.wurst/wurst-compiler/agent-docs/WURST_LANGUAGE.md"
        val onlineFallback = "https://wurstlang.org/manual.html"
        val localIndex = content.indexOf(localReference)
        val onlineIndex = content.indexOf(onlineFallback)

        Assert.assertTrue(localIndex >= 0, "Missing compiler-matched local language reference")
        Assert.assertTrue(onlineIndex > localIndex, "Online manual must remain a fallback after the local reference")
    }

    @Test
    fun testTemplateGuardsIdiomaticWurst() {
        val content = Files.readString(templatePath)

        listOf(
            "String concatenation invokes `toString()` implicitly",
            "zero-overhead `vec2`/`vec3` tuples",
            "Use `ArrayList<T>`",
            "`class Box<T:>`",
            "null-safe access (`?.`)",
            "GetLocalPlayer()"
        ).forEach { guidance ->
            Assert.assertTrue(content.contains(guidance), "Missing durable template guidance: $guidance")
        }
    }

    @Test
    fun testNewerTemplateDoesNotLookStaleToOlderGrill() {
        val newerMarked = "<!-- WURST_AGENTS_TEMPLATE_VERSION: 2099-01-01 -->\n# AGENTS.md\n"

        Assert.assertNull(SetupApp.agentsTemplateWarning(newerMarked))
    }
}
