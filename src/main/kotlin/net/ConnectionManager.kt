package net

import logging.KotlinLogging
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
    private const val COMPILER_LATEST_RELEASE_URL = "https://api.github.com/repos/wurstscript/WurstScript/releases/latest"
    private const val COMPILER_RELEASES_URL = "https://api.github.com/repos/wurstscript/WurstScript/releases?per_page=1"
    private const val COMPILER_ASSET_URL = "https://github.com/wurstscript/WurstScript/releases/download/nightly/wurst-compiler-nightly-linux-x64.zip"
    private val UPDATED_AT_PATTERN = Pattern.compile("\"(?:updated_at|published_at|created_at)\"\\s*:\\s*\"(\\d{4})-(\\d{2})-(\\d{2})T")

    private val log = KotlinLogging.logger {}
    var netStatus = NetStatus.CLIENT_OFFLINE
    var lastError: String = ""
        private set

    fun checkConnectivity(url: String): NetStatus {
        netStatus = NetStatus.SERVER_CONTACT
        try {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.connectTimeout = 14_000
            connection.readTimeout = 20_000
            connection.requestMethod = "GET"
            connection.inputStream.use { }
            lastError = ""
        } catch (e: IOException) {
            log.debug("couldn't contact: " + e.localizedMessage)
            lastError = e.localizedMessage ?: e.toString()
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
        netStatus = if (fetchReleaseJson() != null || canReachCompilerAsset()) NetStatus.ONLINE else NetStatus.SERVER_OFFLINE
        return netStatus
    }

    private fun fetchReleaseJson(): String? {
        for (url in listOf(COMPILER_RELEASE_URL, COMPILER_LATEST_RELEASE_URL, COMPILER_RELEASES_URL)) {
            val payload = fetchText(url)
            if (!payload.isNullOrBlank()) {
                return payload
            }
        }
        return null
    }

    private fun fetchText(url: String): String? {
        return try {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.connectTimeout = 14_000
            connection.readTimeout = 20_000
            connection.setRequestProperty("Accept", "application/vnd.github+json")
            connection.setRequestProperty("User-Agent", "WurstSetup")
            if (connection.responseCode in 200..299) {
                lastError = ""
                connection.inputStream.bufferedReader().use { it.readText() }.takeIf { it.isNotBlank() }
            } else {
                lastError = "$url returned HTTP ${connection.responseCode}"
                log.debug("couldn't contact wurst release api: $lastError")
                null
            }
        } catch (e: Exception) {
            lastError = "$url failed: ${e.localizedMessage ?: e.toString()}"
            log.debug("couldn't contact wurst release api: $lastError")
            null
        }
    }

    private fun canReachCompilerAsset(): Boolean {
        return try {
            val connection = URL(COMPILER_ASSET_URL).openConnection() as HttpURLConnection
            connection.connectTimeout = 14_000
            connection.readTimeout = 20_000
            connection.requestMethod = "HEAD"
            connection.setRequestProperty("User-Agent", "WurstSetup")
            val reachable = connection.responseCode in 200..399
            if (reachable) {
                lastError = ""
            } else {
                lastError = "$COMPILER_ASSET_URL returned HTTP ${connection.responseCode}"
            }
            reachable
        } catch (e: Exception) {
            lastError = "$COMPILER_ASSET_URL failed: ${e.localizedMessage ?: e.toString()}"
            log.debug("couldn't contact wurst compiler asset: $lastError")
            false
        }
    }
}
