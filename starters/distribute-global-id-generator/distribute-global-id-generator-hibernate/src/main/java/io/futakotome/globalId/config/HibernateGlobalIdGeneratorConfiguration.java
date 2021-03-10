package io.futakotome.globalId.config;

import io.futakotome.globalId.HibernateGlobalIdGenerator;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class HibernateGlobalIdGeneratorConfiguration implements GlobalIdGeneratorConfigurationExtension {
    @Bean
    public HibernateGlobalIdGenerator hibernateGlobalIdGenerator(ObjectFactory<IdGeneratorConfiguration> idGeneratorConfiguration) {
        return new HibernateGlobalIdGenerator(idGeneratorConfiguration::getObject);
    }
}
