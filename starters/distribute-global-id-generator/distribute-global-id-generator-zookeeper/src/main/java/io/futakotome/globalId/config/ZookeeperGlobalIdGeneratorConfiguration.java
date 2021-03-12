package io.futakotome.globalId.config;

import io.futakotome.globalId.ZookeeperGlobalIdGenerator;
import io.futakotome.zk.ZookeeperClientTemplate;
import io.futakotome.zk.config.EnableZookeeperClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableZookeeperClient
public class ZookeeperGlobalIdGeneratorConfiguration implements GlobalIdGeneratorConfigurationExtension {
    @Bean
    public ZookeeperGlobalIdGenerator zookeeperGlobalIdGenerator(ZookeeperClientTemplate template) {
        return new ZookeeperGlobalIdGenerator(template);
    }
}
