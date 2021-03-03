package io.futakotome.MHDatasource.ssh;

import io.futakotome.MHDatasource.util.JdbcUrlExtractor;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

@Slf4j
public class SSHTunneledHikariDatasource extends HikariDataSource {

    private static final String PORT_FORWARDING_INFO = "host:{},port:{} will be forwarding to {}:{}";

    private SSHConfigProperties sshConfigProperties;

    public SSHTunneledHikariDatasource() {
        super();
        sshConfigProperties = null;
    }

    public void setSshConfigProperties(SSHConfigProperties sshConfigProperties) {
        this.sshConfigProperties = sshConfigProperties;
    }

    public void tunneled() {
        if (sshConfigProperties.isPasswordMode()) {
            tunneledByPasswordMode(sshConfigProperties);
        } else {
            tunneledByKeyPair(sshConfigProperties);
        }
    }

    private void tunneledByPasswordMode(SSHConfigProperties sshConfigProperties) {
        JSch jSch = new JSch();
        String remoteSSHUsername = sshConfigProperties.getUsername();
        String password = sshConfigProperties.getPassword();
        int remoteSSHPort = sshConfigProperties.getPort();
        JdbcUrlExtractor.JdbcUrlInformation information = JdbcUrlExtractor.extractFromJdbcUrl(getJdbcUrl());
        try {
            Session session = jSch.getSession(remoteSSHUsername, information.getHost(), remoteSSHPort);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            int assignedPort = session.setPortForwardingL(0, information.getHost(), information.getPort());
            log.info("Established SSH tunnel by password.");
            log.info(PORT_FORWARDING_INFO, information.getHost(), information.getPort(), "localhost", assignedPort);
            setJdbcUrl(tunneledJdbcUrl(assignedPort, information.getDatabase()));
        } catch (JSchException e) {
            log.error("ssh establish filed , the reason: " + e.getMessage());
        }
    }

    private void tunneledByKeyPair(SSHConfigProperties sshConfigProperties) {
        JSch jSch = new JSch();
        String remoteSSHUsername = sshConfigProperties.getUsername();
        String remoteSSHPassphrase = sshConfigProperties.getPassphrase();
        int remoteSSHPort = sshConfigProperties.getPort();
        JdbcUrlExtractor.JdbcUrlInformation information = JdbcUrlExtractor.extractFromJdbcUrl(getJdbcUrl());
        try {
            String privateKeyPath = ResourceUtils.getFile(sshConfigProperties.getPrivateKeyPath()).getPath();
            jSch.addIdentity(privateKeyPath, remoteSSHPassphrase);
            Session session = jSch.getSession(remoteSSHUsername, information.getHost(), remoteSSHPort);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            int assignedPort = session.setPortForwardingL(0, information.getHost(), information.getPort());
            log.info("Established SSH tunnel by key pair.");
            log.info(PORT_FORWARDING_INFO, information.getHost(), information.getPort(), "localhost", assignedPort);
            setJdbcUrl(tunneledJdbcUrl(assignedPort, information.getDatabase()));
        } catch (FileNotFoundException e) {
            log.error("private key not found , reason " + e.getMessage());
        } catch (JSchException e) {
            log.error("ssh establish filed , reason: " + e.getMessage());
        }
    }

    private String tunneledJdbcUrl(int remotePort, String database) {
        String jdbcTemplate = "jdbc:mysql://%s:%d/%s";
        return String.format(jdbcTemplate, "localhost", remotePort, database);
    }

}
