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
        if (!setup.silent) {
            log.info("is GUI launch")
            UiManager.initUI()
        } else {
            log.info("is silent launch")
            handleCMD()
        }
        startup()
    }

    private fun handleCMD() {
		ConnectionManager.checkConnectivity()
		ConnectionManager.checkWurstBuild()
		InstallationManager.verifyInstallation()
		handleRunArgs()
    }

	private fun handleRunArgs() {
		log.info("handle runargs")
		val configFile = DEFAULT_DIR.resolve(CONFIG_FILE_NAME)
		var configData: WurstProjectConfigData? = null
		if (Files.exists(configFile)) {
			configData = WurstProjectConfig.loadProject(configFile)!!
		} else {
			log.warn("No wurst project found at current location")
		}

		when {
			setup.install != "%unset%" -> {
				if (setup.install.toLowerCase() == "wurstscript") {
					handleInstallWurst()
				} else {
					if (configData != null) {
						handleInstallDep(configData)
					}
				}
			}
			setup.update != "%unset%" -> {
				if (setup.update.toLowerCase() == "wurstscript") {
					handleInstallWurst()
				} else {
					if (configData != null) {
						handleUpdateProject(configData)
					}
				}
			}
			setup.remove != "%unset%"  -> {
				if (setup.remove.toLowerCase() == "wurstscript") {
					handleRemoveWurst()
				} else {
					if (configData != null) {
						handleRemoveDep(configData)
					}
				}
			}
			setup.generate -> {
				if (configData == null) {
					WurstProjectConfig.handleCreate(DEFAULT_DIR, null, WurstProjectConfigData())
				}
			}
		}

	}

	private fun handleRemoveDep(configData: WurstProjectConfigData) {
		log.error("removing ${setup.remove}")
		if (configData.dependencies.contains(setup.remove)) {
			configData.dependencies.remove(setup.remove)
		} else {
			log.error("dependency does not exist in project")
		}
	}

	private fun handleRemoveWurst() {
		if (setup.force) {
			InstallationManager.handleRemove()
		}
	}

	private fun handleUpdateProject(configData: WurstProjectConfigData) {
		WurstProjectConfig.handleUpdate(DEFAULT_DIR, null, configData)
	}

	private fun handleInstallDep(configData: WurstProjectConfigData) {
		log.info("installing $setup.install")
		if (configData.dependencies.contains(setup.install)) {
			log.info("already installed")
			return
		}
		try {
			val result = Git.lsRemoteRepository()
				.setRemote(setup.install)
				.call()
			if (!result.isEmpty()) {
				Log.print("valid!\n")
				configData.dependencies.add(setup.install)
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
			if (setup.force) {
				log.info("Forcing update..")
				InstallationManager.handleUpdate()
			} else {
				if (!setup.silent) {
					UpdateFoundDialog("A Wurst compiler update has been found!")
				} else {
					log.info("Do you want to update your wurst installation? (y/n)")
					val sc = Scanner(System.`in`)
					val line = sc.nextLine()
					if (line == "y") {
						InstallationManager.handleUpdate()
					}
				}
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
