package file

import config.WurstProjectConfigData
import global.Log
import logging.KotlinLogging
import org.eclipse.jgit.api.CreateBranchCommand
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.api.ResetCommand
import org.eclipse.jgit.internal.storage.file.FileRepository
import org.eclipse.jgit.lib.Constants
import java.io.File
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.URI
import java.nio.file.Files
import java.nio.file.Path
import java.util.Comparator

/**
 * Created by Frotty on 17.07.2017.
 */
object DependencyManager {
    private val log = KotlinLogging.logger {}
    var debug = false

    fun isLocalDependency(dependency: String): Boolean =
        runCatching { URI(dependency).scheme.equals("file", ignoreCase = true) }.getOrDefault(false)

    private fun localDependencyPath(dependency: String): Path {
        val uri = URI(dependency)
        require(uri.scheme.equals("file", ignoreCase = true)) { "Local dependencies must use a file: URL." }
        require(uri.query == null && uri.fragment == null) { "Local dependency URLs cannot contain a query or fragment." }
        return Path.of(uri).toAbsolutePath().normalize()
    }

    private fun hasSymbolicLinkComponent(path: Path): Boolean {
        val normalized = path.toAbsolutePath().normalize()
        var current = normalized.root ?: return Files.isSymbolicLink(normalized)
        for (component in normalized) {
            current = current.resolve(component)
            if (Files.isSymbolicLink(current)) return true
        }
        return false
    }

    private fun dependencyFiles(root: Path): List<Path> {
        require(!hasSymbolicLinkComponent(root)) {
            "Local dependency paths cannot contain symbolic links: $root"
        }
        require(Files.isDirectory(root)) { "Local dependency directory does not exist: $root" }
        val entries = Files.walk(root).use { paths ->
            paths.filter { path ->
                path != root && path.none { it.toString() == ".git" }
            }.toList()
        }
        require(entries.none(Files::isSymbolicLink)) {
            "Local dependency directories cannot contain symbolic links: $root"
        }
        return entries.sorted()
    }

    private fun localDependencyMatches(dependency: String, destination: Path): Boolean = try {
        val source = localDependencyPath(dependency)
        if (!Files.isDirectory(source) || !Files.isDirectory(destination) || source == destination ||
            destination.startsWith(source) || source.startsWith(destination)) {
            false
        } else {
            val sourceFiles = dependencyFiles(source)
            val destinationFiles = dependencyFiles(destination)
            sourceFiles.map { source.relativize(it) } == destinationFiles.map { destination.relativize(it) } &&
                sourceFiles.zip(destinationFiles).all { (left, right) ->
                    Files.isDirectory(left) && Files.isDirectory(right) ||
                        (Files.isRegularFile(left) && Files.isRegularFile(right) && Files.mismatch(left, right) == -1L)
                }
        }
    } catch (_: Exception) {
        false
    }

    private fun copyLocalDependency(dependency: String, destination: Path) {
        val source = localDependencyPath(dependency)
        require(Files.isDirectory(source)) { "Local dependency directory does not exist: $source" }
        val normalizedDestination = destination.toAbsolutePath().normalize()
        require(source != normalizedDestination && !normalizedDestination.startsWith(source) && !source.startsWith(normalizedDestination)) {
            "A local dependency cannot contain its _build destination."
        }
        val sourceEntries = dependencyFiles(source)
        if (Files.exists(destination)) deleteDirectoryStream(destination)
        Files.createDirectories(destination)
        for (sourcePath in sourceEntries) {
            val relative = source.relativize(sourcePath)
            val target = destination.resolve(relative)
            if (Files.isDirectory(sourcePath)) {
                Files.createDirectories(target)
            } else if (Files.isRegularFile(sourcePath)) {
                Files.createDirectories(target.parent)
                Files.copy(sourcePath, target)
            }
        }
    }

    fun updateDependencies(
        projectRoot: Path,
        projectConfig: WurstProjectConfigData,
        localDependenciesOnly: Boolean = false
    ) {
        cleanupLegacyDependencyFile(projectRoot)
        log.info("\uD83D\uDD37 Installing dependencies..")
        Log.print("Updating dependencies...\n")
        for (dependency in projectConfig.dependencies) {
            val (depUri, dependencyName, requestedBranch) = resolveName(dependency)
            val isLocal = isLocalDependency(depUri)
            if (localDependenciesOnly && !isLocal) continue
            val depFolder = projectRoot.resolve("_build/dependencies/$dependencyName")
            if (isLocal) {
                copyLocalDependency(depUri, depFolder)
                Log.print("Updated local dependency - $dependencyName\n")
                continue
            }
            val branch = resolveBranch(depUri, requestedBranch)
            log.info("\t\uD83D\uDD39 Pulling <$dependencyName:$branch>")
            Log.print("Updating dependency - $dependencyName ..")

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
        var dependencyName = if (isLocalDependency(dependency)) {
            localDependencyPath(dependency).fileName?.toString().orEmpty()
        } else {
            dependency.substring(dependency.lastIndexOf("/") + 1)
        }
        var branch = ""
        var depURI = dependency

        if (!isLocalDependency(dependency) && dependencyName.contains(":")) {
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
            if (isLocalDependency(resolveName(dependency).first)) {
                if (!localDependencyMatches(dependency, depFolder)) return true
                continue
            }
            if (Files.exists(depFolder)) {
                isGitRepoUpToDate(depFolder)
            } else {
                return true
            }
        }
        return false
    }

    fun hasOutdatedDependencies(projectRoot: Path, projectConfig: WurstProjectConfigData): Boolean {
        Log.print("Checking dependencies...\n")
        for (dependency in projectConfig.dependencies) {
            val (depUri, dependencyName, requestedBranch) = resolveName(dependency)
            val depFolder = projectRoot.resolve("_build/dependencies/$dependencyName")
            if (isLocalDependency(depUri)) {
                if (!localDependencyMatches(depUri, depFolder)) {
                    Log.print("outdated\n")
                    return true
                }
                Log.print("ok\n")
                continue
            }
            val branch = resolveBranch(depUri, requestedBranch)
            Log.print("Checking dependency - $dependencyName ..")

            if (!Files.exists(depFolder.resolve(".git"))) {
                Log.print("missing\n")
                return true
            }

            if (isDependencyOutdated(depFolder, depUri, branch)) {
                Log.print("outdated\n")
                return true
            }
            Log.print("ok\n")
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
                .setCredentialsProvider(GitCredentialProvider)
                .call()
                .use { Log.print("done\n") }
        } catch (e: Exception) {
            Log.print("error!\n")
            reportDependencyError(depURI, branch, e)
            throw RuntimeException("Could not clone dependency <$depURI:$branch>", e)
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
                            .setCredentialsProvider(GitCredentialProvider)
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
                    reportDependencyError(depUri, branch, e)
                }
            }
        } catch (e: Exception) {
            Log.print("error when trying open repository")
            if (debug) {
                e.printStackTrace()
            } else {
                log.error("❌ Could not open dependency repo at $depFolder.")
                log.info("Try: delete that dependency folder and run `grill install` again.")
            }
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
                .setCredentialsProvider(GitCredentialProvider)
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
                            git.lsRemote()
                                .setHeads(true)
                                .setCredentialsProvider(GitCredentialProvider)
                                .call()
                            val status = git.status().call()
                            if (status.hasUncommittedChanges()) {
                                Log.print("You have modified files in your dependencies folder.")
                            } else if (status.isClean) {
                                return true
                            }
                        }
                    } catch (e: Exception) {
                        Log.print("error when trying to fetch remote\n")
                        if (debug) {
                            e.printStackTrace()
                        } else {
                            log.warn("Could not fetch dependency status: ${e.message}")
                        }
                    }
                }
            } catch (e: Exception) {
                Log.print("error when trying open repository")
                if (debug) {
                    e.printStackTrace()
                } else {
                    log.warn("Could not open dependency repo: ${e.message}")
                }
            }
        } catch (ignored: Exception) {
        }
        return false
    }

    private fun isDependencyOutdated(depFolder: Path, depUri: String, branch: String): Boolean {
        return try {
            FileRepository(depFolder.resolve(".git").toFile()).use { repository ->
                repository.config.setString("remote", "origin", "url", depUri)
                repository.config.save()
                Git(repository).use { git ->
                    git.fetch()
                        .setRemote("origin")
                        .setRemoveDeletedRefs(true)
                        .setCredentialsProvider(GitCredentialProvider)
                        .call()
                }
                val localHead = repository.resolve(Constants.HEAD)
                val remoteHead = repository.resolve("refs/remotes/origin/$branch")
                localHead == null || remoteHead == null || localHead != remoteHead
            }
        } catch (e: Exception) {
            log.warn("Could not verify dependency at <$depFolder>.", e)
            true
        }
    }

    private fun reportDependencyError(depURI: String, branch: String, e: Exception) {
        val message = e.message ?: e.javaClass.simpleName
        log.error("❌ Could not clone dependency.")
        log.info("Repo: $depURI")
        log.info("Branch: $branch")
        when {
            message.contains("Remote branch", true) && message.contains("not found", true) -> {
                val branches = listRemoteBranches(depURI)
                log.info("Reason: branch <$branch> does not exist on that remote.")
                if (branches.isNotEmpty()) {
                    log.info("Available branches: ${branches.joinToString(", ")}")
                }
                log.info("Try: use https://github.com/user/repo:branch with an existing branch.")
            }
            message.contains("Authentication", true) || message.contains("not authorized", true) -> {
                log.info("Reason: authentication failed.")
                log.info("Try: check that your installed Git can access this repo and has a credential helper configured.")
            }
            else -> {
                log.info("Reason: $message")
                log.info("Try: rerun with --debug for the full stack trace.")
            }
        }
        if (debug) {
            e.printStackTrace()
        }
    }

    private fun listRemoteBranches(depURI: String): List<String> {
        return try {
            Git.lsRemoteRepository()
                .setRemote(depURI)
                .setHeads(true)
                .setTags(false)
                .setCredentialsProvider(GitCredentialProvider)
                .call()
                .mapNotNull { ref ->
                    ref.name.takeIf { it.startsWith(Constants.R_HEADS) }?.removePrefix(Constants.R_HEADS)
                }
                .sorted()
        } catch (_: Exception) {
            emptyList()
        }
    }
}
