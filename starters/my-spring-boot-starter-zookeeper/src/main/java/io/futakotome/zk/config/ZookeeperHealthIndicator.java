package io.futakotome.zk.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

public class ZookeeperHealthIndicator extends AbstractHealthIndicator {
    private final CuratorFramework curatorFramework;

    public ZookeeperHealthIndicator(CuratorFramework curatorFramework) {
        this.curatorFramework = curatorFramework;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        try {
            CuratorFrameworkState state = this.curatorFramework.getState();
            if (state != CuratorFrameworkState.STARTED) {
                builder.down().withDetail("error", "Zookeeper client not started.");
            } else if (this.curatorFramework.checkExists().forPath("/") == null) {
                builder.down().withDetail("error", "Root for namespace does not exist.");
            } else {
                builder.up();
            }
            builder.withDetail("connectionString", this.curatorFramework.getZookeeperClient().getCurrentConnectionString())
                    .withDetail("state", state);
        } catch (Exception e) {
            builder.down(e);
        }
    }
}
