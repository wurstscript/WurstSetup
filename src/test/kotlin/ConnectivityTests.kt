import net.ConnectionManager
import net.NetStatus
import org.testng.annotations.Test

class ConnectivityTests {

		@Test fun testConnectionManager() {
			assert(ConnectionManager.checkConnectivity("http://google.com") == NetStatus.SERVER_CONTACT) { "google.com unreachable" }

			assert(ConnectionManager.checkWurstBuild() == NetStatus.ONLINE) { "wurst build unreachable, status=${ConnectionManager.netStatus}" }

			assert(ConnectionManager.getLatestCompilerBuild() > 20250000) { "unexpected build date: ${ConnectionManager.getLatestCompilerBuild()}" }

			assert(ConnectionManager.getLatestSetupBuild() == 0) { "expected 0 but got ${ConnectionManager.getLatestSetupBuild()}" }
		}

}

