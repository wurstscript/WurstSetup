package file

import config.WurstProjectConfigData
import global.Log
import mu.KotlinLogging
import org.eclipse.jgit.api.CreateBranchCommand
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.internal.storage.file.FileRepository
import java.io.File
import java.io.IOException
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import java.util.*


/**
 * Created by Frotty on 17.07.2017.
 */
object DependencyManager {
    private val log = KotlinLogging.logger {}

    fun updateDependencies(projectRoot: Path, projectConfig: WurstProjectConfigData) {
        val depFolders = ArrayList<String>()
        // Iterate through git dependencies
        log.info("\uD83D\uDD37 Installing dependencies..")
        Log.print("Updating dependencies...\n")
        for (dependency in projectConfig.dependencies) {
            val (_, dependencyName, branch) = resolveName(dependency)
            log.info("\t\uD83D\uDD39 Pulling <$dependencyName:$branch>")
            Log.print("Updating dependency - $dependencyName ..")
            val depFolder = projectRoot.resolve("_build/dependencies/$dependencyName")
            if (Files.exists(depFolder)) {
                log.debug("depencency exists locally")
                depFolders.add(depFolder.toAbsolutePath().toString())
                // clean
                if(!cleanRepo(depFolder, branch)) {
                    deleteDirectoryStream(depFolder)
                    cloneRepo(dependency, depFolder)
                } else {
                    // update
                    updateRepo(depFolder, branch)
                }
            } else {
                // clone
                cloneRepo(dependency, depFolder)
                depFolders.add(depFolder.toAbsolutePath().toString())
            }
        }
        if (!depFolders.isEmpty()) {
            try {
                Files.write(projectRoot.resolve("wurst.dependencies"), depFolders, Charset.defaultCharset())
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        log.info("✔ Installed dependencies!")
    }

    fun resolveName(dependency: String): Triple<String, String, String> {
        var dependencyName = dependency.substring(dependency.lastIndexOf("/") + 1)
        var branch = "master"
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
            val dependencyName = dependency.substring(dependency.lastIndexOf("/") + 1)
            Log.print("Checking dependency - $dependencyName ..")
            val depFolder = projectRoot.resolve("_build/dependencies/" + dependencyName)
            if (Files.exists(depFolder)) {
                isGitRepoUpToDate(depFolder)
            } else {
                return true
            }

        }
        return false
    }

    fun cloneRepo(dependency: String, depFolder: Path) {
        val (depURI, _, branch) = resolveName(dependency)
        try {
            Files.createDirectories(depFolder)
        } catch (e: IOException) {
            Log.print("error when trying to create directory")
            throw RuntimeException("Could not create dependency folder", e)
        }
        try {
            Git.cloneRepository().setURI(depURI).setBranch(branch)
                    .setDirectory(depFolder.toFile())
                    .call().use { result -> Log.print("done\n") }
        } catch (e: Exception) {
            Log.print("error!\n")
            e.printStackTrace()
        }
    }

    private fun updateRepo(depFolder: Path, branch: String) {
        try {
            FileRepository(depFolder.resolve(".git").toFile()).use { repository ->
                try {
                    Git(repository).use { git ->
                        val pullResult = git.pull().call()
                        Log.print("done (success=" + pullResult.isSuccessful + ")\n")
                        log.debug("Was pull successful?: " + pullResult.isSuccessful)
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
    }

    @Throws(IOException::class)
    private fun deleteDirectoryStream(path: Path) {
        Files.walk(path)
            .sorted(Comparator.reverseOrder())
            .map<File> { it.toFile() }
            .forEach { it.delete() }
    }


    private fun cleanRepo(depFolder: Path, branch: String): Boolean {
        try {
            FileRepository(depFolder.resolve(".git").toFile()).use { repository ->
                try {
                    Git(repository).use { git ->
                        git.clean().setCleanDirectories(true).setForce(true).call()
                        git.checkout().setAllPaths(true).call()
                        git.reset().call()
                        log.debug("cleaned repo")
                        return prepareRepo(git, branch)
                    }
                } catch (e: Exception) {
                    Log.print("error when trying to clean repository\n")
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
            git.checkout().setCreateBranch(true).setName(branch)
                .setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.SET_UPSTREAM)
                .setStartPoint("origin/$branch").call()
            true
        } catch (e: java.lang.Exception) {
            try {
                git.checkout().setName(branch)
                    .setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.SET_UPSTREAM)
                    .setStartPoint("origin/$branch").call()
                true
            } catch (e: java.lang.Exception) {
                false
            }
        }
    }

    private fun isGitRepoUpToDate(depFolder: Path): Boolean {
        try {
            try {
                FileRepository(depFolder.toFile()).use { repository ->
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
