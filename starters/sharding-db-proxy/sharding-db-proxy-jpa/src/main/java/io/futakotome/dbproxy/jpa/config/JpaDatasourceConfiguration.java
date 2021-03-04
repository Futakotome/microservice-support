package io.futakotome.dbproxy.jpa.config;

import io.futakotome.dbproxy.config.DatasourceConfigurationExtension;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ProxyDatasourceProperties.class)
@RequiredArgsConstructor
public class JpaDatasourceConfiguration implements DatasourceConfigurationExtension {

}
