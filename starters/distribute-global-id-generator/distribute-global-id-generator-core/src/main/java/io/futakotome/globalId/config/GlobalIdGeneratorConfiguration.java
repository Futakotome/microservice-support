package io.futakotome.globalId.config;

import io.futakotome.globalId.DefaultIdGenerator;
import io.futakotome.globalId.IdGenerator;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class GlobalIdGeneratorConfiguration {

    @Bean
    public IdGeneratorConfiguration idGeneratorConfiguration(ObjectProvider<IdGenerator> idGenerators) {
        return new IdGeneratorConfiguration(
                () -> idGenerators.getIfAvailable(DefaultIdGenerator::new)
        );
    }

}
