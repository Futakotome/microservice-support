package io.futakotome.lock;

import io.futakotome.lock.configuration.DistributeLockConfigurationExtension;
import io.futakotome.lock.impl.ZookeeperDistributeLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class ZookeeperDistributeLockConfiguration implements DistributeLockConfigurationExtension {

    @Bean
    public ZookeeperDistributeLock zookeeperDistributeLock() {
        return new ZookeeperDistributeLock();
    }

}
