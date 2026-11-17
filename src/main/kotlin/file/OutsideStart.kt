package file

import global.Log
import java.io.IOException
import java.nio.file.Path
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

/**
 * Starts processes on Windows in the user's own registry view.
 *
 * A child process sees the registry its parent sees. Under a packaged (MSIX) app, such as the Claude desktop app or a
 * VS Code started from a terminal inside it, that is the package's private copy of `HKCU`: what such a process writes
 * there no other process reads, and once it has written a value it no longer reads the user's. Battle.net, the sign-in
 * renewal and the game pass the sign-in between them through `HKCU` ([BattleNetSignIn]), so each of them started from
 * inside such an app would keep it where the rest of the machine never sees it. A process created through WMI
 * (`Win32_Process.Create`) starts in the user's own context instead, in the same session and on the same desktop.
 */
internal object OutsideStart {
    /** The `SW_` value WMI passes to the new process's first window. */
    enum class Show(val value: Int) { HIDDEN(0), NORMAL(1), MINIMIZED(7) }

    private const val WMI_TIMEOUT_SECONDS = 30L
    private val CREATED = Regex("""^created (\d+) (\d+)\s*$""", RegexOption.MULTILINE)

    // The command line, window and directory travel in the environment, so nothing in them needs PowerShell quoting.
    private val CREATE_SCRIPT = listOf(
        "\$ErrorActionPreference = 'Stop'",
        "\$ProgressPreference = 'SilentlyContinue'",
        "[Console]::OutputEncoding = [Text.Encoding]::UTF8",
        "\$startup = New-CimInstance -ClassName Win32_ProcessStartup -ClientOnly -Property @{ ShowWindow = [uint16]\$env:GRILL_OUTSIDE_SHOW }",
        "\$arguments = @{ CommandLine = \$env:GRILL_OUTSIDE_COMMAND; ProcessStartupInformation = \$startup }",
        "if (\$env:GRILL_OUTSIDE_CWD) { \$arguments.CurrentDirectory = \$env:GRILL_OUTSIDE_CWD }",
        "\$created = Invoke-CimMethod -ClassName Win32_Process -MethodName Create -Arguments \$arguments",
        "'created {0} {1}' -f \$created.ReturnValue, \$created.ProcessId",
    ).joinToString("; ")

    /** One argument quoted for a Windows command line, as CommandLineToArgvW and the C runtime split it. */
    fun quoteWindowsArg(arg: String): String {
        if (arg.isNotEmpty() && arg.none { it == '"' || it.isWhitespace() }) return arg
        val quoted = StringBuilder("\"")
        var backslashes = 0
        for (c in arg) {
            if (c == '\\') {
                backslashes++
                continue
            }
            quoted.append("\\".repeat(if (c == '"') backslashes * 2 + 1 else backslashes)).append(c)
            backslashes = 0
        }
        return quoted.append("\\".repeat(backslashes * 2)).append('"').toString()
    }

    fun commandLine(command: List<String>): String = command.joinToString(" ") { quoteWindowsArg(it) }

    /**
     * Starts [command] through WMI, in the user's own registry view, and returns its pid. Throws when WMI cannot
     * create it.
     *
     * @param directory the new process's working directory; null for WMI's default
     */
    fun start(command: List<String>, directory: Path?, show: Show): Long {
        val builder = ProcessBuilder("powershell.exe", "-NoProfile", "-NonInteractive", "-Command", CREATE_SCRIPT)
            .redirectErrorStream(true)
        builder.environment()["GRILL_OUTSIDE_COMMAND"] = commandLine(command)
        builder.environment()["GRILL_OUTSIDE_SHOW"] = show.value.toString()
        builder.environment()["GRILL_OUTSIDE_CWD"] = directory?.toString() ?: ""
        val process = builder.start()
        val output = try {
            process.outputStream.close()
            // Read while it runs: an error record could fill the pipe and stall PowerShell until the timeout.
            val read = CompletableFuture.supplyAsync {
                try {
                    process.inputStream.use { String(it.readAllBytes(), Charsets.UTF_8) }
                } catch (_: IOException) {
                    ""
                }
            }
            if (!process.waitFor(WMI_TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
                throw IOException("WMI did not answer within $WMI_TIMEOUT_SECONDS s")
            }
            read.get(5, TimeUnit.SECONDS)
        } finally {
            process.destroyForcibly()
        }
        val name = Path.of(command[0]).fileName.toString()
        val created = CREATED.find(output)
            ?: throw IOException("WMI could not start $name: ${output.trim().lineSequence().firstOrNull() ?: "no answer"}")
        if (created.groupValues[1] != "0") {
            throw IOException("WMI could not start $name (Win32_Process.Create returned ${created.groupValues[1]})")
        }
        return created.groupValues[2].toLong()
    }

    /**
     * [start] if WMI can, else a direct start from this process (whose registry view the process then shares).
     * Returns the pid.
     */
    fun startOrHere(command: List<String>, directory: Path?, show: Show): Long {
        return try {
            start(command, directory, show)
        } catch (e: Exception) {
            if (e is InterruptedException) throw e
            Log.println("Starting ${Path.of(command[0]).fileName} from grill instead: ${e.message}")
            val builder = ProcessBuilder(command)
                .redirectOutput(ProcessBuilder.Redirect.DISCARD)
                .redirectError(ProcessBuilder.Redirect.DISCARD)
            if (directory != null) builder.directory(directory.toFile())
            builder.start().pid()
        }
    }
}
