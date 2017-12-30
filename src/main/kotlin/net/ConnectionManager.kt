package net

import mu.KotlinLogging
import us.monoid.json.JSONObject
import us.monoid.web.Resty
import java.io.IOException

enum class NetStatus {
    CLIENT_OFFLINE,
    SERVER_OFFLINE,
    ONLINE
}

object ConnectionManager {
    val WURST_SETUP_URL = "peeeq.de/hudson/job/WurstSetup/lastSuccessfulBuild/api/json"
    val WURST_COMPILER_URL = "peeeq.de/hudson/job/Wurst/lastSuccessfulBuild/api/json"
    val MASTER_BRANCH = "refs/remotes/origin/master"

    private val log = KotlinLogging.logger {}
    private val resty = Resty()
    var netStatus = NetStatus.CLIENT_OFFLINE

    private fun getJson(url: String, path: String): JSONObject {
        val response = resty.json(url).get(Resty.path(path)).toString()
        return JSONObject(response)
    }

    fun checkConnectivity(): NetStatus {
        // If google can be reached, the client is not offline
        try {
            val json = resty.json("https://google.com")
            if (json == null || json.toString().isBlank()) {
                netStatus = NetStatus.CLIENT_OFFLINE
                return netStatus
            }
        } catch (e: IOException) {
            log.info("couldn't contact google: " + e.localizedMessage)
        }

        contactWurstServer("https://" + WURST_COMPILER_URL)
        if (netStatus == NetStatus.SERVER_OFFLINE) {
            contactWurstServer("http://" + WURST_COMPILER_URL)
        }

        return netStatus
    }

    private fun contactWurstServer(url: String) {
        try {
            val wurstResponse = resty.json(url)
            if (wurstResponse == null || wurstResponse.toString().isBlank()) {
                netStatus = NetStatus.SERVER_OFFLINE
            } else {
                netStatus = NetStatus.ONLINE
            }
        } catch (e: IOException) {
            log.info("couldn't contact wurst jenkins: " + e.localizedMessage)
            netStatus = NetStatus.SERVER_OFFLINE
        }
    }

    fun getBuildNumber(url: String, branch: String): Int {
        if (netStatus != NetStatus.ONLINE) return 0
        val response = getJson(url, "actions[2].buildsByBranchName")
        val innerObject = JSONObject(response.get(branch).toString())
        return innerObject.get("buildNumber").toString().toInt()
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

}