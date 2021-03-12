package io.futakotome.globalId.config;

import io.futakotome.globalId.HibernateGlobalIdGenerator;
import io.futakotome.globalId.IdGenerator;
import io.futakotome.globalId.ZookeeperGlobalIdGenerator;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration(proxyBeanMethods = false)
@Import(ZookeeperGlobalIdGeneratorConfiguration.class)
public class HibernateGlobalIdGeneratorConfiguration implements GlobalIdGeneratorConfigurationExtension {
    @Bean
    public HibernateGlobalIdGenerator hibernateGlobalIdGenerator(ObjectFactory<IdGenerator> idGenerator) {
        return new HibernateGlobalIdGenerator(idGenerator::getObject);
    }
}
