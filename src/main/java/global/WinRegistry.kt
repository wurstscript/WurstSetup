package global

import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.prefs.Preferences

// source: http://stackoverflow.com/questions/62289/read-write-to-windows-registry-using-java
object WinRegistry {
    val HKEY_CURRENT_USER = 0x80000001.toInt()

    private val HKEY_LOCAL_MACHINE = 0x80000002.toInt()
    private val REG_SUCCESS = 0
    private val KEY_READ = 0x20019
    private val userRoot = Preferences.userRoot()
    private val systemRoot = Preferences.systemRoot()
    private val userClass = userRoot.javaClass
    private val regOpenKey: Method
    private val regCloseKey: Method
    private val regQueryValueEx: Method
    private val regEnumValue: Method
    private val regQueryInfoKey: Method
    private val regEnumKeyEx: Method
    private val regCreateKeyEx: Method
    private val regSetValueEx: Method
    private val regDeleteKey: Method
    private val regDeleteValue: Method

    init {
        try {
            regOpenKey = userClass.getDeclaredMethod("WindowsRegOpenKey", Int::class.javaPrimitiveType, ByteArray::class.java, Int::class.javaPrimitiveType)
            regOpenKey.isAccessible = true
            regCloseKey = userClass.getDeclaredMethod("WindowsRegCloseKey", Int::class.javaPrimitiveType)
            regCloseKey.isAccessible = true
            regQueryValueEx = userClass.getDeclaredMethod("WindowsRegQueryValueEx", Int::class.javaPrimitiveType, ByteArray::class.java)
            regQueryValueEx.isAccessible = true
            regEnumValue = userClass.getDeclaredMethod("WindowsRegEnumValue", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
            regEnumValue.isAccessible = true
            regQueryInfoKey = userClass.getDeclaredMethod("WindowsRegQueryInfoKey1", Int::class.javaPrimitiveType)
            regQueryInfoKey.isAccessible = true
            regEnumKeyEx = userClass.getDeclaredMethod("WindowsRegEnumKeyEx", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
            regEnumKeyEx.isAccessible = true
            regCreateKeyEx = userClass.getDeclaredMethod("WindowsRegCreateKeyEx", Int::class.javaPrimitiveType, ByteArray::class.java)
            regCreateKeyEx.isAccessible = true
            regSetValueEx = userClass.getDeclaredMethod("WindowsRegSetValueEx", Int::class.javaPrimitiveType, ByteArray::class.java, ByteArray::class.java)
            regSetValueEx.isAccessible = true
            regDeleteValue = userClass.getDeclaredMethod("WindowsRegDeleteValue", Int::class.javaPrimitiveType, ByteArray::class.java)
            regDeleteValue.isAccessible = true
            regDeleteKey = userClass.getDeclaredMethod("WindowsRegDeleteKey", Int::class.javaPrimitiveType, ByteArray::class.java)
            regDeleteKey.isAccessible = true
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    /**
     * Read a value from key and value name
     *
     * @param hkey      HKEY_CURRENT_USER/HKEY_LOCAL_MACHINE
     * @param key
     * @param valueName
     * @return the value
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Throws(IllegalArgumentException::class, IllegalAccessException::class, InvocationTargetException::class)
    fun readString(hkey: Int, key: String, valueName: String): String? {
        return when (hkey) {
            HKEY_LOCAL_MACHINE -> readString(systemRoot, hkey, key, valueName)
            HKEY_CURRENT_USER -> readString(userRoot, hkey, key, valueName)
            else -> throw IllegalArgumentException("hkey=" + hkey)
        }
    }

    @Throws(IllegalArgumentException::class, IllegalAccessException::class, InvocationTargetException::class)
    private fun readString(root: Preferences, hkey: Int, key: String, value: String): String? {
        val handles = regOpenKey.invoke(root, hkey, toCstr(key), KEY_READ) as IntArray
        if (handles[1] != REG_SUCCESS) {
            return null
        }
        val valb = regQueryValueEx.invoke(root, handles[0], toCstr(value)) as ByteArray
        regCloseKey.invoke(root, handles[0])
        return String(valb).trim { it <= ' ' }
    }

    private fun toCstr(str: String): ByteArray {
        val result = ByteArray(str.length + 1)

        for (i in 0 until str.length) {
            result[i] = str[i].toByte()
        }
        result[str.length] = 0
        return result
    }
}
