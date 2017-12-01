package file

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption

object YamlHelper {

    fun loadProjectConfig(path: Path): WurstProjectConfigData {
        val mapper = ObjectMapper(YAMLFactory())
        mapper.registerModule(KotlinModule())

        return try {
            Files.newBufferedReader(path).use {
                mapper.readValue(it, WurstProjectConfigData::class.java)
            }
        } catch (e:MismatchedInputException) {
            val configData = WurstProjectConfigData()
            val projectConfigBytes = dumpProjectConfig(configData).toByteArray()
            Files.write(path, projectConfigBytes, StandardOpenOption.WRITE,StandardOpenOption.TRUNCATE_EXISTING)
            configData
        }
    }

    fun dumpProjectConfig(configData: WurstProjectConfigData): String {
        val mapper = ObjectMapper(YAMLFactory())
        mapper.registerModule(KotlinModule())
        return mapper.writeValueAsString(configData)
    }

//    private val options = DumperOptions()
//    val yaml: Yaml
//
//    private class UnsortedPropertyUtils : PropertyUtils() {
//        @Throws(IntrospectionException::class)
//        override fun createPropertySet(type: Class<out Any>, bAccess: BeanAccess): Set<Property> {
//            val props = getPropertiesMap(type, bAccess).values
//            return props.filter { (it.isReadable || it.isWritable) && it is FieldProperty }
//                    .toSet()
//        }
//    }
//
//    init {
//        options.tags = Collections.emptyMap()
//        val representer = Representer()
//        representer.propertyUtils = UnsortedPropertyUtils()
//        representer.propertyUtils.setSkipMissingProperties(true)
//        representer.addClassTag(WurstProjectConfig::class.java, Tag.MAP)
//        yaml = Yaml(Constructor(WurstProjectConfig::class.java), representer, options)
//    }
}