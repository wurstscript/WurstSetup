package config

const val CONFIG_FILE_NAME = "wurst.build"

typealias ScriptMode = org.wurstscript.projectconfig.ScriptMode
typealias Race = org.wurstscript.projectconfig.Race
typealias Controller = org.wurstscript.projectconfig.Controller
typealias WurstProjectConfigData = org.wurstscript.projectconfig.WurstProjectConfigData
typealias WurstProjectBuildMapData = org.wurstscript.projectconfig.WurstProjectBuildMapData
typealias WurstProjectBuildScenarioData = org.wurstscript.projectconfig.WurstProjectBuildScenarioData
typealias WurstProjectBuildLoadingScreenData = org.wurstscript.projectconfig.WurstProjectBuildLoadingScreenData
typealias WurstProjectBuildOptionFlagsData = org.wurstscript.projectconfig.WurstProjectBuildOptionFlagsData
typealias WurstProjectBuildPlayer = org.wurstscript.projectconfig.WurstProjectBuildPlayer
typealias WurstProjectBuildForce = org.wurstscript.projectconfig.WurstProjectBuildForce
typealias WurstProjectBuildForceFlags = org.wurstscript.projectconfig.WurstProjectBuildForceFlags

fun newProjectConfig(
    projectName: String = "unnamed",
    dependencies: List<String> = emptyList(),
    buildMapData: WurstProjectBuildMapData = WurstProjectBuildMapData.empty(),
    scriptMode: ScriptMode? = null,
    wc3Patch: String? = null
): WurstProjectConfigData {
    return WurstProjectConfigData(projectName, dependencies, buildMapData, scriptMode, wc3Patch)
}

fun WurstProjectConfigData.withProjectName(projectName: String): WurstProjectConfigData {
    return WurstProjectConfigData(projectName, dependencies(), buildMapData(), scriptMode(), wc3Patch())
}

fun WurstProjectConfigData.withWc3Patch(wc3Patch: String?): WurstProjectConfigData {
    return WurstProjectConfigData(projectName(), dependencies(), buildMapData(), scriptMode(), wc3Patch)
}

fun WurstProjectConfigData.withDependencies(dependencies: List<String>): WurstProjectConfigData {
    return WurstProjectConfigData(projectName(), dependencies, buildMapData(), scriptMode(), wc3Patch())
}

fun WurstProjectConfigData.withAddedDependency(dependency: String): WurstProjectConfigData {
    return withDependencies(dependencies() + dependency)
}

fun WurstProjectConfigData.withRemovedDependency(dependency: String): WurstProjectConfigData {
    return withDependencies(dependencies().filterNot { it == dependency })
}
