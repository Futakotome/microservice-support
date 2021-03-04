package io.futakotome.dbproxy.jpa.config;

import io.futakotome.dbproxy.config.DatasourceConfigurationExtension;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(JpaProxyDatasourceProperties.class)
public class JpaDatasourceConfiguration
        extends AbstractProxyDatasourceConfiguration
        implements DatasourceConfigurationExtension {

    public JpaDatasourceConfiguration(JpaProxyDatasourceProperties properties) {
        super(properties);
    }
}
