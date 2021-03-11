package io.futakotome.zk.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.util.Assert;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@ConfigurationProperties(prefix = ZookeeperProperties.PREFIX)
public class ZookeeperProperties {
    public static final String PREFIX = "spring.zookeeper";

    private String zkAddress = "localhost:2181";

    private boolean enabled;

    private Integer baseSleepTimeMs = 50;

    private Integer maxSleepMs = 500;

    private Integer maxRetries = 10;

    private Integer blockUntilConnectedWait = 10;

    private TimeUnit blockUntilConnectedUnit = TimeUnit.SECONDS;

    @DurationUnit(ChronoUnit.MILLIS)
    private Duration sessionTimeout = Duration.of(60 * 1000, ChronoUnit.MILLIS);

    @DurationUnit(ChronoUnit.MILLIS)
    private Duration connectionTimeout = Duration.of(15 * 1000, ChronoUnit.MILLIS);

    public String getZkAddress() {
        return zkAddress;
    }

    public void setZkAddress(String zkAddress) {
        Assert.notNull(zkAddress, "Zookeeper address must not be null");
        this.zkAddress = zkAddress;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getBaseSleepTimeMs() {
        return baseSleepTimeMs;
    }

    public void setBaseSleepTimeMs(Integer baseSleepTimeMs) {
        this.baseSleepTimeMs = baseSleepTimeMs;
    }

    public Integer getMaxSleepMs() {
        return maxSleepMs;
    }

    public void setMaxSleepMs(Integer maxSleepMs) {
        this.maxSleepMs = maxSleepMs;
    }

    public Integer getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(Integer maxRetries) {
        this.maxRetries = maxRetries;
    }

    public Integer getBlockUntilConnectedWait() {
        return blockUntilConnectedWait;
    }

    public void setBlockUntilConnectedWait(Integer blockUntilConnectedWait) {
        this.blockUntilConnectedWait = blockUntilConnectedWait;
    }

    public TimeUnit getBlockUntilConnectedUnit() {
        return blockUntilConnectedUnit;
    }

    public void setBlockUntilConnectedUnit(TimeUnit blockUntilConnectedUnit) {
        this.blockUntilConnectedUnit = blockUntilConnectedUnit;
    }

    public Duration getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(Duration sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public Duration getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Duration connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }
}
