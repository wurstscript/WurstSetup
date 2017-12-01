package ui

import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.*
import java.util.prefs.Preferences

// source: http://stackoverflow.com/questions/62289/read-write-to-windows-registry-using-java
object WinRegistry {
    val HKEY_CURRENT_USER = 0x80000001.toInt()
    val HKEY_LOCAL_MACHINE = 0x80000002.toInt()
    val REG_SUCCESS = 0

    private val KEY_ALL_ACCESS = 0xf003f
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
        return if (hkey == HKEY_LOCAL_MACHINE) {
            readString(systemRoot, hkey, key, valueName)
        } else if (hkey == HKEY_CURRENT_USER) {
            readString(userRoot, hkey, key, valueName)
        } else {
            throw IllegalArgumentException("hkey=" + hkey)
        }
    }

    /**
     * Read value(s) and value name(s) form given key
     *
     * @param hkey HKEY_CURRENT_USER/HKEY_LOCAL_MACHINE
     * @param key
     * @return the value name(s) plus the value(s)
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Throws(IllegalArgumentException::class, IllegalAccessException::class, InvocationTargetException::class)
    fun readStringValues(hkey: Int, key: String): Map<String, String>? {
        return if (hkey == HKEY_LOCAL_MACHINE) {
            readStringValues(systemRoot, hkey, key)
        } else if (hkey == HKEY_CURRENT_USER) {
            readStringValues(userRoot, hkey, key)
        } else {
            throw IllegalArgumentException("hkey=" + hkey)
        }
    }

    /**
     * Read the value name(s) from a given key
     *
     * @param hkey HKEY_CURRENT_USER/HKEY_LOCAL_MACHINE
     * @param key
     * @return the value name(s)
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Throws(IllegalArgumentException::class, IllegalAccessException::class, InvocationTargetException::class)
    fun readStringSubKeys(hkey: Int, key: String): List<String>? {
        return if (hkey == HKEY_LOCAL_MACHINE) {
            readStringSubKeys(systemRoot, hkey, key)
        } else if (hkey == HKEY_CURRENT_USER) {
            readStringSubKeys(userRoot, hkey, key)
        } else {
            throw IllegalArgumentException("hkey=" + hkey)
        }
    }

    /**
     * Create a key
     *
     * @param hkey HKEY_CURRENT_USER/HKEY_LOCAL_MACHINE
     * @param key
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Throws(IllegalArgumentException::class, IllegalAccessException::class, InvocationTargetException::class)
    fun createKey(hkey: Int, key: String) {
        val ret: IntArray
        if (hkey == HKEY_LOCAL_MACHINE) {
            ret = createKey(systemRoot, hkey, key)
            regCloseKey.invoke(systemRoot, ret[0])
        } else if (hkey == HKEY_CURRENT_USER) {
            ret = createKey(userRoot, hkey, key)
            regCloseKey.invoke(userRoot, ret[0])
        } else {
            throw IllegalArgumentException("hkey=" + hkey)
        }
        if (ret[1] != REG_SUCCESS) {
            throw IllegalArgumentException("rc=" + ret[1] + "  key=" + key)
        }
    }

    /**
     * Write a value in a given key/value name
     *
     * @param hkey
     * @param key
     * @param valueName
     * @param value
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Throws(IllegalArgumentException::class, IllegalAccessException::class, InvocationTargetException::class)
    fun writeStringValue(hkey: Int, key: String, valueName: String, value: String) {
        if (hkey == HKEY_LOCAL_MACHINE) {
            writeStringValue(systemRoot, hkey, key, valueName, value)
        } else if (hkey == HKEY_CURRENT_USER) {
            writeStringValue(userRoot, hkey, key, valueName, value)
        } else {
            throw IllegalArgumentException("hkey=" + hkey)
        }
    }

    /**
     * Delete a given key
     *
     * @param hkey
     * @param key
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Throws(IllegalArgumentException::class, IllegalAccessException::class, InvocationTargetException::class)
    fun deleteKey(hkey: Int, key: String) {
        var rc = -1
        if (hkey == HKEY_LOCAL_MACHINE) {
            rc = deleteKey(systemRoot, hkey, key)
        } else if (hkey == HKEY_CURRENT_USER) {
            rc = deleteKey(userRoot, hkey, key)
        }
        if (rc != REG_SUCCESS) {
            throw IllegalArgumentException("rc=$rc  key=$key")
        }
    }

    /**
     * delete a value from a given key/value name
     *
     * @param hkey
     * @param key
     * @param value
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Throws(IllegalArgumentException::class, IllegalAccessException::class, InvocationTargetException::class)
    fun deleteValue(hkey: Int, key: String, value: String) {
        var rc = -1
        if (hkey == HKEY_LOCAL_MACHINE) {
            rc = deleteValue(systemRoot, hkey, key, value)
        } else if (hkey == HKEY_CURRENT_USER) {
            rc = deleteValue(userRoot, hkey, key, value)
        }
        if (rc != REG_SUCCESS) {
            throw IllegalArgumentException("rc=$rc  key=$key  value=$value")
        }
    }

    // =====================
    @Throws(IllegalArgumentException::class, IllegalAccessException::class, InvocationTargetException::class)
    private fun deleteValue(root: Preferences, hkey: Int, key: String, value: String): Int {
        val handles = regOpenKey.invoke(root, *arrayOf<Any>(hkey, toCstr(key), KEY_ALL_ACCESS)) as IntArray
        if (handles[1] != REG_SUCCESS) {
            return handles[1] // can be REG_NOTFOUND, REG_ACCESSDENIED
        }
        val rc = regDeleteValue.invoke(root, *arrayOf<Any>(handles[0], toCstr(value))) as Int
        regCloseKey.invoke(root, handles[0])
        return rc
    }

    @Throws(IllegalArgumentException::class, IllegalAccessException::class, InvocationTargetException::class)
    private fun deleteKey(root: Preferences, hkey: Int, key: String): Int {
        return regDeleteKey.invoke(root, *arrayOf<Any>(hkey, toCstr(key))) as Int // can REG_NOTFOUND, REG_ACCESSDENIED, REG_SUCCESS
    }

    @Throws(IllegalArgumentException::class, IllegalAccessException::class, InvocationTargetException::class)
    private fun readString(root: Preferences, hkey: Int, key: String, value: String): String? {
        val handles = regOpenKey.invoke(root, *arrayOf<Any>(hkey, toCstr(key), KEY_READ)) as IntArray
        if (handles[1] != REG_SUCCESS) {
            return null
        }
        val valb = regQueryValueEx.invoke(root, *arrayOf<Any>(handles[0], toCstr(value))) as ByteArray
        regCloseKey.invoke(root, handles[0])
        return String(valb).trim { it <= ' ' }
    }

    @Throws(IllegalArgumentException::class, IllegalAccessException::class, InvocationTargetException::class)
    private fun readStringValues(root: Preferences, hkey: Int, key: String): Map<String, String>? {
        val results = HashMap<String, String>()
        val handles = regOpenKey.invoke(root, *arrayOf<Any>(hkey, toCstr(key), KEY_READ)) as IntArray
        if (handles[1] != REG_SUCCESS) {
            return null
        }
        val info = regQueryInfoKey.invoke(root, *arrayOf<Any>(handles[0])) as IntArray

        val count = info[0] // count
        val maxlen = info[3] // value length max
        for (index in 0 until count) {
            val name = regEnumValue.invoke(root, *arrayOf<Any>(handles[0], index, maxlen + 1)) as ByteArray
            val value = readString(hkey, key, String(name))
            results.put(String(name).trim { it <= ' ' }, value!!)
        }
        regCloseKey.invoke(root, handles[0])
        return results
    }

    @Throws(IllegalArgumentException::class, IllegalAccessException::class, InvocationTargetException::class)
    private fun readStringSubKeys(root: Preferences, hkey: Int, key: String): List<String>? {
        val results = ArrayList<String>()
        val handles = regOpenKey.invoke(root, hkey, toCstr(key), KEY_READ) as IntArray
        if (handles[1] != REG_SUCCESS) {
            return null
        }
        val info = regQueryInfoKey.invoke(root, handles[0]) as IntArray

        val count = info[0] // Fix: info[2] was being used here with wrong results. Suggested by davenpcj, confirmed by Petrucio
        val maxlen = info[3] // value length max
        for (index in 0 until count) {
            val name = regEnumKeyEx.invoke(root, handles[0], index, maxlen + 1) as ByteArray
            results.add(String(name).trim { it <= ' ' })
        }
        regCloseKey.invoke(root, handles[0])
        return results
    }

    @Throws(IllegalArgumentException::class, IllegalAccessException::class, InvocationTargetException::class)
    private fun createKey(root: Preferences, hkey: Int, key: String): IntArray {
        return regCreateKeyEx.invoke(root, hkey, toCstr(key)) as IntArray
    }

    @Throws(IllegalArgumentException::class, IllegalAccessException::class, InvocationTargetException::class)
    private fun writeStringValue(root: Preferences, hkey: Int, key: String, valueName: String, value: String) {
        val handles = regOpenKey.invoke(root, hkey, toCstr(key), KEY_ALL_ACCESS) as IntArray

        regSetValueEx.invoke(root, handles[0], toCstr(valueName), toCstr(value))
        regCloseKey.invoke(root, handles[0])
    }

    // utility
    private fun toCstr(str: String): ByteArray {
        val result = ByteArray(str.length + 1)

        for (i in 0 until str.length) {
            result[i] = str[i].toByte()
        }
        result[str.length] = 0
        return result
    }
}
