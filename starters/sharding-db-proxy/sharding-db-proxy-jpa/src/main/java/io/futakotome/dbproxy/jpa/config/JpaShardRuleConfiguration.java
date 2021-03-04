package io.futakotome.dbproxy.jpa.config;

import io.futakotome.dbproxy.config.ShardRuleConfigurationExtension;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class JpaShardRuleConfiguration implements ShardRuleConfigurationExtension {
}
