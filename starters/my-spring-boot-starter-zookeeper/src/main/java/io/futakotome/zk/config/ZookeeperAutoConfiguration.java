package io.futakotome.zk.config;

import io.futakotome.zk.CuratorFactory;
import io.futakotome.zk.CuratorFrameworkCustomizer;
import io.futakotome.zk.ZookeeperClientTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.drivers.TracerDriver;
import org.apache.curator.ensemble.EnsembleProvider;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ZookeeperProperties.class)
@ConditionalOnZookeeperEnabled
public class ZookeeperAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ZookeeperProperties zookeeperProperties() {
        return new ZookeeperProperties();
    }

    @Bean(destroyMethod = "close")
    public CuratorFramework curatorFramework(ZookeeperProperties properties, RetryPolicy retryPolicy,
                                             ObjectProvider<CuratorFrameworkCustomizer> curatorFrameworkCustomizers,
                                             ObjectProvider<EnsembleProvider> ensembleProviders,
                                             ObjectProvider<TracerDriver> tracerDrivers) throws InterruptedException {
        return CuratorFactory.curatorFramework(properties,
                retryPolicy,
                curatorFrameworkCustomizers::orderedStream,
                ensembleProviders::getIfAvailable,
                tracerDrivers::getIfAvailable);
    }

    @Bean
    @ConditionalOnMissingBean
    public RetryPolicy exponentialBackoffRetry(ZookeeperProperties properties) {
        return CuratorFactory.retryPolicy(properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public ZookeeperClientTemplate zookeeperClientTemplate(CuratorFramework curatorFramework) {
        return new ZookeeperClientTemplate(curatorFramework);
    }
}
