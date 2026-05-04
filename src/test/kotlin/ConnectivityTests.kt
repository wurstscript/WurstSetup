import net.ConnectionManager
import net.NetStatus
import org.testng.Assert
import org.testng.annotations.Test

class ConnectivityTests {

		@Test fun testConnectionManager() {
			Assert.assertEquals(ConnectionManager.checkConnectivity("http://google.com"), NetStatus.SERVER_CONTACT, "google.com unreachable: ${ConnectionManager.lastError}")

			Assert.assertEquals(ConnectionManager.checkWurstBuild(), NetStatus.ONLINE, "wurst build unreachable, status=${ConnectionManager.netStatus}, error=${ConnectionManager.lastError}")

			val latestCompilerBuild = ConnectionManager.getLatestCompilerBuild()
            if (latestCompilerBuild > 0) {
			    Assert.assertTrue(latestCompilerBuild > 20250000, "unexpected build date: $latestCompilerBuild")
            }

			Assert.assertEquals(ConnectionManager.getLatestSetupBuild(), 0)
		}

}

