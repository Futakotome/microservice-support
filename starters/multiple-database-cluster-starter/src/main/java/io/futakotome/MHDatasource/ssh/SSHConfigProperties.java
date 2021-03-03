package io.futakotome.MHDatasource.ssh;

import java.io.Serializable;

//todo 应该加属性校验
public class SSHConfigProperties implements Serializable {

    private boolean enabled = false;
    private String username;
    private int port;
    private boolean passwordMode = false;
    private String password;
    private String privateKeyPath = "classpath:META-INF/Identity";
    private String passphrase;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPrivateKeyPath() {
        return privateKeyPath;
    }

    public void setPrivateKeyPath(String privateKeyPath) {
        this.privateKeyPath = privateKeyPath;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public void setPassphrase(String passphrase) {
        this.passphrase = passphrase;
    }

    public boolean isPasswordMode() {
        return passwordMode;
    }

    public void setPasswordMode(boolean passwordMode) {
        this.passwordMode = passwordMode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "[" + "\n" +
                "\t\t\t\t" + "enabled ---- " + enabled + "\n" +
                "\t\t\t\t" + "username ---- " + username + "\n" +
                "\t\t\t\t" + " port ---- " + port + "\n" +
                "\t\t\t\t" + " passwordMode ---- " + passwordMode + "\n" +
                "\t\t\t\t" + " password ---- " + password + "\n" +
                "\t\t\t\t" + " privateKeyPath ---- " + privateKeyPath + "\n" +
                "\t\t\t\t" + " passphrase ---- " + passphrase + "\n" +
                "\t\t\t\t" + "]";
    }
}
