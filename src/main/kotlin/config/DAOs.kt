package config

import java.util.*
import kotlin.collections.ArrayList

data class WurstProjectConfigData(var projectName: String = "DefaultName",
                                  val dependencies: ArrayList<String> = ArrayList(Arrays.asList("https://github.com/wurstscript/wurstStdlib2")),
                                  val buildMapData: WurstProjectBuildMapData = WurstProjectBuildMapData())

data class WurstProjectBuildMapData(val name: String = "JustAnotherWurstMap",
                                    val fileName: String = "MyWurstMap",
                                    val author: String = "SomeWurstUser",
                                    val playerCount: Int = 1,
                                    val mapBoundsX: Int = 64,
                                    val mapBoundsY: Int = 64,
                                    val scenarioData: WurstProjectBuildScenarioData = WurstProjectBuildScenarioData(),
                                    val optionsFlags: WurstProjectBuildOptionFlagsData = WurstProjectBuildOptionFlagsData(),
                                    val players: ArrayList<WurstProjectBuildPlayer> = ArrayList(Arrays.asList(WurstProjectBuildPlayer())),
                                    val forces: ArrayList<WurstProjectBuildForce> = ArrayList(Arrays.asList(WurstProjectBuildForce())))

data class WurstProjectBuildJobData(val jobs: ArrayList<WurstProjectBuildJob> = ArrayList(Arrays.asList()))

data class WurstProjectBuildJob(val name: String = "DefaultJobName",
                                val args: ArrayList<WurstProjectBuildJob> = ArrayList(Arrays.asList()))

data class WurstProjectBuildScenarioData(val description: String = "DefaultDescription",
                                         val suggestedPlayers: String = "DefaultSuggestedPlayers",
                                         val loadingScreenTitle: String = "DefaultTitle",
                                         val loadingScreenSubTitle: String = "DefaultSubTitle",
                                         val loadingScreenText: String = "DefaultText")

data class WurstProjectBuildOptionFlagsData(val hideMinimapPreview: Boolean = false,
                                            val maskedAreasPartiallyVisible: Boolean = true,
                                            val showWavesOnCliffShores: Boolean = true,
                                            val showWavesOnRollingShores: Boolean = true,
                                            val useItemClassificationSystem: Boolean = true)


data class WurstProjectBuildForce(val name: String = "DefaultForce",
                                  val flags: WurstProjectBuildForceFlags = WurstProjectBuildForceFlags(),
                                  val playerIds: Array<Int> = arrayOf(0)) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WurstProjectBuildForce

        if (name != other.name) return false
        if (flags != other.flags) return false
        if (!Arrays.equals(playerIds, other.playerIds)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + flags.hashCode()
        result = 31 * result + Arrays.hashCode(playerIds)
        return result
    }
}

data class WurstProjectBuildForceFlags(val allied: Boolean = true,
                                       val alliedVictory: Boolean = true,
                                       val sharedVision: Boolean = true,
                                       val sharedControl: Boolean = false,
                                       val sharedControlAdvanced: Boolean = false)

enum class Race {
    HUMAN, ORC, UNDEAD, NIGHTELF, SELECTABLE
}

enum class Controller {
    USER, COMPUTER, NEUTRAL, RESCUABLE
}

data class WurstProjectBuildPlayer(val id: Int = 0,
                                   val name: String = "DefaultPlayer",
                                   val race: Race = Race.SELECTABLE,
                                   val controller: Controller = Controller.USER,
                                   val fixedStartLoc: Boolean = false)

