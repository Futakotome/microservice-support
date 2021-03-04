package io.futakotome.dbproxy.jpa.config;

import io.futakotome.dbproxy.config.ShardRuleConfigurationExtension;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(JpaShardRuleProperties.class)
public class JpaShardRuleConfiguration implements ShardRuleConfigurationExtension {
}
