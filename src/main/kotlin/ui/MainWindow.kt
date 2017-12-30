package ui

import file.CompileTimeInfo
import file.WurstProjectConfig
import file.WurstProjectConfigData
import global.InstallationManager
import global.Log
import global.WinRegistry
import mu.KotlinLogging
import net.ConnectionManager
import net.NetStatus
import tablelayout.Table
import workers.CompilerUpdateWorker
import workers.DependencyVerifierWorker
import workers.ProjectCreateWorker
import workers.ProjectUpdateWorker
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
    var ui: UI? = null

    private var saveChooser: JSystemFileChooser? = null
    private var importChooser: JSystemFileChooser? = null
    val point: Point = Point()
    /**
     * Create the frame.
     */
    fun init() {
        log.info("init UI")
        layout = BorderLayout()
        setSize(570, 355)
        background = Color(36, 36, 36)
        centerWindow()
        isUndecorated = true
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        isResizable = false
        initFilechooser()
        ui = UI()
        add(ui, BorderLayout.CENTER)
        addMouseListener(object : MouseAdapter() {
            override fun mousePressed(e: MouseEvent?) {
                point.x = e!!.x
                point.y = e.y
            }
        })
        addMouseMotionListener(object : MouseMotionAdapter() {
            override fun mouseDragged(e: MouseEvent?) {
                val p = location
                setLocation(p.x + e!!.x - point.x, p.y + e.y - point.y)
            }
        })
        isVisible = true
    }


    class UI : JPanel() {
        private val projNamePattern = Pattern.compile("(\\w|\\s)+")
        private var contentTable: Table? = null
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
        var importButton: SetupButton = SetupButton("Import")
        var jTextArea = JTextArea("Ready version: " + CompileTimeInfo.version + "\n")
        var projectNameTF: JTextField = JTextField("MyWurstProject")
        var projectRootTF: JTextField = JTextField(saveChooser!!.currentDirectory.absolutePath + File.separator + projectNameTF.text)
        var dependencyTF: JTextField? = null
        private var exit: JButton? = null
        private var minimize: JButton? = null
        private var gamePathTF: JTextField? = null

        private var selectedConfig: WurstProjectConfigData? = null
        var dependencies: MutableList<String> = ArrayList(Arrays.asList("https://github.com/wurstscript/wurstStdlib2"))

        init {
            initComponents()
        }

        private fun initComponents() {
            setTitle("Wurst Setup")
            background = Color(36, 36, 36)

            setupTopBar()
            topBar.background = Color(64, 67, 69)
            title.background = Color(94, 97, 99)
            windowLabel.foreground = Color(255, 255, 255)
            contentTable = Table()
            contentTable!!.setSize(570, 350)

            contentPane.add(contentTable)

            contentTable!!.top()
            contentTable!!.row().height(26f)
            val titleTable = Table()
            titleTable.addCell(topBar).growX().height(26f)
            titleTable.addCell(title).size(100f, 26f)
            contentTable!!.addCell(titleTable).growX()

            contentTable!!.row()

            lblWelcome.horizontalAlignment = SwingConstants.CENTER
            lblWelcome.font = Font(Font.SANS_SERIF, Font.BOLD, 18)
            contentTable!!.addCell(lblWelcome).center().pad(2f)

            contentTable!!.row()

            val noteTable = Table()
            noteTable.addCell(lblCurrentVersion).center()
            noteTable.addCell(lblCurVerNumber).center()
            noteTable.addCell(lblLatestVer).center().padLeft(12f)
            noteTable.addCell(lblLatestVerNumber).center()
            contentTable!!.addCell(noteTable).pad(2f).growX()

            contentTable!!.row()

            createConfigTable()

            contentTable!!.row()

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
            contentTable!!.addCell(scrollPane).height(120f).growX().pad(2f)
            val line = BorderFactory.createLineBorder(Color.DARK_GRAY)
            scrollPane.border = line
            scrollPane.verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_NEVER
            scrollPane.horizontalScrollBarPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
            contentTable!!.row()

            contentTable!!.addCell(progressBar).growX().pad(2f)

            contentTable!!.row()

            createButtonTable()
            UiStyle.setStyle(contentTable!!)


            refreshComponents(false)
        }

        private fun setupTopBar() {
            try {
                val exitimg = ImageIO.read(MainWindow::class.java.getResourceAsStream("/exitup.png"))
                val minimg = ImageIO.read(MainWindow::class.java.getResourceAsStream("/minimizeup.png"))
                val exitimgdown = ImageIO.read(MainWindow::class.java.getResourceAsStream("/exitdown.png"))
                val minimgdown = ImageIO.read(MainWindow::class.java.getResourceAsStream("/minimizedown.png"))
                val exithover = ImageIO.read(MainWindow::class.java.getResourceAsStream("/exithover.png"))
                val minimghover = ImageIO.read(MainWindow::class.java.getResourceAsStream("/minimizehover.png"))

                val exitIcon = ImageIcon(exitimg)
                val minIcon = ImageIcon(minimg)
                val exitIconDown = ImageIcon(exitimgdown)
                val minIconDown = ImageIcon(minimgdown)
                val exitIconHover = ImageIcon(exithover)
                val minIconHover = ImageIcon(minimghover)
                exit = JButton(exitIcon)
                minimize = JButton(minIcon)

                exit!!.isOpaque = false
                exit!!.isContentAreaFilled = false
                exit!!.isFocusPainted = false
                exit!!.isBorderPainted = false
                exit!!.pressedIcon = exitIconDown
                exit!!.rolloverIcon = exitIconHover

                minimize!!.isOpaque = false
                minimize!!.isContentAreaFilled = false
                minimize!!.isFocusPainted = false
                minimize!!.isBorderPainted = false
                minimize!!.pressedIcon = minIconDown
                minimize!!.rolloverIcon = minIconHover
            } catch (e: IOException) {
                e.printStackTrace()
            }

            title.layout = GridLayout(1, 2)

            minimize!!.size = Dimension(50, 26)
            exit!!.size = Dimension(50, 26)

            title.add(minimize)
            title.add(exit)
            topBar.layout = GridLayout(1, 1)
            topBar.add(windowLabel, GridBagConstraints(0, 0, 0, 0, 0.0, 0.0, NORTHWEST, VERTICAL, Insets(0, 0, 0, 0), 0, 0))
            titleEvents(minimize!!, exit!!)
        }

        private fun titleEvents(minimize: JButton, exit: JButton) {
            minimize.addActionListener { e -> state = Frame.ICONIFIED }
            exit.addActionListener { e ->
                dispose()
                System.exit(0)
            }
        }

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
                            projectRootTF.text = saveChooser!!.currentDirectory.absolutePath + File.separator + projectNameTF.text
                            btnCreate.isEnabled = true
                        }
                    }
                }
            })
            projectInputTable.addCell(projectNameTF).growX()
            importButton.addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(arg0: MouseEvent?) {
                    if (importButton.isEnabled && !progressBar.isIndeterminate) {
                        if (importChooser!!.showOpenDialog(that) == JFileChooser.APPROVE_OPTION) {
                            handleImport()
                        }
                    }
                }
            })
            projectInputTable.addCell(importButton)
            configTable.addCell(projectInputTable).growX()

            configTable.row().height(24f).padTop(2f)

            configTable.addCell(JLabel("Project root:")).left()

            val projectTF = Table()
            projectRootTF.isEditable = false
            projectTF.row().height(24f)
            projectTF.addCell(projectRootTF).growX()
            val selectProjectRoot = SetupButton("...")

            selectProjectRoot.addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(arg0: MouseEvent?) {
                    if (saveChooser!!.showSaveDialog(that) == JFileChooser.APPROVE_OPTION) {
                        projectRootTF.text = saveChooser!!.selectedFile.absolutePath + File.separator + projectNameTF.text
                    }
                }
            })
            projectTF.addCell(selectProjectRoot).pad(0f, 2f, 0f, 2f)

            configTable.addCell(projectTF).growX()

            configTable.row().height(24f).padTop(2f)

            configTable.addCell(JLabel("Game path:")).left()

            val gameTF = Table()
            gamePathTF = JTextField("Select your wc3 installation folder (optional)")
            gamePathTF!!.isEditable = false
            gameTF.addCell(gamePathTF).height(24f).growX()
            val selectGamePath = SetupButton("...")
            selectGamePath.addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(arg0: MouseEvent?) {
                    if (saveChooser!!.showSaveDialog(that) == JFileChooser.APPROVE_OPTION) {
                        gamePathTF!!.text = saveChooser!!.selectedFile.absolutePath
                    }
                }
            })
            if (System.getProperty("os.name").startsWith("Windows")) {
                try {
                    var wc3Path = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\Blizzard Entertainment\\Warcraft III", "InstallPath")
                    if (wc3Path != null) {
                        if (!wc3Path.endsWith(File.separator)) wc3Path += File.separator
                        val gameFolder = File(wc3Path)
                        if (gameFolder.exists()) {
                            gamePathTF!!.text = wc3Path
                        }
                    }
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                } catch (e: InvocationTargetException) {
                    e.printStackTrace()
                }

            }
            gameTF.addCell(selectGamePath).height(24f).pad(0f, 2f, 0f, 2f)

            configTable.addCell(gameTF).growX()
            configTable.row().height(24f).padTop(2f)

            configTable.addCell(JLabel("Dependencies:")).left()

            val dependencyTable = Table()
            dependencyTF = JTextField("wurstStdlib2")
            dependencyTF!!.isEditable = false
            dependencyTable.addCell(dependencyTF).height(24f).growX()
            val manageDependencies = SetupButton("Advanced")
            manageDependencies.addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(arg0: MouseEvent?) {
                    log.info("Adding dependency")
                    val url = JOptionPane.showInputDialog("Enter git remote address (https://github.com/user/project)")
                    if (url != null && url.isNotEmpty()) {
                        log.info("Adding <{}>", url)
                        if (dependencies.contains(url)) {
                            Log.print("This git repo is already added")
                            return
                        }
                        disableButtons()
                        DependencyVerifierWorker(url).execute()
                    }
                }
            })

            dependencyTable.addCell(manageDependencies).height(24f).pad(0f, 2f, 0f, 2f)

            configTable.addCell(dependencyTable).growX()

            contentTable!!.addCell(configTable).growX().pad(2f)
        }

        private fun handleImport() {
            try {
                val buildFile = importChooser!!.selectedFile.toPath()
                val config = WurstProjectConfig.loadProject(buildFile)
                if (config != null) {
                    projectNameTF.text = config.projectName
                    projectRootTF.text = buildFile.parent.toString()
                    dependencyTF!!.text = config.dependencies.stream().map { i -> i.substring(i.lastIndexOf("/") + 1) }.collect(Collectors.joining(", "))
                    dependencies = ArrayList(config.dependencies)
                    btnCreate.text = "Update Project"
                    selectedConfig = config
                    Log.print("Use the \"Update Project\" button to update config and dependencies.\n")
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        fun refreshComponents(verify: Boolean) {
            SwingUtilities.invokeLater({
                if(verify) {
                    ConnectionManager.checkConnectivity()
                    InstallationManager.verifyInstallation()
                }
                progressBar.isIndeterminate = false
                importButton.isEnabled = true
                when (ConnectionManager.netStatus) {
                    NetStatus.CLIENT_OFFLINE, NetStatus.SERVER_OFFLINE -> {
                        lblLatestVerNumber.text = "(offline)"
                        lblLatestVerNumber.foreground = Color.DARK_GRAY
                        btnCreate.isEnabled = false
                        btnUpdate.isEnabled = false
                    }
                    NetStatus.ONLINE -> {
                        lblLatestVerNumber.text = InstallationManager.latestCompilerVersion.toString()
                        lblLatestVerNumber.foreground = Color.decode("#005719")
                        btnUpdate.isEnabled = true
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
                        btnCreate.isEnabled = true
                        btnUpdate.text = "Compiler up to date"
                        btnUpdate.isEnabled = false
                    }
                    InstallationManager.InstallationStatus.INSTALLED_UNKNOWN, InstallationManager.InstallationStatus.INSTALLED_OUTDATED -> {
                        lblCurVerNumber.text = getVersionString()
                        lblCurVerNumber.foreground = Color.decode("#702D2D")
                        btnCreate.isEnabled = true
                        btnUpdate.text = "Update WurstScript"
                    }
                }
            })
        }

        private fun getVersionString() =
                if (InstallationManager.currentCompilerVersion > 0) InstallationManager.currentCompilerVersion.toString() else "(unofficial build)"

        private fun disableButtons() {
            btnCreate.isEnabled = false
            btnUpdate.isEnabled = false
            importButton.isEnabled = false
        }

        private fun createButtonTable() {
            val buttonTable = Table()
            buttonTable.setSize(420, 90)
            btnUpdate.addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(arg0: MouseEvent?) {
                    if (btnUpdate.isEnabled && !progressBar.isIndeterminate) {
                        handleWurstUpdate()
                    }
                }
            })
            buttonTable.addCell(btnUpdate)
            buttonTable.addCell().growX()
            btnCreate.addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(arg0: MouseEvent?) {
                    if (btnCreate.isEnabled && !progressBar.isIndeterminate) {
                        progressBar.isIndeterminate = true
                        disableButtons()
                        if (selectedConfig == null) {
                            try {
                                selectedConfig = WurstProjectConfig.loadProject(Paths.get(projectRootTF.text, "wurst.build"))
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
            buttonTable.addCell(btnCreate)
            contentTable!!.addCell(buttonTable).growX().pad(2f)
        }

        private fun handleUpdateProject() {
            val gameRoot = Paths.get(gamePathTF!!.text)
            val projectRoot = Paths.get(projectRootTF.text)
            if (Files.exists(gameRoot) && selectedConfig != null) {
                selectedConfig?.dependencies?.clear()
                selectedConfig?.dependencies?.addAll(dependencies)
                log.info("Update project. gamepath <{}>, root <{}>", gameRoot, projectRoot)
                ProjectUpdateWorker(projectRoot, gameRoot, selectedConfig!!).execute()
            }
        }

        private fun handleCreateProject() {
            progressBar.isIndeterminate = true
            disableButtons()
            val gamePath = gamePathTF!!.text
            val projectRoot = Paths.get(projectRootTF.text)
            val gameRoot = if (gamePath.isNotEmpty()) Paths.get(gamePath) else null
            val config = WurstProjectConfigData()
            config.projectName = projectNameTF.text
            dependencies.forEach({ elem ->
                if (!config.dependencies.contains(elem)) {
                    config.dependencies.add(elem)
                }
            })

            log.info("Creating new project. gamepath <{}>, root <{}>, config <{}>", gameRoot, projectRoot, config)
            ProjectCreateWorker(projectRoot, gameRoot, config).execute()
        }

        private fun handleWurstUpdate() {
            log.info("handle wurst update")
            progressBar.isIndeterminate = true
            disableButtons()
            CompilerUpdateWorker().execute()
        }

    }

    fun initFilechooser() {
        saveChooser = JSystemFileChooser()
        saveChooser!!.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
        saveChooser!!.currentDirectory = java.io.File(".")
        saveChooser!!.dialogTitle = "Select project root"
        saveChooser!!.isAcceptAllFileFilterUsed = false

        importChooser = JSystemFileChooser()
        importChooser!!.currentDirectory = java.io.File(".")
        importChooser!!.dialogTitle = "Select wurst.build file"
        importChooser!!.isAcceptAllFileFilterUsed = false
        importChooser!!.fileFilter = FileNameExtensionFilter("wurst.build files", "build")
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
