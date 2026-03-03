package file

import config.WurstProjectConfigData
import global.Log
import mu.KotlinLogging
import org.eclipse.jgit.api.CreateBranchCommand
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.api.ResetCommand
import org.eclipse.jgit.internal.storage.file.FileRepository
import org.eclipse.jgit.lib.Constants
import java.io.File
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.util.Comparator

/**
 * Created by Frotty on 17.07.2017.
 */
object DependencyManager {
    private val log = KotlinLogging.logger {}

    fun updateDependencies(projectRoot: Path, projectConfig: WurstProjectConfigData) {
        cleanupLegacyDependencyFile(projectRoot)
        log.info("\uD83D\uDD37 Installing dependencies..")
        Log.print("Updating dependencies...\n")
        for (dependency in projectConfig.dependencies) {
            val (depUri, dependencyName, requestedBranch) = resolveName(dependency)
            val branch = resolveBranch(depUri, requestedBranch)
            log.info("\t\uD83D\uDD39 Pulling <$dependencyName:$branch>")
            Log.print("Updating dependency - $dependencyName ..")

            val depFolder = projectRoot.resolve("_build/dependencies/$dependencyName")
            if (Files.exists(depFolder)) {
                log.debug("dependency exists locally")
                if (!refreshRepo(depFolder, depUri, branch)) {
                    deleteDirectoryStream(depFolder)
                    cloneRepo(depUri, branch, depFolder)
                }
            } else {
                cloneRepo(depUri, branch, depFolder)
            }
        }
        log.info("✔ Installed dependencies!")
    }

    private fun cleanupLegacyDependencyFile(projectRoot: Path) {
        val legacyFile = projectRoot.resolve("wurst.dependencies")
        if (Files.exists(legacyFile)) {
            try {
                Files.delete(legacyFile)
                log.info("Removed legacy wurst.dependencies file.")
            } catch (e: IOException) {
                log.warn("Could not remove legacy wurst.dependencies file.", e)
            }
        }
    }

    fun resolveName(dependency: String): Triple<String, String, String> {
        var dependencyName = dependency.substring(dependency.lastIndexOf("/") + 1)
        var branch = ""
        var depURI = dependency

        if (dependencyName.contains(":")) {
            depURI = depURI.substring(0, depURI.lastIndexOf(":"))
            branch = dependencyName.substring(dependencyName.lastIndexOf(":") + 1)
            dependencyName = dependencyName.substring(0, dependencyName.lastIndexOf(":"))
        }
        return Triple(depURI, dependencyName, branch)
    }

    fun isUpdateAvailable(projectRoot: Path, projectConfig: WurstProjectConfigData): Boolean {
        Log.print("Checking dependencies...\n")
        for (dependency in projectConfig.dependencies) {
            val (_, dependencyName, _) = resolveName(dependency)
            Log.print("Checking dependency - $dependencyName ..")
            val depFolder = projectRoot.resolve("_build/dependencies/$dependencyName")
            if (Files.exists(depFolder)) {
                isGitRepoUpToDate(depFolder)
            } else {
                return true
            }
        }
        return false
    }

    fun cloneRepo(dependency: String, depFolder: Path) {
        val (depURI, _, requestedBranch) = resolveName(dependency)
        val branch = resolveBranch(depURI, requestedBranch)
        cloneRepo(depURI, branch, depFolder)
    }

    private fun cloneRepo(depURI: String, branch: String, depFolder: Path) {
        try {
            Files.createDirectories(depFolder)
        } catch (e: IOException) {
            Log.print("error when trying to create directory")
            throw RuntimeException("Could not create dependency folder", e)
        }
        try {
            Git.cloneRepository()
                .setURI(depURI)
                .setBranch(branch)
                .setDirectory(depFolder.toFile())
                .call()
                .use { Log.print("done\n") }
        } catch (e: Exception) {
            Log.print("error!\n")
            e.printStackTrace()
        }
    }

    @Throws(IOException::class)
    private fun deleteDirectoryStream(path: Path) {
        Files.walk(path)
            .sorted(Comparator.reverseOrder())
            .map<File> { it.toFile() }
            .forEach { it.delete() }
    }

    private fun refreshRepo(depFolder: Path, depUri: String, branch: String): Boolean {
        try {
            FileRepository(depFolder.resolve(".git").toFile()).use { repository ->
                try {
                    Git(repository).use { git ->
                        repository.config.setString("remote", "origin", "url", depUri)
                        repository.config.save()
                        git.fetch()
                            .setRemote("origin")
                            .setRemoveDeletedRefs(true)
                            .call()
                        if (!prepareRepo(git, branch)) {
                            return false
                        }
                        // Keep dependency folder exactly aligned with origin/<branch>.
                        git.reset()
                            .setMode(ResetCommand.ResetType.HARD)
                            .setRef("origin/$branch")
                            .call()
                        git.clean()
                            .setCleanDirectories(true)
                            .setForce(true)
                            .setIgnore(false)
                            .call()

                        Log.print("done\n")
                        log.debug("Refreshed repo to origin/$branch")
                        return true
                    }
                } catch (e: Exception) {
                    Log.print("error when trying to refresh repository\n")
                    e.printStackTrace()
                }
            }
        } catch (e: Exception) {
            Log.print("error when trying open repository")
            e.printStackTrace()
        }
        return false
    }

    private fun prepareRepo(git: Git, branch: String): Boolean {
        return try {
            git.checkout()
                .setCreateBranch(true)
                .setName(branch)
                .setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.SET_UPSTREAM)
                .setStartPoint("origin/$branch")
                .call()
            true
        } catch (e: Exception) {
            try {
                git.checkout()
                    .setName(branch)
                    .call()
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    private fun resolveBranch(depUri: String, requestedBranch: String): String {
        if (requestedBranch.isNotBlank()) {
            return requestedBranch
        }
        return getDefaultBranch(depUri) ?: "master"
    }

    private fun getDefaultBranch(depUri: String): String? {
        return try {
            val fromGit = getDefaultBranchFromGit(depUri)
            if (!fromGit.isNullOrBlank()) {
                return fromGit
            }
            val refs = Git.lsRemoteRepository()
                .setRemote(depUri)
                .setHeads(true)
                .setTags(false)
                .call()

            val branchNames = refs.mapNotNull { ref ->
                val name = ref.name
                if (name.startsWith(Constants.R_HEADS)) name.removePrefix(Constants.R_HEADS) else null
            }.toSet()
            when {
                branchNames.contains("main") -> "main"
                branchNames.contains("master") -> "master"
                branchNames.isNotEmpty() -> branchNames.first()
                else -> null
            }
        } catch (e: Exception) {
            log.warn("Could not determine default branch for <$depUri>, falling back.", e)
            null
        }
    }

    private fun getDefaultBranchFromGit(depUri: String): String? {
        return try {
            val out = ByteArrayOutputStream()
            val err = ByteArrayOutputStream()
            val p = ProcessBuilder("git", "ls-remote", "--symref", depUri, "HEAD")
                .redirectErrorStream(false)
                .start()
            p.inputStream.copyTo(out)
            p.errorStream.copyTo(err)
            if (p.waitFor() != 0) {
                return null
            }
            val line = out.toString(Charsets.UTF_8.name())
                .lineSequence()
                .firstOrNull { it.startsWith("ref: refs/heads/") && it.endsWith("\tHEAD") }
            line?.substringAfter("ref: refs/heads/")?.substringBefore("\tHEAD")
        } catch (_: Exception) {
            null
        }
    }

    private fun isGitRepoUpToDate(depFolder: Path): Boolean {
        try {
            try {
                FileRepository(depFolder.resolve(".git").toFile()).use { repository ->
                    try {
                        Git(repository).use { git ->
                            git.lsRemote().setHeads(true).call()
                            val status = git.status().call()
                            if (status.hasUncommittedChanges()) {
                                Log.print("You have modified files in your dependencies folder.")
                            } else if (status.isClean) {
                                return true
                            }
                        }
                    } catch (e: Exception) {
                        Log.print("error when trying to fetch remote\n")
                        e.printStackTrace()
                    }
                }
            } catch (e: Exception) {
                Log.print("error when trying open repository")
                e.printStackTrace()
            }
        } catch (ignored: Exception) {
        }
        return false
    }
}
