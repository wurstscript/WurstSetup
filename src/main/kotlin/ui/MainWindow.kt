package ui

import config.CONFIG_FILE_NAME
import config.WurstProjectBuildMapData
import config.WurstProjectConfig
import config.WurstProjectConfigData
import de.ralleytn.simple.registry.Registry
import file.CompileTimeInfo
import file.SetupApp
import global.InstallationManager
import global.Log
import mu.KotlinLogging
import net.ConnectionManager
import net.NetStatus
import tablelayout.Table
import workers.*
import java.awt.*
import java.awt.GridBagConstraints.NORTHWEST
import java.awt.GridBagConstraints.VERTICAL
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionAdapter
import java.io.File
import java.io.IOException
import java.lang.reflect.InvocationTargetException
import java.nio.file.Files
import java.nio.file.Paths
import java.security.PrivilegedActionException
import java.util.*
import java.util.regex.Pattern
import java.util.stream.Collectors
import javax.imageio.ImageIO
import javax.swing.*
import javax.swing.border.CompoundBorder
import javax.swing.border.EmptyBorder
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener
import javax.swing.filechooser.FileNameExtensionFilter
import javax.swing.text.DefaultCaret

object MainWindow : JFrame() {
    private val log = KotlinLogging.logger {}
    private val exitIcon by lazy { ImageIcon(ImageIO.read(javaClass.classLoader.getResource("exitup.png"))) }
    private val minIcon by lazy { ImageIcon(ImageIO.read(javaClass.classLoader.getResource("minimizeup.png"))) }
    private val exitIconDown by lazy { ImageIcon(ImageIO.read(javaClass.classLoader.getResource("exitdown.png"))) }
    private val minIconDown by lazy { ImageIcon(ImageIO.read(javaClass.classLoader.getResource("minimizedown.png"))) }
    private val exitIconHover by lazy { ImageIcon(ImageIO.read(javaClass.classLoader.getResource("exithover.png"))) }
    private val minIconHover by lazy { ImageIcon(ImageIO.read(javaClass.classLoader.getResource("minimizehover.png"))) }

    val ui by lazy { UI() }

    private lateinit var saveChooser: JSystemFileChooser
    private lateinit var importChooser: JSystemFileChooser
    val point by lazy { Point() }

    /**
     * Create the frame.
     */
    fun init() {
        initFilechooser()
        log.info("init UI")
        layout = BorderLayout()
        setSize(570, 355)
        background = Color(36, 36, 36)
        centerWindow()
        if(!isDisplayable) {
          isUndecorated = true
        }
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        isResizable = false
        ui.initComponents()
        add(ui, BorderLayout.CENTER)
        addMouseListener(object : MouseAdapter() {

            override fun mousePressed(e: MouseEvent) {
                point.x = e.x
                point.y = e.y
            }
        })
        addMouseMotionListener(object : MouseMotionAdapter() {
            override fun mouseDragged(e: MouseEvent) {
                val p = location
                setLocation(p.x + e.x - point.x, p.y + e.y - point.y)
            }
        })
        isVisible = true
        OnlineCheckWorker("http://google.com") {if (ConnectionManager.netStatus == NetStatus.SERVER_CONTACT) WurstBuildCheckWorker().execute()}.execute()
		OnlineCheckWorker("http://bing.com") {if (ConnectionManager.netStatus == NetStatus.SERVER_CONTACT) WurstBuildCheckWorker().execute()}.execute()
		OnlineCheckWorker("http://baidu.com") {if (ConnectionManager.netStatus == NetStatus.SERVER_CONTACT) WurstBuildCheckWorker().execute()}.execute()
    }

    class UI : JPanel() {
        private val projNamePattern = Pattern.compile("(\\w|\\s)+")
        private var contentTable: Table = Table()
        private val topBar = JPanel()
        private val title = JPanel()
        private val windowLabel = JLabel("    Wurst Setup")
        var lblWelcome: JLabel = JLabel("Welcome to the Wurst Setup")
        var lblCurrentVersion: JLabel = JLabel("Installed Compiler Build: ")
        var lblCurVerNumber: JLabel = JLabel("(not installed)")
        var lblLatestVer: JLabel = JLabel("Latest Build: ")
        var lblLatestVerNumber: JLabel = JLabel("(unknown)")
        var progressBar: JProgressBar = JProgressBar()
        var btnCreate: SetupButton = SetupButton("Create Project")
        var btnUpdate: SetupButton = SetupButton("Install WurstScript")
        var importButton: SetupButton = SetupButton("Open Project")
        var btnAdvanced: SetupButton = SetupButton("Add")
        var jTextArea = JTextArea("Ready version: " + CompileTimeInfo.version + "\n")
        var projectNameTF: JTextField = JTextField("MyWurstProject")
        var projectRootTF: JTextField = JTextField("projectRoot")
        var dependencyTF: JTextField = JTextField("wurstStdlib2")
        private val exit = JButton(exitIcon)
        private val minimize = JButton(minIcon)
        private val gamePathTF = JTextField("Select your wc3 installation folder (optional)")

        private var selectedConfig: WurstProjectConfigData? = null
        var dependencies: MutableList<String> = ArrayList(Arrays.asList("https://github.com/wurstscript/wurstStdlib2"))

        var inited = false
        fun initComponents() {
            setTitle("Wurst Setup")
            background = Color(36, 36, 36)

            setupTopBar()
            topBar.background = Color(64, 67, 69)
            title.background = Color(94, 97, 99)
            windowLabel.foreground = Color(255, 255, 255)
            contentTable.setSize(570, 350)

            contentPane.add(contentTable)

            contentTable.top()
            contentTable.row().height(26f)
            val titleTable = Table()
            titleTable.addCell(topBar).growX().height(26f)
            titleTable.addCell(title).size(100f, 26f)
            contentTable.addCell(titleTable).growX()

            contentTable.row()

            lblWelcome.horizontalAlignment = SwingConstants.CENTER
            lblWelcome.font = Font(Font.SANS_SERIF, Font.BOLD, 18)
            contentTable.addCell(lblWelcome).center().pad(2f)

            contentTable.row()

            val noteTable = Table()
            noteTable.addCell(lblCurrentVersion).center()
            noteTable.addCell(lblCurVerNumber).center()
            noteTable.addCell(lblLatestVer).center().padLeft(12f)
            noteTable.addCell(lblLatestVerNumber).center()
            contentTable.addCell(noteTable).pad(2f).growX()

            contentTable.row()

            createConfigTable()

            contentTable.row()

            jTextArea.background = Color(46, 46, 46)
            jTextArea.foreground = Color(255, 255, 255)
            jTextArea.font = Font(Font.MONOSPACED, Font.PLAIN, 12)
            if (jTextArea.caret is DefaultCaret) {
                val caret = jTextArea.caret as DefaultCaret
                caret.updatePolicy = DefaultCaret.ALWAYS_UPDATE
                caret.isSelectionVisible = true
            }
            jTextArea.isEditable = false
            jTextArea.margin = Insets(2, 2, 2, 2)
            val scrollPane = JScrollPane(jTextArea)
            contentTable.addCell(scrollPane).height(120f).growX().pad(2f)
            val line = BorderFactory.createLineBorder(Color.DARK_GRAY)
            scrollPane.border = line
            scrollPane.verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_NEVER
            scrollPane.horizontalScrollBarPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
            contentTable.row()

            contentTable.addCell(progressBar).growX().pad(2f)

            contentTable.row()

            createButtonTable()
            UiStyle.setStyle(contentTable)

            inited = true
            refreshComponents()
        }

        private fun setupTopBar() {
            try {

                exit.isOpaque = false
                exit.isContentAreaFilled = false
                exit.isFocusPainted = false
                exit.isBorderPainted = false
                exit.pressedIcon = exitIconDown
                exit.rolloverIcon = exitIconHover

                minimize.isOpaque = false
                minimize.isContentAreaFilled = false
                minimize.isFocusPainted = false
                minimize.isBorderPainted = false
                minimize.pressedIcon = minIconDown
                minimize.rolloverIcon = minIconHover
            } catch (e: IOException) {
                e.printStackTrace()
            }

            title.layout = GridLayout(1, 2)

            minimize.size = Dimension(50, 26)
            exit.size = Dimension(50, 26)

            title.add(minimize)
            title.add(exit)
            topBar.layout = GridLayout(1, 1)
            topBar.add(windowLabel, GridBagConstraints(0, 0, 0, 0, 0.0, 0.0, NORTHWEST, VERTICAL, Insets(0, 0, 0, 0), 0, 0))
            titleEvents(minimize, exit)
        }

        private fun titleEvents(minimize: JButton, exit: JButton) {
            minimize.addActionListener { e -> state = Frame.ICONIFIED }
            exit.addActionListener { e ->
                dispose()
                System.exit(0)
            }
        }

        private var projectRootFile: File = File(".")

        private fun createConfigTable() {
            val that = this
            val configTable = Table()
            configTable.row().height(24f)
            configTable.addCell(JLabel("Project name:")).left()

            val projectInputTable = Table()
            projectInputTable.row().height(24f)
            projectNameTF.document.addDocumentListener(object : DocumentListener {
                override fun changedUpdate(e: DocumentEvent) {
                    warn()
                }

                override fun removeUpdate(e: DocumentEvent) {
                    warn()
                }

                override fun insertUpdate(e: DocumentEvent) {
                    warn()
                }

                fun warn() {
                    if (projectNameTF.text.isNotEmpty() && !projNamePattern.matcher(projectNameTF.text).matches()) {
                        JOptionPane.showMessageDialog(null,
                                "Error: Please enter valid project name", "Error Massage",
                                JOptionPane.ERROR_MESSAGE)
                    } else {
                        if (projectNameTF.text.isEmpty()) {
                            btnCreate.isEnabled = false
                        } else {
                            projectRootTF.text = projectRootFile?.absolutePath + File.separator + projectNameTF.text
                            if (!disabled) {
                              btnCreate.isEnabled = true
                            }
                        }
                    }
                }
            })

            configTable.addCell(projectInputTable).growX()

            configTable.row().height(24f).padTop(2f)

            configTable.addCell(JLabel("Project root:")).left()

            val projectTF = Table()
            projectRootTF.isEditable = false
            projectTF.row().height(24f)
            projectTF.addCell(projectRootTF).growX()
            val selectProjectRoot = SetupButton("...")

			projectRootTF.text = projectRootFile.absolutePath + File.separator + projectNameTF.text
			projectInputTable.addCell(projectNameTF).growX()

            selectProjectRoot.addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(arg0: MouseEvent) {
                    if (saveChooser.showSaveDialog(that) == JFileChooser.APPROVE_OPTION) {
                        projectRootFile = saveChooser.selectedFile
                        projectRootTF.text = saveChooser.selectedFile.absolutePath + File.separator + projectNameTF.text
                    }
                }
            })
            projectTF.addCell(selectProjectRoot).pad(0f, 2f, 0f, 2f)

            configTable.addCell(projectTF).growX()

            configTable.row().height(24f).padTop(2f)

            configTable.addCell(JLabel("Game path:")).left()

            val gameTF = Table()
            gamePathTF.isEditable = false
            gameTF.addCell(gamePathTF).height(24f).growX()
            val selectGamePath = SetupButton("...")
            selectGamePath.addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(arg0: MouseEvent) {
                    if (saveChooser.showSaveDialog(that) == JFileChooser.APPROVE_OPTION) {
                        gamePathTF.text = saveChooser.selectedFile.absolutePath
                    }
                }
            })
            if (System.getProperty("os.name").startsWith("Windows")) {
                try {
                    val key = Registry.getKey(Registry.HKEY_CURRENT_USER + "\\SOFTWARE\\Blizzard Entertainment\\Warcraft III")
                    var wc3Path = key?.getValueByName("InstallPath")?.rawValue
                    if (wc3Path != null) {
                        if (!wc3Path.endsWith(File.separator)) wc3Path += File.separator
                        val gameFolder = Paths.get(wc3Path)
                        if (Files.exists(gameFolder)) {
                            gamePathTF.text = wc3Path
                        }
                    } else {
                        checkDefaultWinLocation()
                    }
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                } catch (e: InvocationTargetException) {
                    e.printStackTrace()
                } catch (e: PrivilegedActionException) {
                    e.printStackTrace()
                }

            }
            gameTF.addCell(selectGamePath).height(24f).pad(0f, 2f, 0f, 2f)

            configTable.addCell(gameTF).growX()
            configTable.row().height(24f).padTop(2f)

            configTable.addCell(JLabel("Dependencies:")).left()

            val dependencyTable = Table()
            dependencyTF.isEditable = false
            dependencyTable.addCell(dependencyTF).height(24f).growX()
            btnAdvanced.addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(arg0: MouseEvent) {
                    log.info("Adding dependency")
                    AddRepoDialog()
                }
            })

            dependencyTable.addCell(btnAdvanced).height(24f).pad(0f, 2f, 0f, 2f)

            configTable.addCell(dependencyTable).growX()

            contentTable.addCell(configTable).growX().pad(2f)
        }

        private fun checkDefaultWinLocation() {
            var gameFolder = Paths.get(System.getenv("ProgramFiles")).resolve("Warcraft III")
            if (Files.exists(gameFolder)) {
                gamePathTF.text = gameFolder.toAbsolutePath().toString()
            } else {
                gameFolder = Paths.get(System.getenv("%programfiles% (x86)")).resolve("Warcraft III")
                if (Files.exists(gameFolder)) {
                    gamePathTF.text = gameFolder.toAbsolutePath().toString()
                }
            }
        }

        private fun handleImport() {
            try {
                val buildFile = importChooser.selectedFile.toPath()
                val config = WurstProjectConfig.loadProject(buildFile)
                if (config != null) {
                    projectNameTF.text = config.projectName
                    projectRootTF.text = buildFile.parent.toString()
                    dependencyTF.text = config.dependencies.stream().map { i -> i.substring(i.lastIndexOf("/") + 1) }.collect(Collectors.joining(", "))
                    dependencies = ArrayList(config.dependencies)
                    btnCreate.text = "Update Project"
                    selectedConfig = config
                    Log.print("Use the \"Update Project\" button to update config and dependencies.\n")
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        fun refreshComponents() {
            if (!inited) return
            SwingUtilities.invokeLater {
                progressBar.isIndeterminate = false
                if (!disabled) {
                  importButton.isEnabled = true
                }
                when (ConnectionManager.netStatus) {
                    NetStatus.CLIENT_OFFLINE, NetStatus.SERVER_OFFLINE -> {
                        lblLatestVerNumber.text = "(loading..)"
                        lblLatestVerNumber.foreground = Color.DARK_GRAY
                        btnCreate.isEnabled = false
                        btnUpdate.isEnabled = false
                    }
                    NetStatus.ONLINE -> {
                        lblLatestVerNumber.text = InstallationManager.latestCompilerVersion.toString()
                        lblLatestVerNumber.foreground = Color.decode("#005719")
                        if (!disabled) {
                            btnUpdate.isEnabled = true
                        }
                    }
                    NetStatus.SERVER_CONTACT -> {
                        lblLatestVerNumber.text = "Loading.."
                        btnUpdate.isEnabled = false
                    }
                }
                when (InstallationManager.status) {
                    InstallationManager.InstallationStatus.NOT_INSTALLED -> {
                        btnUpdate.text = "Install WurstScript"
                        btnCreate.isEnabled = false
                        lblCurVerNumber.foreground = Color.DARK_GRAY
                    }
                    InstallationManager.InstallationStatus.INSTALLED_UPTODATE -> {
                        lblCurVerNumber.text = getVersionString()
                        lblCurVerNumber.foreground = Color.decode("#005719")
                        if (!disabled) {
                          btnCreate.isEnabled = true
                        }
                        btnUpdate.text = "Compiler up to date"
                        btnUpdate.isEnabled = false
                    }
                    InstallationManager.InstallationStatus.INSTALLED_UNKNOWN, InstallationManager.InstallationStatus.INSTALLED_OUTDATED -> {
                        lblCurVerNumber.text = getVersionString()
                        lblCurVerNumber.foreground = Color.decode("#702D2D")
                        if (!disabled) {
                          btnCreate.isEnabled = true
                        }
                        btnUpdate.text = "Update WurstScript"
                    }
                }
            }
        }

        private fun getVersionString() =
                if (InstallationManager.currentCompilerVersion > 0) InstallationManager.currentCompilerVersion.toString() else "(unofficial build)"

        private var disabled = false

        fun enableButtons() {
            disabled = false
        }

        fun disableButtons() {
            disabled = true
            if (SwingUtilities.isEventDispatchThread()) {
                btnCreate.isEnabled = false
                btnUpdate.isEnabled = false
                importButton.isEnabled = false
                btnAdvanced.isEnabled = false
            } else {
                SwingUtilities.invokeLater {
                    btnCreate.isEnabled = false
                    btnUpdate.isEnabled = false
                    importButton.isEnabled = false
                    btnAdvanced.isEnabled = false
                }
            }

          }

        private fun createButtonTable() {
            val buttonTable = Table()
            buttonTable.setSize(420, 90)
            btnUpdate.addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(arg0: MouseEvent) {
                    if (btnUpdate.isEnabled && !progressBar.isIndeterminate) {
                        handleWurstUpdate()
                    }
                }
            })
            buttonTable.addCell(btnUpdate)
            buttonTable.addCell().growX()
            btnCreate.addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(arg0: MouseEvent) {
                    if (btnCreate.isEnabled && !progressBar.isIndeterminate) {
                        SwingUtilities.invokeLater { progressBar.isIndeterminate = true }
                        disableButtons()
                        if (selectedConfig == null) {
                            try {
                                selectedConfig = WurstProjectConfig.loadProject(Paths.get(projectRootTF.text, CONFIG_FILE_NAME))
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                        }
                        if (selectedConfig != null) {
                            handleUpdateProject()
                        } else {
                            handleCreateProject()
                        }
                    }
                }
            })
			val that = this
			importButton.addMouseListener(object : MouseAdapter() {
				override fun mouseClicked(arg0: MouseEvent) {
					if (importButton.isEnabled && !progressBar.isIndeterminate) {
						if (importChooser.showOpenDialog(that) == JFileChooser.APPROVE_OPTION) {
							handleImport()
						}
					}
				}
			})
			buttonTable.addCell(importButton).padRight(6f)
            buttonTable.addCell(btnCreate)
            contentTable.addCell(buttonTable).growX().pad(2f)
        }

        private fun handleCreateProject() {
            SwingUtilities.invokeLater { progressBar.isIndeterminate = true }
            disableButtons()
            val gamePath = gamePathTF.text
            val projectRoot = Paths.get(projectRootTF.text)
            val gameRoot = if (gamePath.isNotEmpty()) Paths.get(gamePath) else null
            val config = WurstProjectConfigData(buildMapData = WurstProjectBuildMapData(name = "MyMapName", fileName = "MyMapFile", author = System.getProperty("user.name")))
            config.projectName = projectNameTF.text
            dependencies.forEach { elem ->
                if (!config.dependencies.contains(elem)) {
                    config.dependencies.add(elem)
                }
            }

            log.info("Creating new project. gamepath <{}>, root <{}>, config <{}>", gameRoot, projectRoot, config)
            if (SetupApp.setup.isGUILaunch) {
				ProjectCreateWorker(projectRoot, gameRoot, config).execute()
			} else {
				WurstProjectConfig.handleCreate(projectRoot, gameRoot, config)
            }
        }

        private fun handleUpdateProject() {
            val gameRoot = Paths.get(gamePathTF.text)
            val projectRoot = Paths.get(projectRootTF.text)
            if (selectedConfig != null) {
                dependencies.forEach { e -> if (selectedConfig?.dependencies?.contains(e) == false) selectedConfig?.dependencies?.add(e) }
                log.info("Update project. gamepath <{}>, root <{}>", gameRoot, projectRoot)
                if (SetupApp.setup.isGUILaunch) {
					ProjectUpdateWorker(projectRoot, gameRoot, selectedConfig!!).execute()
				} else {
					WurstProjectConfig.handleUpdate(projectRoot, gameRoot, selectedConfig!!)
                }

            }
        }

        private fun handleWurstUpdate() {
            log.info("handle wurst update")
            SwingUtilities.invokeLater { progressBar.isIndeterminate = true }
            disableButtons()
            CompilerUpdateWorker().execute()
        }

    }

    private fun initFilechooser() {
        saveChooser = JSystemFileChooser()
        saveChooser.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
        saveChooser.currentDirectory = java.io.File(".")
        saveChooser.dialogTitle = "Select project root"
        saveChooser.isAcceptAllFileFilterUsed = false

        importChooser = JSystemFileChooser()
        importChooser.currentDirectory = java.io.File(".")
        importChooser.dialogTitle = "Select wurst.build file"
        importChooser.isAcceptAllFileFilterUsed = false
        importChooser.fileFilter = FileNameExtensionFilter("wurst.build files", "build")
    }

    private fun centerWindow() {
        val screenBounds = graphicsConfiguration.bounds

        val centerX = screenBounds.x + screenBounds.width / 2
        val centerY = screenBounds.y + screenBounds.height / 2

        setLocation(centerX - width / 2, centerY - height / 2)
    }

    class SetupButton internal constructor(buttonTag: String) : JButton(buttonTag) {
        private val textColor = Color.WHITE
        private val backgroundColor = Color(18, 18, 18)
        private val overColor = Color(120, 20, 20)
        private val pressedColor = Color(240, 40, 40)

        init {
            background = backgroundColor
            foreground = textColor
            isContentAreaFilled = false
            isFocusPainted = false

            val line = BorderFactory.createLineBorder(Color(80, 80, 80))
            val empty = EmptyBorder(4, 4, 4, 4)
            val border = CompoundBorder(line, empty)
            setBorder(border)
        }

        override fun paintComponent(g: Graphics) {
            when {
                getModel().isPressed -> g.color = pressedColor
                getModel().isRollover -> g.color = overColor
                else -> g.color = background
            }
            g.fillRect(0, 0, width, height)
            super.paintComponent(g)
        }
    }

}
