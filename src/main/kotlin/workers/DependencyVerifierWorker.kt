package workers

import file.DependencyManager
import global.Log
import org.eclipse.jgit.api.Git
import ui.MainWindow
import ui.UiManager
import java.util.stream.Collectors
import javax.swing.DesktopManager
import javax.swing.SwingUtilities
import javax.swing.SwingWorker

class DependencyVerifierWorker(val dependencyUrl: String) : SwingWorker<Boolean, Void>() {

    @Throws(Exception::class)
    override fun doInBackground(): Boolean? {
        Log.print("Checking git repo..")
        try {
            val resolved = DependencyManager.resolveName(dependencyUrl)
            val result = Git.lsRemoteRepository()
                    .setRemote(resolved.first)
                    .call()
            if (!result.isEmpty()) {
                Log.print("valid!\n")
                MainWindow.ui.dependencies.add(dependencyUrl)
                SwingUtilities.invokeLater {
                    MainWindow.ui.dependencyTF.text = MainWindow.ui.dependencies.stream().map { i -> i.substring(i.lastIndexOf("/") + 1) }.collect(Collectors.joining(", "))
                }
                UiManager.refreshComponents()
            } else {
                Log.print("Error: Entered invalid git repo\n")
            }
        } catch (e: Exception) {
            Log.print("Error: Entered invalid git repo\n")
            e.printStackTrace()
        }
        return null
    }
}
