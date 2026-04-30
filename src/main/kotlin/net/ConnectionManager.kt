package net

import mu.KotlinLogging
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.regex.Pattern

enum class NetStatus {
    CLIENT_OFFLINE,
    SERVER_OFFLINE,
    SERVER_CONTACT,
    ONLINE
}

object ConnectionManager {
    private const val COMPILER_RELEASE_URL = "https://api.github.com/repos/wurstscript/WurstScript/releases/tags/nightly"
    private val UPDATED_AT_PATTERN = Pattern.compile("\"updated_at\"\\s*:\\s*\"(\\d{4})-(\\d{2})-(\\d{2})T")

    private val log = KotlinLogging.logger {}
    var netStatus = NetStatus.CLIENT_OFFLINE

    fun checkConnectivity(url: String): NetStatus {
        netStatus = NetStatus.SERVER_CONTACT
        try {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.connectTimeout = 14_000
            connection.readTimeout = 20_000
            connection.requestMethod = "GET"
            connection.inputStream.use { }
        } catch (e: IOException) {
            log.debug("couldn't contact: " + e.localizedMessage)
            netStatus = NetStatus.CLIENT_OFFLINE
        }

        return netStatus
    }

    fun getLatestSetupBuild(): Int {
        // Grill no longer has a dedicated update channel here.
        return 0
    }

    fun getLatestCompilerBuild(): Int {
        log.debug("getting latest compiler build")
        val payload = fetchReleaseJson() ?: return 0
        val matcher = UPDATED_AT_PATTERN.matcher(payload)
        if (!matcher.find()) {
            return 0
        }
        return matcher.group(1).toInt() * 10000 + matcher.group(2).toInt() * 100 + matcher.group(3).toInt()
    }

    fun checkWurstBuild(): NetStatus {
        log.debug("checking wurst build")
        netStatus = if (fetchReleaseJson() != null) NetStatus.ONLINE else NetStatus.SERVER_OFFLINE
        return netStatus
    }

    private fun fetchReleaseJson(): String? {
        return try {
            val connection = URL(COMPILER_RELEASE_URL).openConnection() as HttpURLConnection
            connection.connectTimeout = 14_000
            connection.readTimeout = 20_000
            connection.setRequestProperty("Accept", "application/vnd.github+json")
            connection.setRequestProperty("User-Agent", "WurstSetup")
            connection.inputStream.bufferedReader().use { it.readText() }.takeIf { it.isNotBlank() }
        } catch (e: IOException) {
            log.debug("couldn't contact wurst release api: " + e.localizedMessage)
            null
        }
    }
}
