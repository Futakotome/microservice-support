package io.futakotome.MHDatasource.config.mysql;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "multiple.mysql", name = "enabled", havingValue = "true")
@EnableTransactionManagement
@EnableConfigurationProperties(MysqlDynamicDatasourceProperties.class)
@Import(MysqlDatasourcesRouterConfiguration.class)
public class DynamicDatasourceRouterAutoConfiguration {

}
