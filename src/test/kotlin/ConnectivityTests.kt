import net.ConnectionManager
import net.NetStatus
import org.testng.annotations.Test

@Test fun testConnectionManager() {
    assert(ConnectionManager.checkConnectivity() == NetStatus.SERVER_CONTACT)

    assert(ConnectionManager.checkWurstBuild() == NetStatus.ONLINE)

	assert(ConnectionManager.getLatestCompilerBuild() > 1000)

	assert(ConnectionManager.getLatestSetupBuild() > 50)
}
