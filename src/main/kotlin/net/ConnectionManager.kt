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

        contactWurstServer("https://peeeq.de/hudson/job/Wurst/lastSuccessfulBuild/api/json")
        if (netStatus == NetStatus.SERVER_OFFLINE) {
            contactWurstServer("http://peeeq.de/hudson/job/Wurst/lastSuccessfulBuild/api/json")
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
        return getBuildNumber("https://peeeq.de/hudson/job/WurstSetup/lastSuccessfulBuild/api/json", "refs/remotes/origin/master")
    }

    fun getLatestCompilerBuild(): Int {
        return getBuildNumber("https://peeeq.de/hudson/job/Wurst/lastSuccessfulBuild/api/json", "refs/remotes/origin/master")
    }

}