package file

import config.CONFIG_FILE_NAME
import config.WurstProjectConfig
import config.WurstProjectConfigData
import global.InstallationManager
import global.Log
import mu.KotlinLogging
import net.ConnectionManager
import org.eclipse.jgit.api.Git
import ui.UiManager
import ui.UpdateFoundDialog
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*


object SetupApp {
	val DEFAULT_DIR = Paths.get(".")!!
    private val log = KotlinLogging.logger {}
    lateinit var setup: SetupMain

    fun handleArgs(setup: SetupMain) {
        this.setup = setup
        if (setup.isGUILaunch) {
            log.info("is GUI")
            UiManager.initUI()
        } else {
            log.info("is CLI")
            handleCMD()
        }
        startup()
    }

    private fun handleCMD() {
		ConnectionManager.checkConnectivity("http://google.com")
		ConnectionManager.checkWurstBuild()
		InstallationManager.verifyInstallation()
		handleRunArgs()
    }

	private fun handleRunArgs() {
		log.info("handle runargs")
		val configFile = setup.projectRoot.resolve(CONFIG_FILE_NAME)
		var configData: WurstProjectConfigData? = null
		if (Files.exists(configFile)) {
			configData = WurstProjectConfig.loadProject(configFile)!!
		} else {
			log.warn("No wurst project found at current location")
		}

		when {
            setup.command == CLICommand.HELP -> {
                log.info("Grill version ${CompileTimeInfo.version}\nUse one of the following commands: ${CLICommand.values().joinToString(", ")}")
            }
			setup.command == CLICommand.INSTALL -> {
				if (setup.commandArg.toLowerCase() == "wurstscript") {
					handleInstallWurst()
				} else {
					if (configData != null) {
						handleInstallDep(configData)
						WurstProjectConfig.saveProjectConfig(setup.projectRoot, configData)
					}
				}
			}
			setup.command == CLICommand.UPDATE -> {
				if (setup.commandArg.toLowerCase() == "wurstscript") {
					handleInstallWurst()
				} else {
					if (configData != null) {
						handleUpdateProject(configData)
					}
				}
			}
			setup.command == CLICommand.REMOVE -> {
				if (setup.commandArg.toLowerCase() == "wurstscript") {
					handleRemoveWurst()
				} else {
					if (configData != null) {
						handleRemoveDep(configData)
						WurstProjectConfig.saveProjectConfig(setup.projectRoot, configData)
					}
				}
			}
			setup.command == CLICommand.GENERATE -> {
				if (configData == null) {
					WurstProjectConfig.handleCreate(DEFAULT_DIR.resolve(setup.commandArg), null, WurstProjectConfigData())
				}
			}
		}

	}

	private fun handleRemoveDep(configData: WurstProjectConfigData) {
		log.error("removing ${setup.commandArg}")
		if (configData.dependencies.contains(setup.commandArg)) {
			configData.dependencies.remove(setup.commandArg)
		} else {
			log.error("dependency does not exist in project")
		}
	}

	private fun handleRemoveWurst() {
		if (!setup.requireConfirmation) {
			InstallationManager.handleRemove()
		}
	}

	private fun handleUpdateProject(configData: WurstProjectConfigData) {
		WurstProjectConfig.handleUpdate(setup.projectRoot, null, configData)
	}

	private fun handleInstallDep(configData: WurstProjectConfigData) {
		log.info("installing $setup.install")
		if (configData.dependencies.contains(setup.commandArg)) {
			log.info("already installed")
			return
		}
		try {
			val result = Git.lsRemoteRepository()
				.setRemote(setup.commandArg)
				.call()
			if (!result.isEmpty()) {
				Log.print("valid!\n")
				configData.dependencies.add(setup.commandArg)
			} else {
				log.error("Entered invalid git repo")
			}
		} catch (e: Exception) {
			log.error("Entered invalid git repo")
			e.printStackTrace()
		}
	}

	private fun handleInstallWurst() {
		log.info("install/update wurstscript")
		if (InstallationManager.status != InstallationManager.InstallationStatus.INSTALLED_UPTODATE) {
			log.info("compiler update eligible")
			if (setup.requireConfirmation) {
				if (setup.isGUILaunch) {
					UpdateFoundDialog("A Wurst compiler update has been found!")
				} else {
					log.info("Do you want to update your wurst installation? (y/n)")
					val sc = Scanner(System.`in`)
					val line = sc.nextLine()
					if (line == "y") {
						InstallationManager.handleUpdate()
					}
				}
			} else {
				log.info("Forcing update..")
				InstallationManager.handleUpdate()
			}
		} else {
			log.info("Already up to date.")
		}
	}

	private fun startup() {
        log.info("startup setup version: <{}>", CompileTimeInfo.version)
        InstallationManager.verifyInstallation()
        copyJar()
    }

    private fun copyJar() {
        val url = InstallationManager::class.java.protectionDomain.codeSource.location
        val ownFile = Paths.get(url.toURI())
        if (ownFile.endsWith(".2.jar")) {
            log.info("copy jar from own")
            Files.copy(ownFile, ownFile.resolveSibling("WurstSetup.jar"), StandardCopyOption.REPLACE_EXISTING)
        }
        log.info("path: $url")
        log.info("file: " + ownFile.toAbsolutePath())
        if (ownFile != null && Files.exists(ownFile) && ownFile.toString().endsWith(".jar") &&
                (ownFile.parent == null || ownFile.parent?.fileName?.toString() != ".wurst")) {
            log.info("copy jar")
            Files.copy(ownFile, Paths.get(InstallationManager.installDir.toString(), "WurstSetup.jar"), StandardCopyOption.REPLACE_EXISTING)
        }
    }
}
