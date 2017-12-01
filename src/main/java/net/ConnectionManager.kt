package net

import us.monoid.json.JSONObject
import us.monoid.web.Resty

enum class NetStatus {
    CLIENT_OFFLINE,
    SERVER_OFFLINE,
    ONLINE
}

object ConnectionManager {
    val resty = Resty()
    var netStatus = NetStatus.CLIENT_OFFLINE

    fun checkConnectivity() : NetStatus {
        // If google can be reached, the client is not offline
        val json = resty.json("http://google.com")
        if (json == null || json.toString().isBlank()) {
            netStatus = NetStatus.CLIENT_OFFLINE
            return netStatus
        }

        val wurstResponse = resty.json("http://peeeq.de/hudson/job/Wurst/lastSuccessfulBuild/api/json")
        if (wurstResponse == null || wurstResponse.toString().isBlank()) {
            netStatus = NetStatus.SERVER_OFFLINE
            return netStatus
        }

        netStatus = NetStatus.ONLINE
        return netStatus
    }

    fun getLatestSetupBuild(): Int {
        val response = resty.json("http://peeeq.de/hudson/job/WurstSetup/lastSuccessfulBuild/api/json")
                .get(Resty.path("actions[2].buildsByBranchName")).toString()
        val innerObject = JSONObject(JSONObject(response).get("refs/remotes/origin/master").toString())
        return innerObject.get("buildNumber").toString().toInt()
    }

    fun getLatestCompilerBuild(): Int {
        val response = resty.json("http://peeeq.de/hudson/job/Wurst/lastSuccessfulBuild/api/json")
                .get(Resty.path("actions[2].buildsByBranchName")).toString()
        val innerObject = JSONObject(JSONObject(response).get("refs/remotes/origin/master").toString())
        return innerObject.get("buildNumber").toString().toInt()
    }
}