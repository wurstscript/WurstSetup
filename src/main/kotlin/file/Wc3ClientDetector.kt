package file

import org.wurstscript.projectconfig.Wc3PatchTarget
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.Locale

object Wc3ClientDetector {
    enum class ClientKind {
        PRE_129,
        CLASSIC,
        REFORGED
    }

    data class ClientInfo(
        val root: Path,
        val executable: Path,
        val kind: ClientKind?,
    )

    private val exeCandidates = listOf(
        Paths.get("_retail_", "x86_64", "Warcraft III.exe"),
        Paths.get("_retail_", "x86", "Warcraft III.exe"),
        Paths.get("x86_64", "Warcraft III.exe"),
        Paths.get("x86", "Warcraft III.exe"),
        Paths.get("Warcraft III.exe"),
        Paths.get("Frozen Throne.exe"),
        Paths.get("war3.exe"),
    )

    fun detectGameRoot(): Path? {
        return candidateRoots()
            .map { it.toAbsolutePath().normalize() }
            .distinct()
            .firstOrNull { inspectGameRoot(it) != null }
    }

    fun inspectGameRoot(root: Path?): ClientInfo? {
        if (root == null || !Files.exists(root)) {
            return null
        }
        val normalizedRoot = root.toAbsolutePath().normalize()
        val executable = findExecutable(normalizedRoot) ?: return null
        val installRoot = if (Files.isRegularFile(normalizedRoot)) {
            installationRootForExecutable(executable)
        } else {
            normalizedRoot
        }
        return ClientInfo(installRoot, executable, classifyExecutable(executable))
    }

    fun describe(info: ClientInfo?): String {
        if (info == null) {
            return "not found"
        }
        val kind = info.kind?.let { describeKind(it) } ?: "unknown patch family"
        return "${info.root} ($kind)"
    }

    fun projectKind(patch: String?): ClientKind? {
        val target = Wc3PatchTarget.parse(patch).orElse(null) ?: return null
        return when (target.kind()) {
            Wc3PatchTarget.Kind.PRE_129 -> ClientKind.PRE_129
            Wc3PatchTarget.Kind.CLASSIC -> ClientKind.CLASSIC
            Wc3PatchTarget.Kind.REFORGED -> ClientKind.REFORGED
        }
    }

    fun mismatchMessage(projectPatch: String?, clientInfo: ClientInfo?): String? {
        val projectKind = projectKind(projectPatch) ?: return null
        val clientKind = clientInfo?.kind ?: return null
        if (projectKind == clientKind) {
            return null
        }
        return "Selected Warcraft III client is ${describeKind(clientKind)}, but the project targets ${describeKind(projectKind)}. Running may fail unless you choose a matching client."
    }

    private fun findExecutable(root: Path): Path? {
        if (Files.isRegularFile(root)) {
            return root.takeIf { classifyExecutable(it) != null }
        }
        return exeCandidates
            .map { root.resolve(it) }
            .firstOrNull(Files::isRegularFile)
    }

    private fun classifyExecutable(executable: Path): ClientKind? {
        val path = executable.toAbsolutePath().normalize().toString().replace('\\', '/').lowercase(Locale.ROOT)
        val fileName = executable.fileName.toString().lowercase(Locale.ROOT)
        if (path.contains("/_retail_/") || path.contains("/_ptr_/")) {
            return ClientKind.REFORGED
        }
        if (fileName == "war3.exe" || fileName == "frozen throne.exe") {
            return ClientKind.PRE_129
        }
        if (fileName == "warcraft iii.exe") {
            val parentName = executable.parent?.fileName?.toString()?.lowercase(Locale.ROOT)
            if (parentName == "x86" || parentName == "x86_64") {
                return ClientKind.CLASSIC
            }
            return ClientKind.CLASSIC
        }
        return null
    }

    private fun installationRootForExecutable(executable: Path): Path {
        val parts = executable.toAbsolutePath().normalize()
        val parent = parts.parent ?: return parts
        if (parent.fileName?.toString()?.equals("x86", ignoreCase = true) == true ||
            parent.fileName?.toString()?.equals("x86_64", ignoreCase = true) == true
        ) {
            val maybeRetail = parent.parent
            if (maybeRetail?.fileName?.toString()?.equals("_retail_", ignoreCase = true) == true ||
                maybeRetail?.fileName?.toString()?.equals("_ptr_", ignoreCase = true) == true
            ) {
                return maybeRetail.parent ?: parent
            }
            return maybeRetail ?: parent
        }
        return parent
    }

    private fun describeKind(kind: ClientKind): String {
        return when (kind) {
            ClientKind.PRE_129 -> "pre-1.29"
            ClientKind.CLASSIC -> "classic 1.29-1.31"
            ClientKind.REFORGED -> "Reforged"
        }
    }

    private fun candidateRoots(): List<Path> {
        val envNames = listOf("WURST_WC3_PATH", "WC3_PATH", "WARCRAFT_III_PATH")
        val envRoots = envNames.mapNotNull { System.getenv(it)?.takeIf(String::isNotBlank)?.let(Paths::get) }
        val programRoots = listOfNotNull(
            System.getenv("ProgramFiles"),
            System.getenv("ProgramFiles(x86)"),
            "C:\\Program Files",
            "C:\\Program Files (x86)",
        ).map { Paths.get(it, "Warcraft III") }
        return envRoots + programRoots
    }
}
