package io.futakotome.MHDatasource.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DynamicDatasourceAspectConfig {

    @Bean
    public DynamicDataSourceHandler dynamicDataSourceHandler() {
        return new DynamicDataSourceHandler();
    }

}
