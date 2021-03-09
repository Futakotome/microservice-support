package io.futakotome.lock;


import io.futakotome.lock.configuration.DistributeLockExecutorConfigurationExtension;
import io.futakotome.lock.impl.ZookeeperDistributeLockExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class ZookeeperDistributeLockExecutorConfiguration implements DistributeLockExecutorConfigurationExtension {

    @Bean
    public ZookeeperDistributeLockExecutor zookeeperDistributeLockExecutor() {
        return new ZookeeperDistributeLockExecutor();
    }
}
