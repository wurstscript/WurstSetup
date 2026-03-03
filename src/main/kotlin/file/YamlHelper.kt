package file

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import com.fasterxml.jackson.module.kotlin.KotlinModule
import config.WurstProjectConfigData
import mu.KotlinLogging
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

        mapper = ObjectMapper(yamlFactory)
        mapper.registerModule(KotlinModule.Builder().build())
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
        mapper.enable(SerializationFeature.INDENT_OUTPUT)
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
        val yaml = mapper.writeValueAsString(normalized).trim()
        if (isEffectivelyEmptyYaml(yaml)) {
            return defaultYaml(normalized.projectName)
        }
        return yaml + "\n"
    }

    private fun normalizeConfig(configData: WurstProjectConfigData, sourcePath: Path?): WurstProjectConfigData {
        if (configData.projectName.isBlank()) {
            configData.projectName = sourcePath?.parent?.fileName?.toString() ?: "unnamed"
        }
        return configData
    }

    private fun fallbackConfig(path: Path): WurstProjectConfigData {
        val projectName = path.parent?.fileName?.toString() ?: "unnamed"
        return WurstProjectConfigData(projectName)
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

