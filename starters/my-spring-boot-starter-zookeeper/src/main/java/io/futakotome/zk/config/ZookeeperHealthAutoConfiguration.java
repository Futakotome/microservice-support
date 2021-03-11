package io.futakotome.zk.config;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnClass(Endpoint.class)
@ConditionalOnZookeeperEnabled
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(ZookeeperAutoConfiguration.class)
public class ZookeeperHealthAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(ZookeeperHealthIndicator.class)
    @ConditionalOnBean(CuratorFramework.class)
    @ConditionalOnEnabledHealthIndicator("zookeeper")
    public ZookeeperHealthIndicator zookeeperHealthIndicator(CuratorFramework curatorFramework) {
        return new ZookeeperHealthIndicator(curatorFramework);
    }
}
