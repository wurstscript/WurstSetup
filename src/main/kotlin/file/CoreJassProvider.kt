package file

import logging.KotlinLogging
import org.wurstscript.projectconfig.Wc3PatchTarget
import java.net.URI
import java.nio.file.AtomicMoveNotSupportedException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.util.jar.JarFile

object CoreJassProvider {
    const val DEFAULT_PATCH = "v2.0"
    const val PRE_129_PATCH = "v1.28"

    private const val JASS_HISTORY_RAW = "https://raw.githubusercontent.com/wurstscript/jass-history"
    private const val JASS_HISTORY_REF = "master"
    private const val VERSION_LIST_FILE = "version-list-sorted.txt"
    private val log = KotlinLogging.logger {}

    private val PATCH_TO_JASS_HISTORY_FOLDER = linkedMapOf(
        "v2.0" to "Reforged-v2.0.4.23745",
        "v1.36" to "Reforged-v1.36.1.20719-w3-51d40ee",
        "v1.35" to "Reforged-v1.35.0.20093-w3-5ec1b77",
        "v1.34" to "Reforged-v1.34.0.19632-w3-31590bf",
        "v1.33" to "Reforged-v1.33.0.19378-w3-e94d62c",
        "v1.32" to "Reforged-v1.32.10.19202",
        "v1.31" to "TFT-v1.31.1.12173",
        "v1.30" to "TFT-v1.30.4.11274",
        "v1.29" to "TFT-v1.29.2.9231",
        "v1.28" to "TFT-v1.28.2.7395",
        "v1.27b" to "TFT-v1.27b-ru",
        "v1.27a" to "TFT-v1.27a-ru",
        "v1.26a" to "TFT-v1.26a-ru",
        "v1.25b" to "TFT-v1.25b-ru",
        "v1.24e" to "TFT-v1.24e-ru",
        "v1.24d" to "TFT-v1.24d-ru",
        "v1.24c" to "TFT-v1.24c-ru",
        "v1.24b" to "TFT-v1.24b-ru",
        "v1.24a" to "TFT-v1.24a-ru",
        "v1.23a" to "TFT-v1.23a-ru",
        "v1.22a" to "TFT-v1.22a-ru",
        "v1.21b" to "TFT-v1.21b-ru",
        "v1.21a" to "TFT-v1.21a-ru",
        "v1.21" to "Beta-ROC-v1.21",
        "v1.20e" to "TFT-v1.20e-ru",
        "v1.20d" to "TFT-v1.20d-ru",
        "v1.20c" to "TFT-v1.20c-ru",
        "v1.20b" to "TFT-v1.20b-ru",
        "v1.20a" to "TFT-v1.20a-ru",
        "v1.20" to "Beta-ROC-v1.20",
        "v1.19b" to "TFT-v1.19b-ru",
        "v1.19a" to "TFT-v1.19a-ru",
        "v1.18a" to "TFT-v1.18a-ru",
        "v1.17a" to "TFT-v1.17a-ru",
        "v1.16a" to "TFT-v1.16a-ru",
        "v1.15" to "TFT-v1.15-ru",
        "v1.14b" to "TFT-v1.14b-ru",
        "v1.14" to "TFT-v1.14-ru",
        "v1.13b" to "TFT-v1.13b-ru",
        "v1.13" to "TFT-v1.13-ru",
        "v1.12" to "TFT-v1.12-ru",
        "v1.11" to "TFT-v1.11-ru",
        "v1.10" to "TFT-v1.10-ru",
        "v1.07" to "TFT-v1.07-ru",
        "v1.06" to "ROC-v1.06-ru",
        "v1.05" to "ROC-v1.05-ru",
        "v1.04" to "ROC-v1.04-ru",
        "v1.03" to "ROC-v1.03-ru",
        "v1.02a" to "ROC-v1.02a-ru",
        "v1.02" to "ROC-v1.02-ru",
        "v1.01b" to "ROC-v1.01b-ru",
        "v1.01" to "ROC-v1.01-ru",
        "v1.00" to "ROC-v1.00-ru"
    )

    val supportedPatches: List<String> = PATCH_TO_JASS_HISTORY_FOLDER.keys.toList()

    private val BUNDLED_CORE_JASS_PATCH_FOLDERS = mapOf(
        DEFAULT_PATCH to "v2.0",
        "v1.36" to "reforged",
        PRE_129_PATCH to "pre1.29"
    )

    internal var jassHistoryFileDownloader: (List<String>, Path) -> Unit = ::downloadFirstExisting

    fun describePatch(patch: String): String {
        val normalizedPatch = normalizePatchInput(patch)
        val label = when (normalizedPatch) {
            DEFAULT_PATCH -> "latest Reforged / WC3 2.x core JASS"
            "v1.31" -> "latest classic TFT"
            PRE_129_PATCH -> "legacy pre-1.29"
            else -> {
                val target = Wc3PatchTarget.parse(normalizedPatch).orElse(null)
                when (target?.kind()) {
                    Wc3PatchTarget.Kind.REFORGED -> "Reforged"
                    Wc3PatchTarget.Kind.CLASSIC -> "classic TFT"
                    Wc3PatchTarget.Kind.PRE_129 -> "legacy pre-1.29"
                    else -> null
                }
            }
        }
        return if (label == null) normalizedPatch else "$normalizedPatch ($label)"
    }

    fun jassHistoryFolderForPatch(patch: String): String? {
        val normalized = normalizePatchInput(patch)
        return PATCH_TO_JASS_HISTORY_FOLDER[normalized]
            ?: normalized.takeIf(::looksLikeJassHistoryFolder)
    }

    fun isSupportedPatch(patch: String): Boolean {
        val normalized = normalizePatchInput(patch)
        return PATCH_TO_JASS_HISTORY_FOLDER.containsKey(normalized) || looksLikeJassHistoryFolder(normalized)
    }

    /**
     * Whether the patch targets a version before 1.24. These legacy patches ship Blizzard
     * common.j/blizzard.j with return-type mismatches the Jass VM tolerates, so the compiler
     * must relax Jass type checks and skip PJass for them.
     */
    fun isPre124(patch: String?): Boolean {
        val normalized = normalizePatchInput(patch)
        val gameVersion = Wc3PatchTarget.parse(normalized).orElse(null)?.gameVersion() ?: return false
        return compareVersionStrings(gameVersion, "1.24") < 0
    }

    private fun compareVersionStrings(a: String, b: String): Int {
        val pa = a.split(".")
        val pb = b.split(".")
        for (i in 0 until maxOf(pa.size, pb.size)) {
            val na = pa.getOrNull(i)?.takeWhile { it.isDigit() }?.toIntOrNull() ?: 0
            val nb = pb.getOrNull(i)?.takeWhile { it.isDigit() }?.toIntOrNull() ?: 0
            if (na != nb) return na - nb
        }
        return 0
    }

    fun normalizePatchInput(input: String?): String {
        val patch = input?.trim().orEmpty()
        val normalizedAlias = when (patch.lowercase()) {
            "", "reforged", "latest" -> DEFAULT_PATCH
            "classic", "tft" -> "v1.31"
            "pre1.29", "pre-1.29", "pre_129", "pre-129" -> PRE_129_PATCH
            else -> patch
        }
        val withPrefix = if (normalizedAlias.matches(Regex("""\d+\.\d+.*"""))) "v$normalizedAlias" else normalizedAlias
        val canonicalCase = PATCH_TO_JASS_HISTORY_FOLDER.keys.firstOrNull { it.equals(withPrefix, ignoreCase = true) }
        if (canonicalCase != null) {
            return canonicalCase
        }
        PATCH_TO_JASS_HISTORY_FOLDER.entries.firstOrNull { it.value.equals(withPrefix, ignoreCase = true) }?.key?.let {
            return it
        }
        if (looksLikeJassHistoryFolder(withPrefix)) {
            return withPrefix
        }
        val target = Wc3PatchTarget.parse(withPrefix).orElse(null)
        if (target != null) {
            val versionPatch = "v${target.gameVersion()}"
            return PATCH_TO_JASS_HISTORY_FOLDER.keys.firstOrNull { it.equals(versionPatch, ignoreCase = true) }
                ?: versionPatch
        }
        return withPrefix
    }

    fun isPre129Patch(input: String?): Boolean {
        val patch = normalizePatchInput(input)
        return Wc3PatchTarget.parse(patch)
            .map { it.kind() == Wc3PatchTarget.Kind.PRE_129 }
            .orElse(false)
    }

    fun ensureFiles(projectRoot: Path, wc3Patch: String?): List<Path> {
        val buildFolder = projectRoot.resolve("_build")
        Files.createDirectories(buildFolder)
        val patch = resolveSupportedPatch(wc3Patch)
        val previousPatch = readProvenance(buildFolder)
        val materializedFiles = listOf(
            materializeFile(buildFolder, "common.j", patch, previousPatch),
            materializeFile(buildFolder, "blizzard.j", patch, previousPatch)
        )
        if (materializedFiles.all { it.managedByGrill }) {
            Files.writeString(
                buildFolder.resolve("core-jass.provenance"),
                "wc3Patch: $patch\njassHistoryFolder: ${jassHistoryFolderForPatch(patch).orEmpty()}\n"
            )
            // Single happy-path line; stay quiet when a fallback already warned about an issue.
            val sources = materializedFiles.map { it.source }
            when {
                sources.all { it == CoreJassSource.CACHED } -> log.info("✔ Core JASS ready ($patch, cached)")
                sources.all { it == CoreJassSource.CACHED || it == CoreJassSource.FRESH } ->
                    log.info("✔ Core JASS ready ($patch, updated)")
            }
        } else {
            log.warn(
                "Existing _build core JASS files have no Grill provenance; leaving them project-owned. " +
                    "Delete _build/common.j and _build/blizzard.j to let Grill regenerate them for $patch."
            )
        }
        return materializedFiles.map { it.path }
    }

    fun fetchJassHistoryVersions(): List<String> {
        val versionListUrl = "$JASS_HISTORY_RAW/$JASS_HISTORY_REF/$VERSION_LIST_FILE"
        return try {
            parseJassHistoryVersionList(URI(versionListUrl).toURL().readText())
                .asReversed()
                .distinct()
                .ifEmpty { supportedPatches }
        } catch (e: Exception) {
            log.warn("Could not load jass-history version list; using bundled fallback. Reason: ${e.message}")
            supportedPatches
        }
    }

    internal fun parseJassHistoryVersionList(content: String): List<String> {
        return content
            .lineSequence()
            .flatMap { line ->
                line.substringBefore("#")
                    .trim()
                    .splitToSequence(Regex("""\s+"""))
                    .filter(String::isNotBlank)
            }
            .filter(::looksLikeJassHistoryFolder)
            .toList()
    }

    fun recommendedPatchOptions(versions: List<String>): List<String> {
        val base = listOf(DEFAULT_PATCH, "v1.31", PRE_129_PATCH)
        val latestExactVersion = versions.firstOrNull { version ->
            base.none { normalizePatchInput(it).equals(normalizePatchInput(version), ignoreCase = true) }
        }
        return (base + listOfNotNull(latestExactVersion)).distinct()
    }

    private enum class CoreJassSource { CACHED, FRESH, FALLBACK, PROJECT_OWNED }

    private data class MaterializedFile(val path: Path, val source: CoreJassSource) {
        val managedByGrill: Boolean get() = source != CoreJassSource.PROJECT_OWNED
    }

    private fun resolveSupportedPatch(wc3Patch: String?): String {
        val patch = normalizePatchInput(wc3Patch)
        if (isSupportedPatch(patch)) {
            return patch
        }
        log.warn("Ignoring unsupported wc3Patch <$patch>; using ${describePatch(DEFAULT_PATCH)}.")
        return DEFAULT_PATCH
    }

    private fun materializeFile(buildFolder: Path, fileName: String, patch: String, previousPatch: String?): MaterializedFile {
        val target = buildFolder.resolve(fileName)
        val jassHistoryFolder = jassHistoryFolderForPatch(patch)
        if (jassHistoryFolder == null) {
            throw IllegalArgumentException("Unsupported WC3 patch <$patch>. Supported values: ${supportedPatches.joinToString()}")
        }

        if (previousPatch == null && Files.exists(target)) {
            // Project-owned file: reported once as a warning in ensureFiles, not per file here.
            return MaterializedFile(target, CoreJassSource.PROJECT_OWNED)
        }

        val canKeepExisting = previousPatch == patch
        if (canKeepExisting && isValidCoreJassFile(target)) {
            return MaterializedFile(target, CoreJassSource.CACHED)
        }

        try {
            downloadJassHistoryFile(fileName, patch, jassHistoryFolder, target)
            return MaterializedFile(target, CoreJassSource.FRESH)
        } catch (e: Exception) {
            if (canKeepExisting && isValidCoreJassFile(target)) {
                log.warn("Could not refresh $fileName for $patch; keeping existing _build copy. Reason: ${e.message}")
                return MaterializedFile(target, CoreJassSource.FALLBACK)
            }
            if (hasBundledCoreJass(patch)) {
                log.warn("Could not download $fileName for $patch; falling back to bundled core JASS. Reason: ${e.message}")
                copyBundledCoreJass(fileName, patch, target)
                return MaterializedFile(target, CoreJassSource.FALLBACK)
            }
            throw RuntimeException("Could not download $fileName for WC3 patch <$patch> from wurstscript/jass-history.", e)
        }
    }

    private fun readProvenance(buildFolder: Path): String? {
        val provenanceFile = buildFolder.resolve("core-jass.provenance")
        if (!Files.exists(provenanceFile)) {
            return null
        }
        return Files.readString(provenanceFile)
            .lineSequence()
            .firstOrNull { it.startsWith("wc3Patch:") }
            ?.substringAfter(":")
            ?.trim()
            ?.let(::normalizePatchInput)
    }

    private fun hasBundledCoreJass(patch: String): Boolean {
        return BUNDLED_CORE_JASS_PATCH_FOLDERS.containsKey(normalizePatchInput(patch))
    }

    internal fun bundledCoreJassFolderForPatch(patch: String): String? {
        return BUNDLED_CORE_JASS_PATCH_FOLDERS[normalizePatchInput(patch)]
    }

    private fun copyBundledCoreJass(fileName: String, patch: String, target: Path) {
        val patchFolder = bundledCoreJassFolderForPatch(patch)
            ?: throw IllegalStateException("No bundled core JASS is available for $patch.")

        Files.createDirectories(target.parent)
        val resourcePath = "core-jass/$patchFolder/$fileName"
        javaClass.classLoader.getResourceAsStream(resourcePath)?.use { input ->
            Files.copy(input, target, StandardCopyOption.REPLACE_EXISTING)
            return
        }

        JarFile(global.InstallationManager.getCompilerPath()).use { jar ->
            val entry = jar.getEntry(fileName)
                ?: throw IllegalStateException("Bundled $fileName was not found for $patch.")
            jar.getInputStream(entry).use { input ->
                Files.copy(input, target, StandardCopyOption.REPLACE_EXISTING)
            }
        }
    }

    private fun downloadJassHistoryFile(fileName: String, patch: String, jassHistoryFolder: String, target: Path) {
        Files.createDirectories(target.parent)
        val tempFile = Files.createTempFile(target.parent, "$fileName.", ".download")
        var replacedTarget = false
        try {
            jassHistoryFileDownloader(jassHistoryUrls(jassHistoryFolder, fileName), tempFile)
            if (!isValidCoreJassFile(tempFile)) {
                throw IllegalStateException("Downloaded $fileName from wurstscript/jass-history did not look valid.")
            }
            moveValidatedDownload(tempFile, target)
            replacedTarget = true
        } finally {
            if (!replacedTarget) {
                Files.deleteIfExists(tempFile)
            }
        }
    }

    private fun downloadFirstExisting(urls: List<String>, target: Path) {
        var lastError: Exception? = null
        for (url in urls) {
            try {
                URI(url).toURL().openStream().use { input ->
                    Files.copy(input, target, StandardCopyOption.REPLACE_EXISTING)
                }
                return
            } catch (e: Exception) {
                lastError = e
            }
        }
        throw lastError ?: IllegalStateException("No jass-history download URL was provided.")
    }

    private fun jassHistoryUrls(jassHistoryFolder: String, fileName: String): List<String> {
        val scriptDirs = listOf("scripts", "Scripts")
        val fileNames = listOf(fileName, legacyCoreJassFileName(fileName)).distinct()
        return scriptDirs.flatMap { scriptDir ->
            fileNames.map { candidateFileName ->
                "$JASS_HISTORY_RAW/$JASS_HISTORY_REF/war3extract/$jassHistoryFolder/$scriptDir/$candidateFileName"
            }
        }
    }

    private fun legacyCoreJassFileName(fileName: String): String {
        return when (fileName) {
            "blizzard.j" -> "Blizzard.j"
            "common.j" -> "Common.j"
            else -> fileName
        }
    }

    private fun isValidCoreJassFile(path: Path): Boolean {
        return Files.exists(path) && Files.size(path) >= 1024L
    }

    private fun moveValidatedDownload(source: Path, target: Path) {
        try {
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE)
        } catch (_: AtomicMoveNotSupportedException) {
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING)
        }
    }

    private fun looksLikeJassHistoryFolder(value: String): Boolean {
        return value.startsWith("Reforged-v") ||
            value.startsWith("TFT-v") ||
            value.startsWith("ROC-v") ||
            value.startsWith("Beta-TFT-v") ||
            value.startsWith("Beta-ROC-v")
    }

}
