package io.futakotome.MHDatasource.config.mysql;

import io.futakotome.MHDatasource.config.DataSourceDefinition;
import io.futakotome.MHDatasource.ssh.SSHConfigProperties;

public class MysqlDatasourceDefinition implements DataSourceDefinition {

    private String key;

    private String url = "jdbc:mysql://localhost:3306/sys";

    private String username;

    private String password;

    private String driverClassName = "com.mysql.cj.jdbc.Driver";

    private String entityPackage;

    private SSHConfigProperties ssh = new SSHConfigProperties();

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public SSHConfigProperties getSsh() {
        return ssh;
    }

    public void setSsh(SSHConfigProperties ssh) {
        this.ssh = ssh;
    }

    public String getEntityPackage() {
        return entityPackage;
    }

    public void setEntityPackage(String entityPackage) {
        this.entityPackage = entityPackage;
    }

    @Override
    public String toString() {
        return "\t" + "[" + "\n" +
                "\t\t" + "key ----- " + key + "\n" +
                "\t\t" + "url ----- " + url + "\n" +
                "\t\t" + "username ---- " + username + "\n" +
                "\t\t" + "password ---- " + password + "\n" +
                "\t\t" + "driverClassName ---- " + driverClassName + "\n" +
                "\t\t" + "entityPackage ---- " + entityPackage + "\n" +
                "\t\t" + "ssh ---- " + ssh + "\n" +
                "\t" + "]";
    }
}
