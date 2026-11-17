package file

import config.WurstProjectConfig
import config.WurstProjectConfigData
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import org.wurstscript.projectconfig.Wc3PatchTarget
import javax.swing.filechooser.FileSystemView

internal object Wc3MapRunner {
    fun resolveClient(setup: SetupMain, detectGameRoot: () -> Path? = Wc3ClientDetector::detectGameRoot): Wc3ClientDetector.ClientInfo? {
        // An explicit selection must not silently launch a different installation.
        setup.gamePath?.let { return Wc3ClientDetector.inspectGameRoot(it) }
        Wc3ClientDetector.inspectGameRoot(WurstProjectConfig.configuredGamePath(setup.projectRoot))?.let { return it }
        return Wc3ClientDetector.inspectGameRoot(detectGameRoot())
    }

    fun builtMap(projectRoot: Path, config: WurstProjectConfigData, output: List<String>): Path {
        // Prefer the compiler's actual result, including renamed outputs when a map is locked.
        val reported = output.lastOrNull { it.startsWith("Build succeeded. Output file: <") && it.endsWith(">") }
            ?.removePrefix("Build succeeded. Output file: <")?.removeSuffix(">")
        // Older compilers use this same naming rule (fileName is a stem, even if it contains an extension).
        val map = if (reported != null) Paths.get(reported) else {
            val stem = config.buildMapData.fileName.ifEmpty { config.projectName }
            projectRoot.resolve("_build").resolve("$stem.w3x")
        }
        check(Files.isRegularFile(map)) { "Built map was not found at ${map.toAbsolutePath()}" }
        return map.toAbsolutePath().normalize()
    }

    private fun clientPatch(client: Wc3ClientDetector.ClientInfo): Wc3PatchTarget {
        val fallback = when (client.kind) {
            Wc3ClientDetector.ClientKind.REFORGED -> "reforged"
            Wc3ClientDetector.ClientKind.CLASSIC -> "classic"
            else -> "1.26"
        }
        return Wc3PatchTarget.parse(client.version).orElseGet { Wc3PatchTarget.parse(fallback).orElseThrow() }
    }

    fun launchCommand(client: Wc3ClientDetector.ClientInfo, map: Path, osName: String = System.getProperty("os.name")): List<String> {
        val patch = clientPatch(client)
        val args = mutableListOf(client.executable.toAbsolutePath().toString())
        if (patch.isReforgedOrNewer) args.addAll(listOf("-launch", "-editor"))
        if (patch.usesClassicWindowArg()) args.add("-window")
        else args.addAll(listOf("-windowmode", "windowed"))
        args.addAll(listOf("-loadfile", map.toAbsolutePath().toString()))
        if (osName.startsWith("Linux", ignoreCase = true)) args.add(0, "wine")
        return args
    }

    fun stageMap(client: Wc3ClientDetector.ClientInfo, map: Path,
                 documentsRoot: () -> Path = ::warcraftDocumentsRoot): Path {
        val patch = clientPatch(client)
        if (!patch.copiesRunMapToWarcraftMapDir()) return map
        val root = if (patch.usesInstallDirForMaps()) client.installationRoot else documentsRoot()
        // Keep staged run maps separate from the project's configured release filename.
        val target = root.resolve("Maps/Test/GrillRunMap.w3x")
        if (!patch.usesInstallDirForMaps()) {
            // 1.28+ may load an old install-directory copy ahead of the documents copy.
            Files.deleteIfExists(client.installationRoot.resolve("Maps/Test/GrillRunMap.w3x"))
        }
        Files.createDirectories(target.parent)
        Files.copy(map, target, StandardCopyOption.REPLACE_EXISTING)
        return target.toAbsolutePath().normalize()
    }

    /**
     * Where a hot reload writes its payloads for the running map to read: `--custom-map-data`, then the VS Code setting
     * `wurst.customMapDataPath`, then `CustomMapData` in the Warcraft III documents folder (as the language server).
     */
    fun customMapDataPath(setup: SetupMain, documentsRoot: () -> Path = ::warcraftDocumentsRoot): Path {
        setup.customMapDataPath?.let { return it }
        WurstProjectConfig.configuredCustomMapDataPath(setup.projectRoot)?.let { return it }
        return documentsRoot().resolve("CustomMapData")
    }

    private fun warcraftDocumentsRoot(): Path {
        return when {
            System.getProperty("os.name").startsWith("Linux", ignoreCase = true) -> {
                val prefix = System.getenv("WINEPREFIX")?.takeIf(String::isNotBlank)?.let(Paths::get)
                    ?: Paths.get(System.getProperty("user.home"), ".wine")
                prefix.resolve("drive_c/users/${System.getProperty("user.name")}/My Documents/Warcraft III")
            }
            System.getProperty("os.name").startsWith("Mac", ignoreCase = true) ->
                Paths.get(System.getProperty("user.home"), "Library/Application Support/Blizzard/Warcraft III")
            else -> FileSystemView.getFileSystemView().defaultDirectory.toPath().resolve("Warcraft III")
        }
    }

    /**
     * Starts Warcraft III on [map]. A Reforged client managed by Battle.net first has its sign-in renewed from the app
     * (see [BattleNetSignIn]), and this returns once that client has signed in, so the shared lock covers it.
     */
    fun launch(client: Wc3ClientDetector.ClientInfo, map: Path, launchMode: String = "auto", report: (String) -> Unit = {}) {
        val launchMap = stageMap(client, map)
        val command = launchCommand(client, launchMap)
        val renewal = renewSignIn(client, command, launchMode, report)
        try {
            ProcessBuilder(command)
                .directory(client.root.toFile())
                .redirectOutput(ProcessBuilder.Redirect.DISCARD)
                .redirectError(ProcessBuilder.Redirect.DISCARD)
                .start()
        } catch (e: Exception) {
            renewal?.close()
            throw e
        }
        if (renewal != null) awaitSignIn(renewal, report)
    }

    /**
     * Holds the Battle.net lock until the launched client has signed in. The map is running by now, so nothing here
     * may throw: callers treat an exception from a launch as a failed launch and roll back, as `grill run --hot` does
     * with its session. A sign-in that cannot be watched is reported and the lock released.
     */
    internal fun awaitSignIn(renewal: BattleNetSignIn.Renewal, report: (String) -> Unit) {
        report("Waiting for Warcraft III to sign in...")
        try {
            if (!renewal.awaitSignIn()) {
                report("Warcraft III did not sign in within ${BattleNetSignIn.SIGN_IN_TIMEOUT_SECONDS} s.")
            }
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
        } catch (e: Exception) {
            report("Could not watch the Warcraft III sign-in (${e.message}); released the Battle.net lock.")
        } finally {
            renewal.close()
        }
    }

    /**
     * `-editor` launches sign in with a stored token that lapses, after which the game asks for a login.
     * [launchMode] is the VS Code setting `wurst.wc3LaunchMode`, as for the language server's Run Map: "auto" renews
     * when Battle.net is open and otherwise launches on the stored token, "battlenet" fails the launch when it cannot
     * renew, "legacy" never renews.
     *
     * @return the renewal holding the Battle.net lock, or null when there is none
     */
    internal fun renewSignIn(client: Wc3ClientDetector.ClientInfo, command: List<String>, launchMode: String,
                             report: (String) -> Unit,
                             find: () -> BattleNetApp? = BattleNetSignIn::find,
                             registered: (Path) -> Boolean = BattleNetSignIn::isRegisteredGame,
                             osName: String = System.getProperty("os.name")): BattleNetSignIn.Renewal? {
        // The token lives in the Windows registry; other systems launch as before.
        if (launchMode == "legacy" || "-editor" !in command || !osName.startsWith("Windows", ignoreCase = true)) return null
        val battleNet = find() ?: return failOrNull(launchMode, "Battle.net was not found.", report)
        val managed = try {
            registered(client.executable)
        } catch (e: IOException) {
            false
        }
        if (!managed) return failOrNull(launchMode, "This Warcraft III install is not the one Battle.net manages.", report)
        if (launchMode != "battlenet" && !battleNet.isRunning()) {
            report("Battle.net is not running: launching with the stored sign-in, which may ask for a login.")
            return null
        }
        report("Renewing the Warcraft III sign-in from Battle.net...")
        return try {
            battleNet.renew { report("Another Warcraft III launch is signing in; waiting until it has...") }
        } catch (e: BattleNetSignIn.LockBusyException) {
            // In every mode: launching without the lock could spend the other client's unread token.
            throw IllegalStateException("${e.message} Run the map again once it has signed in.", e)
        } catch (e: IOException) {
            failOrNull(launchMode, "Could not renew the Warcraft III sign-in: ${e.message}", report)
        }
    }

    private fun failOrNull(launchMode: String, problem: String, report: (String) -> Unit): BattleNetSignIn.Renewal? {
        if (launchMode == "battlenet") throw IllegalStateException("$problem (wurst.wc3LaunchMode is \"battlenet\")")
        report("$problem Warcraft III may ask for a login.")
        return null
    }
}
