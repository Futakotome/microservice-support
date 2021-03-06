package io.futakotome.globalId.config;

import io.futakotome.globalId.ZookeeperGlobalIdGenerator;
import io.futakotome.zk.ZookeeperClientTemplate;
import io.futakotome.zk.config.EnableZookeeperClient;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableZookeeperClient
public class ZookeeperGlobalIdGeneratorConfiguration {
    @Bean
    public ZookeeperGlobalIdGenerator zookeeperGlobalIdGenerator(ObjectFactory<ZookeeperClientTemplate> template) {
        return new ZookeeperGlobalIdGenerator(template::getObject);
    }
}
