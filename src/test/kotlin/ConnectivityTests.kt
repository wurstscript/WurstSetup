import net.ConnectionManager
import net.NetStatus
import org.testng.annotations.Test

@Test fun testConnectionManager() {
    assert(ConnectionManager.checkConnectivity() == NetStatus.SERVER_CONTACT)

    assert(ConnectionManager.checkWurstBuild() == NetStatus.ONLINE)
}
