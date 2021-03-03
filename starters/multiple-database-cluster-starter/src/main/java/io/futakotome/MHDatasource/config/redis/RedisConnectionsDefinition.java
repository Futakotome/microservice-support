package io.futakotome.MHDatasource.config.redis;

import io.futakotome.MHDatasource.config.DataSourceDefinition;

import java.time.Duration;

public class RedisConnectionsDefinition implements DataSourceDefinition {

    private String key;

    private int database = 0;

    private String host;

    private int port = 6379;

    private String password;

//    private boolean ssl = false;

    private String clientName;

    private Duration timeout;

    private Lettuce lettuce;

    private Jedis jedis;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Lettuce getLettuce() {
        return lettuce;
    }

    public Duration getTimeout() {
        return timeout;
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }

    public Jedis getJedis() {
        return jedis;
    }

    public void setLettuce(Lettuce lettuce) {
        this.lettuce = lettuce;
    }

    public void setJedis(Jedis jedis) {
        this.jedis = jedis;
    }

    public static class Lettuce {

        private Duration shutdownTimeout = Duration.ofMillis(100);

        private Pool pool;

        public Duration getShutdownTimeout() {
            return this.shutdownTimeout;
        }

        public void setShutdownTimeout(Duration shutdownTimeout) {
            this.shutdownTimeout = shutdownTimeout;
        }

        public Pool getPool() {
            return this.pool;
        }

        public void setPool(Pool pool) {
            this.pool = pool;
        }

        @Override
        public String toString() {
            return "[" + "\n" +
                    "\t\t\t\t\t\t\t" + " shutdownTimeout ---- " + shutdownTimeout + "\n" +
                    "\t\t\t\t\t\t\t" + " pool ----- " + pool + "\n"
                    + "\t\t\t\t\t" +
                    "]";
        }
    }

    public static class Jedis {

        private Pool pool;

        public Pool getPool() {
            return this.pool;
        }

        public void setPool(Pool pool) {
            this.pool = pool;
        }

        @Override
        public String toString() {
            return "[" + "\n" +
                    "\t\t\t\t\t\t\t" + " pool ----- " + pool + "\n"
                    + "\t\t\t\t\t" +
                    "]";
        }
    }

    public static class Pool {

        private int maxIdle = 8;

        private int minIdle = 0;

        private int maxActive = 8;

        private Duration maxWait = Duration.ofMillis(-1);

        private Duration timeBetweenEvictionRuns;

        public int getMaxIdle() {
            return this.maxIdle;
        }

        public void setMaxIdle(int maxIdle) {
            this.maxIdle = maxIdle;
        }

        public int getMinIdle() {
            return this.minIdle;
        }

        public void setMinIdle(int minIdle) {
            this.minIdle = minIdle;
        }

        public int getMaxActive() {
            return this.maxActive;
        }

        public void setMaxActive(int maxActive) {
            this.maxActive = maxActive;
        }

        public Duration getMaxWait() {
            return this.maxWait;
        }

        public void setMaxWait(Duration maxWait) {
            this.maxWait = maxWait;
        }

        public Duration getTimeBetweenEvictionRuns() {
            return this.timeBetweenEvictionRuns;
        }

        public void setTimeBetweenEvictionRuns(Duration timeBetweenEvictionRuns) {
            this.timeBetweenEvictionRuns = timeBetweenEvictionRuns;
        }

        @Override
        public String toString() {
            return "[" + "\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t" + " maxIdle ---- " + maxIdle + "\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t" + " minIdle ---- " + minIdle + "\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t" + " maxActive ---- " + maxActive + "\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t" + " maxWait ---- " + maxWait + "\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t" + "timeBetweenEvictionRuns ---- " + timeBetweenEvictionRuns + "\n" +
                    "\t\t\t\t\t\t\t\t\t\t" + "]";
        }
    }

    @Override
    public String toString() {
        return "\t" + "[" + "\n" +
                "\t\t" + "key ----- " + key + "\n" +
                "\t\t" + "database ----- " + database + "\n" +
                "\t\t" + "host ---- " + host + "\n" +
                "\t\t" + "port ---- " + port + "\n" +
                "\t\t" + "clientName ---- " + clientName + "\n" +
                "\t\t" + "timeout ---- " + timeout + "\n" +
                "\t\t" + "lettuce ---- " + (lettuce == null ? "null" : lettuce.toString()) + "\n" +
                "\t\t" + "jedis ---- " + (jedis == null ? "null" : jedis.toString()) + "\n" +
                "\t" + "]";
    }
}
