package config

import file.DependencyManager
import file.Download
import file.YamlHelper
import file.ZipArchiveExtractor
import global.InstallationManager
import global.Log
import mu.KotlinLogging
import ui.UiManager
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption
import javax.swing.JOptionPane


/**
 * Created by Frotty on 10.07.2017.
 */



object WurstProjectConfig {
    private val log = KotlinLogging.logger {}
    fun handleCreate(projectRoot: Path, gameRoot: Path?, projectConfig: WurstProjectConfigData) {
        try {
            createProject(projectRoot, gameRoot, projectConfig)
            UiManager.refreshComponents()
        } catch (e: Exception) {
            Log.print("\n===ERROR PROJECT CREATE===\n" + e.message + "\nPlease report here: github.com/wurstscript/WurstScript/issues\n")
        }

    }


    @Throws(IOException::class)
    fun loadProject(buildFile: Path): WurstProjectConfigData? {
        Log.println("Loading project..")
        if (Files.exists(buildFile) && buildFile.fileName.toString().equals("wurst.build", ignoreCase = true)) {
            val config = YamlHelper.loadProjectConfig(buildFile)
            val projectRoot = buildFile.parent
            if (config.projectName.isEmpty()) {
                config.projectName = projectRoot?.fileName.toString()
                WurstProjectConfig.saveProjectConfig(projectRoot, config)
            }
            Log.print("done\n")
            return config
        }
        return null
    }

    @Throws(Exception::class)
    private fun createProject(projectRoot: Path, gameRoot: Path?, projectConfig: WurstProjectConfigData) {
        Log.print("Creating project root..")
        if (Files.exists(projectRoot) && Files.list(projectRoot).filter({ !Files.isDirectory(it) }).findAny().isPresent) {
            log.error("Project root already exists and contains files")
            Log.print("\nError: Project root already exists!\n")
        } else {
            Files.createDirectories(projectRoot)
            Log.print("done\n")

            Log.print("Download template..")
            Download.downloadBareboneProject {
                Log.println(" done.")

                Log.print("Extracting template..")
                val extractSuccess = ZipArchiveExtractor.extractArchive(it, projectRoot)
                Files.delete(it)
                if (extractSuccess) {
                    Log.print("done\n")
                    Log.print("Clean up..")
                    val folder = projectRoot.resolve("WurstBareboneTemplate-master")
                    copyFolder(folder, projectRoot)
                    Files.walk(folder).sorted { a, b -> b.compareTo(a) }.forEach { p ->
                        try {
                            Files.delete(p)
                        } catch (e: IOException) {
                        }
                    }
                } else {
                    Log.print("error\n")
                    JOptionPane.showMessageDialog(null,
                            "Error: Cannot extract patch files.\nWurst might still be in use.\nClose any Wurst, VSCode or Eclipse instances before updating.",
                            "Error Massage", JOptionPane.ERROR_MESSAGE)
                }

                Log.print("done\n")

                setupVSCode(projectRoot, gameRoot)

                saveProjectConfig(projectRoot, projectConfig)

                DependencyManager.updateDependencies(projectRoot, projectConfig)

                Log.print("---\n\n")
                if (gameRoot == null || !Files.exists(gameRoot)) {
                    Log.print("Warning: Your game path has not been set.\nThis means you will be able to develop, but not run maps.\n")
                }
                Log.print("Your project has been successfully created!\n" + "You can now open your project folder in VSCode.\nOpen the wurst/Hello.wurst package to continue.\n")
                UiManager.refreshComponents()
            }
        }
    }

    fun copyFolder(src: Path, dest: Path) {
        try {
            Files.walk(src)
                    .forEach { s ->
                        try {
                            val d = dest.resolve(src.relativize(s))
                            if (Files.isDirectory(s)) {
                                if (!Files.exists(d))
                                    Files.createDirectory(d)
                            } else {
                                Files.copy(s, d)// use flag to override existing
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    @Throws(IOException::class)
    private fun saveProjectConfig(projectRoot: Path, projectConfig: WurstProjectConfigData) {
        val projectYaml = YamlHelper.dumpProjectConfig(projectConfig)
        Files.write(projectRoot.resolve("wurst.build"), projectYaml.toByteArray())
    }

    @Throws(IOException::class)
    private fun setupVSCode(projectRoot: Path?, gamePath: Path?) {
        Log.print("Updating vsconfig..")
        if (!Files.exists(projectRoot)) {
            throw IOException("Project root does not exist!")
        }
        val vsCode = projectRoot?.resolve(".vscode/settings.json")
        if (!Files.exists(vsCode)) {
            Files.createDirectories(vsCode?.parent)
            Files.write(vsCode, VSCODE_MIN_CONFIG.toByteArray(), StandardOpenOption.CREATE_NEW)
        }
        var json = String(Files.readAllBytes(vsCode))
        val absolutePath = InstallationManager.compilerJar.toAbsolutePath().toString()
        json = json.replace("%wurstjar%", absolutePath.replace("\\\\".toRegex(), "\\\\\\\\"))

        if (gamePath != null && Files.exists(gamePath)) {
            json = json.replace("%gamepath%", gamePath.toAbsolutePath().toString().replace("\\\\".toRegex(), "\\\\\\\\"))
        }
        Files.write(vsCode, json.toByteArray(), StandardOpenOption.TRUNCATE_EXISTING)
        Log.print("done.\n")
    }

    fun handleUpdate(projectRoot: Path, gamePath: Path?, config: WurstProjectConfigData) {
        Log.print("Updating project...\n")
        try {
            WurstProjectConfig.setupVSCode(projectRoot, gamePath)
            WurstProjectConfig.saveProjectConfig(projectRoot, config)
            DependencyManager.updateDependencies(projectRoot, config)

            Log.print("Project successfully updated!\nReload vscode to apply the changed dependencies.\n")
            UiManager.refreshComponents()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.print("\n===ERROR PROJECT UPDATE===\n" + e.message + "\nPlease report here: github.com/wurstscript/WurstScript/issues\n")
        }

    }

    private const val VSCODE_MIN_CONFIG =
            "{\n" +
                    "   \"wurst.wurstJar\": \"%wurstjar%\",\n" +
                    "   \"wurst.wc3path\": \"%gamepath%\",\n" +
                    "   \"files.associations\": {\n" +
                    "       \"wurst.build\": \"yaml\"\n" +
                    "   }\n" +
                    "}"
}
