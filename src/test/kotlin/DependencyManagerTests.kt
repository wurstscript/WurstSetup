import config.newProjectConfig
import file.DependencyManager
import org.testng.Assert
import org.testng.SkipException
import org.testng.annotations.Test
import java.nio.file.Files

class DependencyManagerTests {
    @Test
    fun localOnlyUpdateCopiesLocalDependencyAndSkipsRemoteDependencies() {
        val project = Files.createTempDirectory("grill-local-dependency-project")
        val source = Files.createTempDirectory("grill-local-dependency-source")
        Files.writeString(source.resolve("Dependency.wurst"), "package Dependency\n")
        val localUrl = source.toUri().toString()
        val config = newProjectConfig(
            dependencies = listOf("https://example.invalid/private-dependency", localUrl)
        )

        DependencyManager.updateDependencies(project, config, localDependenciesOnly = true)

        Assert.assertTrue(Files.exists(project.resolve("_build/dependencies/${source.fileName}/Dependency.wurst")))
        Assert.assertFalse(Files.exists(project.resolve("_build/dependencies/private-dependency")))
    }

    @Test
    fun localDependencyWithSymlinkIsRejectedBeforeReplacingExistingCopy() {
        val project = Files.createTempDirectory("grill-symlink-dependency-project")
        val source = Files.createTempDirectory("grill-symlink-dependency-source")
        val linkedDirectory = Files.createTempDirectory("grill-symlink-target")
        Files.writeString(linkedDirectory.resolve("Nested.wurst"), "package Nested\n")
        try {
            Files.createSymbolicLink(source.resolve("linked"), linkedDirectory)
        } catch (_: Exception) {
            throw SkipException("The current environment does not allow creating symbolic links.")
        }

        val localUrl = source.toUri().toString()
        val dependencyName = source.fileName.toString()
        val existingCopy = project.resolve("_build/dependencies/$dependencyName")
        Files.createDirectories(existingCopy)
        Files.writeString(existingCopy.resolve("PreviouslyInstalled.wurst"), "package PreviouslyInstalled\n")

        var rejected = false
        try {
            DependencyManager.updateDependencies(project, newProjectConfig(dependencies = listOf(localUrl)))
        } catch (_: IllegalArgumentException) {
            rejected = true
        }

        Assert.assertTrue(rejected, "A local dependency containing a symbolic link should be rejected.")
        Assert.assertTrue(Files.exists(existingCopy.resolve("PreviouslyInstalled.wurst")))
    }
}
