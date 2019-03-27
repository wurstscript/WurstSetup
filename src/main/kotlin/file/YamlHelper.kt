package file

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import com.fasterxml.jackson.module.kotlin.KotlinModule
import config.WurstProjectConfigData
import mu.KotlinLogging
import java.nio.file.Files
import java.nio.file.Path

object YamlHelper {
    private var mapper: ObjectMapper
	private val log = KotlinLogging.logger {}

    init {
        val yamlFactory = YAMLFactory()
        yamlFactory.enable(YAMLGenerator.Feature.MINIMIZE_QUOTES)
        yamlFactory.enable(JsonParser.Feature.ALLOW_MISSING_VALUES)

        mapper = ObjectMapper(yamlFactory)
        mapper.registerModule(KotlinModule())
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
        mapper.enable(SerializationFeature.INDENT_OUTPUT)
    }


    fun loadProjectConfig(path: Path): WurstProjectConfigData {
        Files.newBufferedReader(path).use {
            try {
				return mapper.readValue(it, WurstProjectConfigData::class.java)
            } catch (e: Exception) {
                log.error("The config file could not be read", e)
				throw YamlException("wurst.build file could not be read. Input malformed or corrupt.")
            }
        }
    }

    fun dumpProjectConfig(configData: WurstProjectConfigData): String {
        return mapper.writeValueAsString(configData)
    }

	class YamlException(msg: String): RuntimeException(msg)
}

