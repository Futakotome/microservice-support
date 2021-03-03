import io.futakotome.MHDatasource.ssh.SSHTunneledHikariDatasource;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;

@RunWith(JUnit4.class)
public class SSHTest {
    @Test
    public void test() throws JSchException, IOException {
        JSch jSch = new JSch();
        File privateKeyFile = ResourceUtils.getFile("classpath:META-INF/Identity");
        jSch.addIdentity(privateKeyFile.getPath(), "0220");
        String username = "loguser";
        String host = "115.159.158.192";
        int rport = 3306;
        Session session = jSch.getSession(username, host, 1022);
        session.setConfig("StrictHostKeyChecking", "no");
        session.setDaemonThread(true);
        session.connect();
        int assign_port = session.setPortForwardingL(0, host, rport);
        System.out.println("localhost:" + assign_port + "->" + host + ":" + rport);

        session.disconnect();
    }

    @Test
    public void testTunnelDatasouce() {
        SSHTunneledHikariDatasource sshTunneledHikariDatasource = new SSHTunneledHikariDatasource();
        System.out.println(sshTunneledHikariDatasource);
    }
}
