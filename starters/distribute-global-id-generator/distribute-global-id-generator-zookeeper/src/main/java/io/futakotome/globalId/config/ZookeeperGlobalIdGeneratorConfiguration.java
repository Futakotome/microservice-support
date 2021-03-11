package io.futakotome.globalId.config;

import io.futakotome.globalId.ZookeeperGlobalIdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZookeeperGlobalIdGeneratorConfiguration implements GlobalIdGeneratorConfigurationExtension {
    @Bean
    public ZookeeperGlobalIdGenerator zookeeperGlobalIdGenerator() {

    }
}
