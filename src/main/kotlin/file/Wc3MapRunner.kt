package file

import config.WurstProjectConfig
import config.WurstProjectConfigData
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

internal object Wc3MapRunner {
    fun resolveClient(setup: SetupMain): Wc3ClientDetector.ClientInfo? {
        val path = setup.gamePath
            ?: WurstProjectConfig.configuredGamePath(setup.projectRoot)
            ?: Wc3ClientDetector.detectGameRoot()
        return Wc3ClientDetector.inspectGameRoot(path)
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

    fun launchCommand(client: Wc3ClientDetector.ClientInfo, map: Path): List<String> {
        val args = mutableListOf(client.executable.toAbsolutePath().toString())
        when (client.kind) {
            Wc3ClientDetector.ClientKind.REFORGED -> args.addAll(listOf("-launch", "-editor", "-windowmode", "windowed"))
            else -> args.add("-window")
        }
        args.addAll(listOf("-loadfile", map.toAbsolutePath().toString()))
        return args
    }

    fun launch(client: Wc3ClientDetector.ClientInfo, map: Path) {
        ProcessBuilder(launchCommand(client, map))
            .directory(client.root.toFile())
            .redirectOutput(ProcessBuilder.Redirect.DISCARD)
            .redirectError(ProcessBuilder.Redirect.DISCARD)
            .start()
    }
}
