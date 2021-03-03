package io.futakotome.MHDatasource.util;

import lombok.experimental.UtilityClass;
import org.springframework.util.Assert;

@UtilityClass
public final class JdbcUrlExtractor {

    public JdbcUrlInformation extractFromJdbcUrl(String jdbcUrl) {
        Assert.hasText(jdbcUrl, "Invalid jdbc url");
        int pos, pos1, pos2;
        String driverName, host = null, port = null, database = null, params = null;
        String connectionUrl;
        if ((pos1 = jdbcUrl.indexOf(":", 5)) == -1) {
            throw new IllegalArgumentException("can not found the driver ");
        }
        driverName = jdbcUrl.substring(5, pos1);
        if ((pos2 = jdbcUrl.indexOf(";", pos1)) == -1) {
            connectionUrl = jdbcUrl.substring(pos1 + 1);
        } else {
            connectionUrl = jdbcUrl.substring(pos1 + 1, pos2);
            params = jdbcUrl.substring(pos2 + 1);
        }
        if (connectionUrl.startsWith("//")) {
            if ((pos = connectionUrl.indexOf("/", 2)) != -1) {
                host = connectionUrl.substring(2, pos);
                database = connectionUrl.substring(pos + 1);
                if ((pos = host.indexOf(":")) != -1) {
                    port = host.substring(pos + 1);
                    host = host.substring(0, pos);
                }
            }
        } else {
            database = connectionUrl;
        }
        return new JdbcUrlInformation(host, port, driverName, database, params);
    }

    public class JdbcUrlInformation {
        private String host;
        private String port;
        private String driverPlatform;
        private String database;
        private String params;

        public JdbcUrlInformation(String host, String port, String driverPlatform, String database, String params) {
            this.host = host;
            this.port = port;
            this.driverPlatform = driverPlatform;
            this.database = database;
            this.params = params;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return Integer.parseInt(port);
        }

        public void setPort(String port) {
            this.port = port;
        }

        public String getDriverPlatform() {
            return driverPlatform;
        }

        public void setDriverPlatform(String driverPlatform) {
            this.driverPlatform = driverPlatform;
        }

        public String getDatabase() {
            return database;
        }

        public void setDatabase(String database) {
            this.database = database;
        }

        public String getParams() {
            return params;
        }

        public void setParams(String params) {
            this.params = params;
        }
    }
}
