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

        Assert.assertTrue(wordCount <= 900, "AGENTS template grew to $wordCount words (limit: 900)")
        Assert.assertTrue(content.length <= 7000, "AGENTS template grew to ${content.length} characters (limit: 7000)")
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
}
