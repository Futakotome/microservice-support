package io.futakotome.MHDatasource.config.redis;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.util.HashMap;

@ConditionalOnProperty(prefix = "multiple.redis", name = "enabled", havingValue = "true")
@Configuration(proxyBeanMethods = false)
public class RedisConnectionsRouterConfiguration extends AbstractRedisConnectionConfiguration {

    protected RedisConnectionsRouterConfiguration(RedisDynamicConnectionProperties redisDynamicConnectionProperties) {
        super(redisDynamicConnectionProperties);
    }

    @Bean
    @Primary
    public RedisConnectionFactory redisConnectionFactory() {
        RedisConnectionFactoryRouter redisConnectionFactory = new RedisConnectionFactoryRouter();
        redisConnectionFactory.setTargetRedisConnectionFactory(new HashMap<Object, Object>() {{
            putAll(redisConnectionFactories());
        }});
        redisConnectionFactory.setDefaultRedisConnectionFactory(getFirstRedisConnectionFactory());
        return redisConnectionFactory;
    }

}
