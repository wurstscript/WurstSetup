package file

import global.Log
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.internal.storage.file.FileRepository
import java.io.IOException
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

/**
 * Created by Frotty on 17.07.2017.
 */
object DependencyManager {

    fun updateDependencies(projectRoot: Path, projectConfig: WurstProjectConfigData) {
        val depFolders = ArrayList<String>()
        // Iterate through git dependencies
        Log.print("Updating dependencies...\n")
        for (dependency in projectConfig.dependencies) {
            val dependencyName = dependency.substring(dependency.lastIndexOf("/") + 1)
            Log.print("Updating dependency - $dependencyName ..")
            val depFolder = projectRoot.resolve("_build/dependencies/" + dependencyName)
            if (Files.exists(depFolder)) {
                depFolders.add(depFolder.toAbsolutePath().toString())
                // clean
                try {
                    FileRepository(depFolder.resolve(".git").toString()).use({ repository ->
                        try {
                            Git(repository).use { git ->
                                git.clean().setCleanDirectories(true).setForce(true).call()
                                git.stashCreate().call()
                            }
                        } catch (e: Exception) {
                            Log.print("error when trying to clean repository\n")
                            e.printStackTrace()
                        }
                    })
                } catch (e: Exception) {
                    Log.print("error when trying open repository")
                    e.printStackTrace()
                }

                // update
                try {
                    FileRepository(depFolder.resolve(".git").toString()).use({ repository ->
                        try {
                            Git(repository).use { git ->
                                git.reset().call()
                                val pullResult = git.pull().call()
                                Log.print("done\n")
                                println("Messages: " + pullResult.fetchResult)
                            }
                        } catch (e: Exception) {
                            Log.print("error when trying to fetch remote\n")
                            e.printStackTrace()
                        }
                    })
                } catch (e: Exception) {
                    Log.print("error when trying open repository")
                    e.printStackTrace()
                }


            } else {
                try {
                    Files.createDirectories(depFolder)
                } catch (e: IOException) {
                    Log.print("error when trying to create directory")
                    throw RuntimeException("Could not create dependency folder", e)
                }

                depFolders.add(depFolder.toAbsolutePath().toString())
                // clone
                try {
                    Git.cloneRepository().setURI(dependency)
                            .setDirectory(depFolder.toFile())
                            .call().use { result -> Log.print("done\n") }
                } catch (e: Exception) {
                    Log.print("error!\n")
                    e.printStackTrace()
                }
            }
        }
        if (!depFolders.isEmpty()) {
            try {
                Files.write(projectRoot!!.resolve("wurst.dependencies"), depFolders, Charset.defaultCharset())
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    fun isUpdateAvailable(projectRoot: Path, projectConfig: WurstProjectConfigData): Boolean {
        Log.print("Checking dependencies...\n")
        for (dependency in projectConfig.dependencies) {
            val dependencyName = dependency.substring(dependency.lastIndexOf("/") + 1)
            Log.print("Checking dependency - $dependencyName ..")
            val depFolder = projectRoot.resolve("_build/dependencies/" + dependencyName)
            if (Files.exists(depFolder)) {
                // update
                try {
                    try {
                        FileRepository(depFolder.toFile()).use { repository ->
                            try {
                                Git(repository).use { git ->
                                    val refs = git.lsRemote().setHeads(true).call()
                                    val status = git.status().call()
                                    if (status.hasUncommittedChanges()) {
                                        Log.print("You have modified files in your dependencies folder.")
                                    } else if (status.isClean) {

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

            } else {
                return true
            }

        }
        return false
    }
}
