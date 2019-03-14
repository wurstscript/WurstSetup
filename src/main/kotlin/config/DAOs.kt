package config

import java.util.*
import kotlin.collections.ArrayList

/**
 * The root DAO that contains the child DAOs.
 * Represents a complete wurst.build file.
 */
data class WurstProjectConfigData(var projectName: String = "DefaultName",
                                  val dependencies: ArrayList<String> = ArrayList(Arrays.asList("https://github.com/wurstscript/wurstStdlib2")),
//                                  val jobs: ArrayList<WurstProjectBuildJob> =
//                                      ArrayList(Arrays.asList(WurstProjectBuildJob("run",
//                                          ArrayList(Arrays.asList("runcompiletimefunctions", "injectobjects", "stacktraces"))),
//                                      WurstProjectBuildJob("build",
//                                        ArrayList(Arrays.asList("runcompiletimefunctions", "injectobjects", "stacktraces", "opt", "inline", "localOptimizations"))))),
                                  val buildMapData: WurstProjectBuildMapData = WurstProjectBuildMapData())

/** All data needed to generate the output map */
data class WurstProjectBuildMapData(val name: String = "JustAnotherWurstMap",
                                    val fileName: String = "MyWurstMap",
                                    val author: String = "SomeWurstUser",
                                    val scenarioData: WurstProjectBuildScenarioData = WurstProjectBuildScenarioData(loadingScreen=null),
                                    val optionsFlags: WurstProjectBuildOptionFlagsData = WurstProjectBuildOptionFlagsData(),
                                    val players: ArrayList<WurstProjectBuildPlayer> = ArrayList(),
                                    val forces: ArrayList<WurstProjectBuildForce> = ArrayList())

/** Wurst job data with list of arguments */
data class WurstProjectBuildJob(val name: String = "DefaultJobName",
                                val args: ArrayList<String> = ArrayList(Arrays.asList()))

/** Scenario related information */
data class WurstProjectBuildScenarioData(val description: String = "WurstScript powered!",
                                         val suggestedPlayers: String = "DefaultSuggestedPlayers",
                                         var loadingScreen: WurstProjectBuildLoadingScreenData?)

/** Load screen information. */
data class WurstProjectBuildLoadingScreenData(val model: String = "Generic.mdx",
                                              val title: String = "DefaultTitle",
                                              val subTitle: String = "DefaultSubTitle",
                                              val text: String = "DefaultText")

/** Map build flags */
data class WurstProjectBuildOptionFlagsData(val hideMinimapPreview: Boolean = false,
                                            val maskedAreasPartiallyVisible: Boolean = true,
                                            val showWavesOnCliffShores: Boolean = true,
                                            val showWavesOnRollingShores: Boolean = true,
                                            val useItemClassificationSystem: Boolean = true)

/** Data for one force (team) in the map */
data class WurstProjectBuildForce(val name: String = "DefaultForce",
                                  val flags: WurstProjectBuildForceFlags = WurstProjectBuildForceFlags(),
                                  val playerIds: IntArray = intArrayOf(0)) {

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

/** Force flags */
data class WurstProjectBuildForceFlags(val allied: Boolean = true,
                                       val alliedVictory: Boolean = true,
                                       val sharedVision: Boolean = true,
                                       val sharedControl: Boolean = false,
                                       val sharedControlAdvanced: Boolean = false)

/** Player race */
enum class Race {
    HUMAN, ORC, UNDEAD, NIGHTELF, SELECTABLE
}

/** Player controller */
enum class Controller {
    USER, COMPUTER, NEUTRAL, RESCUABLE
}

/** Data for one player */
data class WurstProjectBuildPlayer(val id: Int = 0,
                                   val name: String = "DefaultPlayer",
                                   val race: Race = Race.SELECTABLE,
                                   val controller: Controller = Controller.USER,
                                   val fixedStartLoc: Boolean = false)

