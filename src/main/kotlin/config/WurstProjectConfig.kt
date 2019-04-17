package config


import file.*
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
	private val BACKSLASH_REGEX = "\\\\".toRegex()
	private const val BACKSLASH_QUERY = "\\\\\\\\"

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
        if (Files.exists(buildFile) && buildFile.fileName.toString().equals(CONFIG_FILE_NAME, ignoreCase = true)) {
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
        if (Files.exists(projectRoot) && Files.list(projectRoot).filter { !Files.isDirectory(it) }.findAny().isPresent) {
            log.error("Project root already exists and contains files")
            Log.print("\nError: Project root already exists!\n")
        } else {
            Files.createDirectories(projectRoot)
            Log.print("done\n")

            Log.print("Download template..")
            log.info("⏬ Downloading template..")
            Download.downloadBareboneProject {
				extractDownload(it, projectRoot, gameRoot, projectConfig)
			}
        }
    }

	private fun extractDownload(it: Path, projectRoot: Path, gameRoot: Path?, projectConfig: WurstProjectConfigData) {
		Log.println(" done.")

		Log.print("Extracting template..")
		val extractSuccess = ZipArchiveExtractor.extractArchive(it, projectRoot)
		Files.delete(it)
		if (extractSuccess) {
			Log.print("done\n")
			cleanupDownload(projectRoot)
		} else {
			Log.print("error\n")
			JOptionPane.showMessageDialog(null,
				"Error: Cannot extract patch files.\nWurst might still be in use.\nClose any Wurst, VSCode or Eclipse instances before updating.",
				"Error Massage", JOptionPane.ERROR_MESSAGE)
		}

		setupEnvironment(projectRoot, gameRoot, projectConfig)

        log.info("✔ Project generated.")
		UiManager.refreshComponents()
	}

	private fun cleanupDownload(projectRoot: Path) {
		Log.print("Clean up..")
		val folder = projectRoot.resolve("WurstBareboneTemplate-master")
		copyFolder(folder, projectRoot)
		Files.walk(folder).sorted { a, b -> b.compareTo(a) }.forEach { p ->
			try {
				Files.delete(p)
			} catch (e: IOException) {
			}
		}
	}

	private fun setupEnvironment(projectRoot: Path, gameRoot: Path?, projectConfig: WurstProjectConfigData) {
		Log.print("done\n")

		setupVSCode(projectRoot, gameRoot)

		saveProjectConfig(projectRoot, projectConfig)

		DependencyManager.updateDependencies(projectRoot, projectConfig)

		Log.print("---\n\n")
		if (gameRoot == null || !Files.exists(gameRoot)) {
			Log.print("Warning: Your game path has not been set.\nThis means you will be able to develop, but not run maps.\n")
		}
		Log.print("Your project has been successfully created!\n" + "You can now open your project folder in VSCode.\nOpen the wurst/Hello.wurst package to continue.\n")
	}


	@Throws(IOException::class)
    fun saveProjectConfig(projectRoot: Path, projectConfig: WurstProjectConfigData) {
        val projectYaml = YamlHelper.dumpProjectConfig(projectConfig)
        Files.write(projectRoot.resolve(CONFIG_FILE_NAME), projectYaml.toByteArray())
    }


    @Throws(IOException::class)
    private fun setupVSCode(projectRoot: Path?, gamePath: Path?) {
        Log.print("Updating vsconfig..")
        if (!Files.exists(projectRoot)) {
            throw IOException("Project root does not exist!")
        }
        val vsCode = projectRoot?.resolve(".vscode/settings.json")
		createConfigFile(vsCode)

		setConfigValues(vsCode, gamePath)
		Log.print("done.\n")
    }

	private fun setConfigValues(vsCode: Path?, gamePath: Path?) {
		val json = replacePlaceholders(vsCode, gamePath?.toAbsolutePath().toString())

		Files.write(vsCode, json.toByteArray(), StandardOpenOption.TRUNCATE_EXISTING)
	}

	private fun createConfigFile(vsCode: Path?) {
		if (!Files.exists(vsCode)) {
			Files.createDirectories(vsCode?.parent)
			Files.write(vsCode, VSCODE_MIN_CONFIG.toByteArray(), StandardOpenOption.CREATE_NEW)
		}
	}

	private fun replacePlaceholders(vsCode: Path?, gamePath: String): String {
		var json = String(Files.readAllBytes(vsCode))
		val absolutePath = InstallationManager.getCompilerPath()
		json = json.replace("%wurstjar%", absolutePath.replace(BACKSLASH_REGEX, BACKSLASH_QUERY))

		if (!gamePath.isBlank()) {
			json = json.replace("%gamepath%", gamePath.replace(BACKSLASH_REGEX, BACKSLASH_QUERY))
		}
		return json
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
                    "       \"$CONFIG_FILE_NAME\": \"yaml\"\n" +
                    "   }\n" +
                    "}"
}

