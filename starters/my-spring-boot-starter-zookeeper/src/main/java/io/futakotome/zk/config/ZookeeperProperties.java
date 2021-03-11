package io.futakotome.zk.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.util.Assert;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@Setter
@Getter
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

    public void setZkAddress(String zkAddress) {
        Assert.notNull(zkAddress, "Zookeeper address should not be null!");
        this.zkAddress = zkAddress;
    }
}
