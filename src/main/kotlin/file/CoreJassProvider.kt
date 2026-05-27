package file

import logging.KotlinLogging
import java.net.URI
import java.nio.file.AtomicMoveNotSupportedException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.util.jar.JarFile

object CoreJassProvider {
    const val DEFAULT_PATCH = "v1.36"
    const val PRE_129_PATCH = "v1.28"

    private const val JASS_HISTORY_RAW = "https://raw.githubusercontent.com/Luashine/jass-history"
    private const val JASS_HISTORY_REF = "master"
    private val log = KotlinLogging.logger {}

    private val PATCH_TO_JASS_HISTORY_FOLDER = linkedMapOf(
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

    fun describePatch(patch: String): String {
        val normalizedPatch = normalizePatchInput(patch)
        val label = when (normalizedPatch) {
            DEFAULT_PATCH -> "latest Reforged / WC3 2.x core JASS"
            "v1.31" -> "latest classic TFT"
            PRE_129_PATCH -> "legacy pre-1.29"
            else -> {
                val minor = Regex("""v1\.(\d+)""").find(normalizedPatch)?.groupValues?.get(1)?.toIntOrNull()
                when {
                    minor != null && minor >= 32 -> "Reforged"
                    minor != null && minor >= 7 -> "classic TFT"
                    minor != null -> "classic ROC"
                    else -> null
                }
            }
        }
        return if (label == null) normalizedPatch else "$normalizedPatch ($label)"
    }

    fun jassHistoryFolderForPatch(patch: String): String? {
        return PATCH_TO_JASS_HISTORY_FOLDER[normalizePatchInput(patch)]
    }

    fun isSupportedPatch(patch: String): Boolean {
        return PATCH_TO_JASS_HISTORY_FOLDER.containsKey(normalizePatchInput(patch))
    }

    fun normalizePatchInput(input: String?): String {
        val patch = input?.trim().orEmpty()
        val normalizedAlias = when (patch.lowercase()) {
            "", "reforged", "latest" -> DEFAULT_PATCH
            "pre1.29", "pre-1.29", "pre_129", "pre-129" -> PRE_129_PATCH
            else -> patch
        }
        val withPrefix = if (normalizedAlias.matches(Regex("""\d+\.\d+.*"""))) "v$normalizedAlias" else normalizedAlias
        val canonicalCase = PATCH_TO_JASS_HISTORY_FOLDER.keys.firstOrNull { it.equals(withPrefix, ignoreCase = true) }
        if (canonicalCase != null) {
            return canonicalCase
        }
        return PATCH_TO_JASS_HISTORY_FOLDER.entries.firstOrNull { it.value.equals(withPrefix, ignoreCase = true) }?.key
            ?: withPrefix
    }

    fun isPre129Patch(input: String?): Boolean {
        val patch = normalizePatchInput(input)
        if (patch == PRE_129_PATCH) {
            return true
        }
        val version = Regex("""v(\d+)\.(\d+)""").find(patch)?.groupValues ?: return false
        return version[1].toIntOrNull() == 1 && (version[2].toIntOrNull() ?: 29) < 29
    }

    fun ensureFiles(projectRoot: Path, wc3Patch: String?): List<Path> {
        val buildFolder = projectRoot.resolve("_build")
        Files.createDirectories(buildFolder)
        val patch = normalizePatchInput(wc3Patch)
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
        } else {
            log.warn(
                "Existing _build core JASS files have no Grill provenance; leaving them project-owned. " +
                    "Delete _build/common.j and _build/blizzard.j to let Grill regenerate them for $patch."
            )
        }
        return materializedFiles.map { it.path }
    }

    fun fetchJassHistoryVersions(): List<String> {
        return supportedPatches
    }

    fun recommendedPatchOptions(versions: List<String>): List<String> {
        return (listOf(DEFAULT_PATCH, "v1.31", PRE_129_PATCH) + versions.take(1)).distinct()
    }

    private data class MaterializedFile(val path: Path, val managedByGrill: Boolean)

    private fun materializeFile(buildFolder: Path, fileName: String, patch: String, previousPatch: String?): MaterializedFile {
        val target = buildFolder.resolve(fileName)
        val jassHistoryFolder = PATCH_TO_JASS_HISTORY_FOLDER[patch]
        if (jassHistoryFolder == null) {
            throw IllegalArgumentException("Unsupported WC3 patch <$patch>. Supported values: ${supportedPatches.joinToString()}")
        }

        if (previousPatch == null && Files.exists(target)) {
            log.info("Keeping existing _build/$fileName because it has no Grill provenance.")
            return MaterializedFile(target, managedByGrill = false)
        }

        val canKeepExisting = previousPatch == patch
        if (canKeepExisting && isValidCoreJassFile(target)) {
            log.info("Using cached _build/$fileName for $patch.")
            return MaterializedFile(target, managedByGrill = true)
        }

        try {
            downloadJassHistoryFile(fileName, patch, jassHistoryFolder, target)
            return MaterializedFile(target, managedByGrill = true)
        } catch (e: Exception) {
            if (canKeepExisting && isValidCoreJassFile(target)) {
                log.warn("Could not refresh $fileName for $patch; keeping existing _build copy. Reason: ${e.message}")
                return MaterializedFile(target, managedByGrill = true)
            }
            if (patch == DEFAULT_PATCH || patch == PRE_129_PATCH) {
                log.warn("Could not download $fileName for $patch; falling back to bundled core JASS. Reason: ${e.message}")
                copyBundledCoreJass(fileName, patch, target)
                return MaterializedFile(target, managedByGrill = true)
            }
            throw RuntimeException("Could not download $fileName for WC3 patch <$patch> from jass-history.", e)
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

    private fun copyBundledCoreJass(fileName: String, patch: String, target: Path) {
        val patchFolder = when (patch) {
            PRE_129_PATCH -> "pre1.29"
            else -> "reforged"
        }

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
        val rawUrl = "$JASS_HISTORY_RAW/$JASS_HISTORY_REF/war3extract/$jassHistoryFolder/scripts/$fileName"
        val tempFile = Files.createTempFile(target.parent, "$fileName.", ".download")
        var replacedTarget = false
        try {
            URI(rawUrl).toURL().openStream().use { input ->
                Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING)
            }
            if (!isValidCoreJassFile(tempFile)) {
                throw IllegalStateException("Downloaded $fileName from jass-history did not look valid.")
            }
            moveValidatedDownload(tempFile, target)
            replacedTarget = true
        } finally {
            if (!replacedTarget) {
                Files.deleteIfExists(tempFile)
            }
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

}
