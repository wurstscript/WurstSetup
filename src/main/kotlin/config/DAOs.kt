package config

import com.fasterxml.jackson.annotation.JsonInclude
import java.util.*
import kotlin.collections.ArrayList


const val CONFIG_FILE_NAME = "wurst.build"

/**
 * The root DAO that contains the child DAOs.
 * Represents a complete wurst.build file.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
data class WurstProjectConfigData(var projectName: String = "DefaultName",
                                  val dependencies: ArrayList<String> = ArrayList(Arrays.asList("https://github.com/wurstscript/wurstStdlib2")),
//                                  val jobs: ArrayList<WurstProjectBuildJob> =
//                                      ArrayList(Arrays.asList(WurstProjectBuildJob("run",
//                                          ArrayList(Arrays.asList("runcompiletimefunctions", "injectobjects", "stacktraces"))),
//                                      WurstProjectBuildJob("build",
//                                        ArrayList(Arrays.asList("runcompiletimefunctions", "injectobjects", "stacktraces", "opt", "inline", "localOptimizations"))))),
                                  val buildMapData: WurstProjectBuildMapData = WurstProjectBuildMapData())

/** All data needed to generate the output map */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
data class WurstProjectBuildMapData(val name: String = "",
                                    val fileName: String = "",
                                    val author: String = "",
                                    val scenarioData: WurstProjectBuildScenarioData = WurstProjectBuildScenarioData(loadingScreen=null),
                                    val optionsFlags: WurstProjectBuildOptionFlagsData = WurstProjectBuildOptionFlagsData(),
                                    val players: ArrayList<WurstProjectBuildPlayer> = ArrayList(),
                                    val forces: ArrayList<WurstProjectBuildForce> = ArrayList())

/** Wurst job data with list of arguments */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
data class WurstProjectBuildJob(val name: String = "DefaultJobName",
                                val args: ArrayList<String> = ArrayList(Arrays.asList()))

/** Scenario related information */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
data class WurstProjectBuildScenarioData(val description: String = "",
                                         val suggestedPlayers: String = "",
                                         var loadingScreen: WurstProjectBuildLoadingScreenData?)

/** Load screen information. */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
data class WurstProjectBuildLoadingScreenData(val model: String = "Generic.mdx",
                                              val title: String = "DefaultTitle",
                                              val subTitle: String = "DefaultSubTitle",
                                              val text: String = "DefaultText")

/** Map build flags */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
data class WurstProjectBuildOptionFlagsData(val hideMinimapPreview: Boolean = false,
                                            val maskedAreasPartiallyVisible: Boolean = true,
                                            val showWavesOnCliffShores: Boolean = true,
                                            val showWavesOnRollingShores: Boolean = true,
                                            val useItemClassificationSystem: Boolean = true)

/** Data for one force (team) in the map */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
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
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
data class WurstProjectBuildForceFlags(val allied: Boolean = true,
                                       val alliedVictory: Boolean = true,
                                       val sharedVision: Boolean = true,
                                       val sharedControl: Boolean = false,
                                       val sharedControlAdvanced: Boolean = false)

/** Player race */
enum class Race {
    HUMAN, ORC, UNDEAD, NIGHT_ELF, SELECTABLE
}

/** Player controller */
enum class Controller {
    USER, COMPUTER, NEUTRAL, RESCUABLE
}

/** Data for one player */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
data class WurstProjectBuildPlayer(val id: Int = -1,
                                   val name: String?,
                                   val race: Race?,
                                   val controller: Controller?,
                                   val fixedStartLoc: Boolean?)

