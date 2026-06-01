package file

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import config.WurstProjectConfigData
import config.WurstProjectBuildForce
import config.WurstProjectBuildForceFlags
import config.WurstProjectBuildLoadingScreenData
import config.WurstProjectBuildMapData
import config.WurstProjectBuildOptionFlagsData
import config.WurstProjectBuildPlayer
import config.WurstProjectBuildScenarioData
import config.newProjectConfig
import config.withProjectName
import config.withWc3Patch
import logging.KotlinLogging
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

object YamlHelper {
    private var mapper: ObjectMapper
	private val log = KotlinLogging.logger {}

    init {
        val yamlFactory = YAMLFactory()
        yamlFactory.enable(YAMLGenerator.Feature.MINIMIZE_QUOTES)
        yamlFactory.enable(JsonParser.Feature.ALLOW_MISSING_VALUES)

        mapper = YAMLMapper.builder(yamlFactory)
            .addModule(KotlinModule.Builder().build())
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)
            .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
            .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
            .enable(SerializationFeature.INDENT_OUTPUT)
            .build()
    }


    fun loadProjectConfig(path: Path): WurstProjectConfigData {
        val content = Files.readString(path)
        if (isEffectivelyEmptyYaml(content)) {
            val fallback = fallbackConfig(path)
            persistRecoveredConfig(path, fallback, backupOriginal = false)
            return fallback
        }

        return try {
            val config = mapper.readValue(content, WurstProjectConfigData::class.java)
            normalizeConfig(config, path)
        } catch (e: Exception) {
            log.warn("The project's wurst.build file could not be read. Recovering with defaults.", e)
            val fallback = fallbackConfig(path)
            persistRecoveredConfig(path, fallback, backupOriginal = true)
            fallback
        }
    }

    fun dumpProjectConfig(configData: WurstProjectConfigData): String {
        val normalized = normalizeConfig(configData, null)
        val yaml = mapper.writeValueAsString(toYamlValue(normalized)).trim()
        if (isEffectivelyEmptyYaml(yaml)) {
            return defaultYaml(normalized.projectName)
        }
        return yaml + "\n"
    }

    private fun toYamlValue(configData: WurstProjectConfigData): Map<String, Any?> {
        val result = linkedMapOf<String, Any?>(
            "projectName" to configData.projectName,
            "dependencies" to configData.dependencies
        )
        configData.buildMapData.toYamlValue()?.let { result["buildMapData"] = it }
        configData.scriptMode?.let { result["scriptMode"] = it }
        configData.wc3Patch?.let { result["wc3Patch"] = it }
        return result
    }

    private fun WurstProjectBuildMapData.toYamlValue(): Map<String, Any?>? {
        val result = linkedMapOf<String, Any?>()
        putIfNotBlank(result, "name", name)
        putIfNotBlank(result, "fileName", fileName)
        putIfNotBlank(result, "author", author)
        scenarioData.toYamlValue()?.let { result["scenarioData"] = it }
        optionsFlags.toYamlValue()?.let { result["optionsFlags"] = it }
        if (players.isNotEmpty()) {
            result["players"] = players.map { it.toYamlValue() }
        }
        if (forces.isNotEmpty()) {
            result["forces"] = forces.map { it.toYamlValue() }
        }
        return result.ifEmpty { null }
    }

    private fun WurstProjectBuildScenarioData.toYamlValue(): Map<String, Any?>? {
        val result = linkedMapOf<String, Any?>()
        putIfNotBlank(result, "description", description)
        putIfNotBlank(result, "suggestedPlayers", suggestedPlayers)
        loadingScreen?.toYamlValue()?.let { result["loadingScreen"] = it }
        return result.ifEmpty { null }
    }

    private fun WurstProjectBuildLoadingScreenData.toYamlValue(): Map<String, Any?>? {
        val result = linkedMapOf<String, Any?>()
        putIfNotBlank(result, "model", model)
        putIfNotBlank(result, "background", background)
        putIfNotBlank(result, "title", title)
        putIfNotBlank(result, "subTitle", subTitle)
        putIfNotBlank(result, "text", text)
        return result.ifEmpty { null }
    }

    private fun WurstProjectBuildOptionFlagsData.toYamlValue(): Map<String, Any?>? {
        val result = linkedMapOf<String, Any?>()
        putIfTrue(result, "hideMinimapPreview", hideMinimapPreview)
        putIfTrue(result, "forcesFixed", forcesFixed)
        putIfTrue(result, "maskedAreasPartiallyVisible", maskedAreasPartiallyVisible)
        putIfTrue(result, "showWavesOnCliffShores", showWavesOnCliffShores)
        putIfTrue(result, "showWavesOnRollingShores", showWavesOnRollingShores)
        putIfTrue(result, "useItemClassificationSystem", useItemClassificationSystem)
        return result.ifEmpty { null }
    }

    private fun WurstProjectBuildPlayer.toYamlValue(): Map<String, Any?> {
        val result = linkedMapOf<String, Any?>("id" to id)
        putIfNotBlank(result, "name", name)
        putIfNotNull(result, "race", race)
        putIfNotNull(result, "controller", controller)
        putIfNotNull(result, "fixedStartLoc", fixedStartLoc)
        return result
    }

    private fun WurstProjectBuildForce.toYamlValue(): Map<String, Any?> {
        val result = linkedMapOf<String, Any?>()
        putIfNotBlank(result, "name", name)
        flags.toYamlValue()?.let { result["flags"] = it }
        if (playerIds.isNotEmpty()) {
            result["playerIds"] = playerIds
        }
        return result
    }

    private fun WurstProjectBuildForceFlags.toYamlValue(): Map<String, Any?>? {
        val defaults = WurstProjectBuildForceFlags.defaults()
        val result = linkedMapOf<String, Any?>()
        putIfChanged(result, "allied", allied, defaults.allied)
        putIfChanged(result, "alliedVictory", alliedVictory, defaults.alliedVictory)
        putIfChanged(result, "sharedVision", sharedVision, defaults.sharedVision)
        putIfChanged(result, "sharedControl", sharedControl, defaults.sharedControl)
        putIfChanged(result, "sharedControlAdvanced", sharedControlAdvanced, defaults.sharedControlAdvanced)
        return result.ifEmpty { null }
    }

    private fun putIfNotBlank(target: MutableMap<String, Any?>, key: String, value: String?) {
        if (!value.isNullOrBlank()) {
            target[key] = value
        }
    }

    private fun putIfTrue(target: MutableMap<String, Any?>, key: String, value: Boolean) {
        if (value) {
            target[key] = value
        }
    }

    private fun putIfChanged(target: MutableMap<String, Any?>, key: String, value: Boolean, defaultValue: Boolean) {
        if (value != defaultValue) {
            target[key] = value
        }
    }

    private fun putIfNotNull(target: MutableMap<String, Any?>, key: String, value: Any?) {
        if (value != null) {
            target[key] = value
        }
    }

    private fun normalizeConfig(configData: WurstProjectConfigData, sourcePath: Path?): WurstProjectConfigData {
        val namedConfig = if (configData.projectName.isBlank()) {
            configData.withProjectName(sourcePath?.parent?.fileName?.toString() ?: "unnamed")
        } else {
            configData
        }
        val patch = namedConfig.wc3Patch
        return if (patch != null) {
            namedConfig.withWc3Patch(CoreJassProvider.normalizePatchInput(patch))
        } else {
            namedConfig
        }
    }

    private fun fallbackConfig(path: Path): WurstProjectConfigData {
        val projectName = path.parent?.fileName?.toString() ?: "unnamed"
        return newProjectConfig(projectName)
    }

    private fun defaultYaml(projectName: String): String {
        return "projectName: \"$projectName\"\n" +
            "dependencies: []\n"
    }

    private fun isEffectivelyEmptyYaml(content: String): Boolean {
        val stripped = content
            .replace("\uFEFF", "")
            .lines()
            .filter { it.trim().isNotEmpty() && !it.trim().startsWith("#") }
            .joinToString("\n") { it.trim() }
        return stripped.isEmpty() || stripped == "--- {}" || stripped == "{}" || stripped == "---"
    }

    private fun persistRecoveredConfig(path: Path, config: WurstProjectConfigData, backupOriginal: Boolean) {
        try {
            if (backupOriginal && Files.exists(path)) {
                val backupPath = path.resolveSibling(path.fileName.toString() + ".bak")
                Files.copy(path, backupPath, StandardCopyOption.REPLACE_EXISTING)
            }
            Files.writeString(path, dumpProjectConfig(config))
        } catch (e: IOException) {
            log.warn("Could not persist recovered wurst.build at <$path>.", e)
        }
    }
}

