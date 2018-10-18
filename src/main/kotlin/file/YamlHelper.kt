package file

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import config.WurstProjectConfigData
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption

object YamlHelper {
    private val mapper = ObjectMapper(YAMLFactory())

    init {
        mapper.registerModule(KotlinModule())
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
        mapper.enable(SerializationFeature.INDENT_OUTPUT)
    }


    fun loadProjectConfig(path: Path): WurstProjectConfigData {
        return try {
            Files.newBufferedReader(path).use {
                mapper.readValue(it, WurstProjectConfigData::class.java)
            }
        } catch (e: MismatchedInputException) {
            val configData = WurstProjectConfigData()
            val projectConfigBytes = dumpProjectConfig(configData).toByteArray()
            Files.write(path, projectConfigBytes, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)
            configData
        }
    }

    fun dumpProjectConfig(configData: WurstProjectConfigData): String {
        return mapper.writeValueAsString(configData)
    }

}
