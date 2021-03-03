package io.futakotome.MHDatasource.config.redis;

import io.futakotome.MHDatasource.config.DataSourceDefinition;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.util.HashMap;
import java.util.Map;

abstract class AbstractRedisConnectionConfiguration implements InitializingBean {
    private final RedisDynamicConnectionProperties properties;
    private final Converter<DataSourceDefinition, RedisConnectionFactory> redisConnectionFactoryConverter = new RedisConnectionFactoryConverter();
    private final Map<String, RedisConnectionFactory> REDIS_CONNECTION_FACTORIES = new HashMap<>();

    protected AbstractRedisConnectionConfiguration(RedisDynamicConnectionProperties redisDynamicConnectionProperties) {
        this.properties = redisDynamicConnectionProperties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("[Redis动态多连接工厂装配]>>>>>>>>");
        System.out.println(properties.toString());
        System.out.println("[complete]>>>>>>>>");

        Map<String, DataSourceDefinition> redisConnectionDefinitions = new HashMap<>();
        properties.getConnections()
                .forEach(redisConnectionsDefinition -> redisConnectionDefinitions.put(redisConnectionsDefinition.getKey(), redisConnectionsDefinition));
        for (Map.Entry<String, DataSourceDefinition> entry : redisConnectionDefinitions.entrySet()) {
            REDIS_CONNECTION_FACTORIES.put(entry.getKey(), redisConnectionFactoryConverter.convert(entry.getValue()));
        }
    }

    protected Map<String, RedisConnectionFactory> redisConnectionFactories() {
        return REDIS_CONNECTION_FACTORIES;
    }

    protected RedisConnectionFactory getFirstRedisConnectionFactory() {
        RedisConnectionFactory connectionFactory = null;
        for (Map.Entry<String, RedisConnectionFactory> entry : REDIS_CONNECTION_FACTORIES.entrySet()) {
            connectionFactory = entry.getValue();
            if (connectionFactory != null) {
                break;
            }
        }
        return connectionFactory;
    }

}
