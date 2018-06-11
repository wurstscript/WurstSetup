package net

import mu.KotlinLogging
import us.monoid.json.JSONArray
import us.monoid.json.JSONObject
import us.monoid.web.Resty
import java.io.IOException

enum class NetStatus {
    CLIENT_OFFLINE,
    SERVER_OFFLINE,
    SERVER_CONTACT,
    ONLINE
}

object ConnectionManager {
    private const val WURST_SETUP_URL = "peeeq.de/hudson/job/WurstSetup/lastSuccessfulBuild/api/json"
    private const val WURST_COMPILER_URL = "peeeq.de/hudson/job/Wurst/lastSuccessfulBuild/api/json"
    private const val MASTER_BRANCH = "refs/remotes/origin/master"

    private val log = KotlinLogging.logger {}
    private val resty = Resty()
    var netStatus = NetStatus.CLIENT_OFFLINE

    private fun findJsonTag(url: String, path: String, name: String): JSONObject {
        val actions = JSONArray(resty.json(url).get(Resty.path(path)).toString())

        return (0 until actions.length())
                .map { JSONObject(actions[it].toString()) }
                .firstOrNull { it.has(name) }
                ?.getJSONObject(name)
                ?: JSONObject()
    }

    fun checkConnectivity(): NetStatus {
        // If google can be reached, the client is not offline
        try {
            val json = resty.json("http://google.com")
            if (json == null || json.toString().isBlank()) {
                netStatus = NetStatus.CLIENT_OFFLINE
                return netStatus
            }
        } catch (e: IOException) {
            log.info("couldn't contact google: " + e.localizedMessage)
        }
        netStatus = NetStatus.SERVER_CONTACT

        return netStatus
    }

    private fun contactWurstServer(url: String) {
        netStatus = try {
            val wurstResponse = resty.json(url)
            if (wurstResponse == null || wurstResponse.toString().isBlank()) {
                NetStatus.SERVER_OFFLINE
            } else {
                NetStatus.ONLINE
            }
        } catch (e: IOException) {
            log.info("couldn't contact wurst jenkins: " + e.localizedMessage)
            NetStatus.SERVER_OFFLINE
        }
    }

    private fun getBuildNumber(url: String, branch: String): Int {
        if (netStatus != NetStatus.ONLINE) return 0
        val response = findJsonTag(url, "actions", "buildsByBranchName")
        if (response.has(branch)) {
            val innerObject = JSONObject(response.get(branch).toString())
            return innerObject.get("buildNumber").toString().toInt()
        }
        return -1
    }

    fun getLatestSetupBuild(): Int {
        return try {
            getBuildNumber("https://" + WURST_SETUP_URL, MASTER_BRANCH)
        } catch (e: IOException) {
            getBuildNumber("http://" + WURST_SETUP_URL, MASTER_BRANCH)
        }
    }

    fun getLatestCompilerBuild(): Int {
        return try {
            getBuildNumber("https://" + WURST_COMPILER_URL, MASTER_BRANCH)
        } catch (e: IOException) {
            getBuildNumber("http://" + WURST_COMPILER_URL, MASTER_BRANCH)
        }
    }

    fun checkWurstBuild() {
        contactWurstServer("https://" + WURST_COMPILER_URL)
        if (netStatus == NetStatus.SERVER_OFFLINE) {
            contactWurstServer("http://" + WURST_COMPILER_URL)
        }
    }

}