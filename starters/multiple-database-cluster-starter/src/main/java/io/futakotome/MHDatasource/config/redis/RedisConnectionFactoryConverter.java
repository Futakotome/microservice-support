package io.futakotome.MHDatasource.config.redis;

import io.futakotome.MHDatasource.config.DataSourceDefinition;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;

public final class RedisConnectionFactoryConverter implements Converter<DataSourceDefinition, RedisConnectionFactory> {
    @Override
    public RedisConnectionFactory convert(DataSourceDefinition source) {
        RedisConnectionsDefinition redisConnectionsDefinition = (RedisConnectionsDefinition) source;
        if (redisConnectionsDefinition.getJedis() != null && redisConnectionsDefinition.getLettuce() == null) {
            return jedisConnectionFactory(baseConfiguration(redisConnectionsDefinition),
                    jedisGenericConfiguration(redisConnectionsDefinition));
        } else if (redisConnectionsDefinition.getJedis() == null && redisConnectionsDefinition.getLettuce() != null) {
            return lettuceConnectionFactory(baseConfiguration(redisConnectionsDefinition),
                    lettuceGenericConfiguration(redisConnectionsDefinition));
        } else if (redisConnectionsDefinition.getJedis() != null && redisConnectionsDefinition.getLettuce() != null) {
            throw new IllegalArgumentException("Redis connection [" + redisConnectionsDefinition.getKey() + "] pool's count >=2 ! Check plz.");
        } else {
            throw new IllegalArgumentException("Redis connection [" + redisConnectionsDefinition.getKey() + "] at least one.[Jedis/Lettuce] ");
        }

    }

    private JedisConnectionFactory jedisConnectionFactory(RedisStandaloneConfiguration baseConfig,
                                                          GenericObjectPoolConfig genericConfig) {
        JedisClientConfiguration.JedisClientConfigurationBuilder builder
                = JedisClientConfiguration.builder();
        builder.usePooling().poolConfig(genericConfig);
        return new JedisConnectionFactory(baseConfig, builder.build());
    }

    private LettuceConnectionFactory lettuceConnectionFactory(RedisStandaloneConfiguration baseConfig,
                                                              GenericObjectPoolConfig genericConfig) {
        LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder builder
                = LettucePoolingClientConfiguration.builder();
        builder.poolConfig(genericConfig);
        return new LettuceConnectionFactory(baseConfig, builder.build());
    }

    private RedisStandaloneConfiguration baseConfiguration(RedisConnectionsDefinition redisConnectionsDefinition) {
        RedisStandaloneConfiguration basePoolConfig = new RedisStandaloneConfiguration();
        basePoolConfig.setHostName(redisConnectionsDefinition.getHost());
        basePoolConfig.setDatabase(redisConnectionsDefinition.getDatabase());
        basePoolConfig.setPassword(redisConnectionsDefinition.getPassword());
        basePoolConfig.setPort(redisConnectionsDefinition.getPort());
        return basePoolConfig;
    }

    private GenericObjectPoolConfig<?> lettuceGenericConfiguration(RedisConnectionsDefinition redisConnectionsDefinition) {
        GenericObjectPoolConfig<?> genericObjectPoolConfig = new GenericObjectPoolConfig<>();
        genericObjectPoolConfig.setMaxIdle(redisConnectionsDefinition.getLettuce().getPool().getMaxIdle());
        genericObjectPoolConfig.setMaxTotal(redisConnectionsDefinition.getLettuce().getPool().getMaxActive());
        genericObjectPoolConfig.setMinIdle(redisConnectionsDefinition.getLettuce().getPool().getMinIdle());
        if (redisConnectionsDefinition.getLettuce().getPool().getMaxWait() != null) {
            genericObjectPoolConfig.setMaxWaitMillis(redisConnectionsDefinition.getLettuce().getPool().getMaxWait().toMillis());
        }
        return genericObjectPoolConfig;
    }

    private GenericObjectPoolConfig<?> jedisGenericConfiguration(RedisConnectionsDefinition redisConnectionsDefinition) {
        GenericObjectPoolConfig<?> genericObjectPoolConfig = new GenericObjectPoolConfig<>();
        genericObjectPoolConfig.setMaxIdle(redisConnectionsDefinition.getJedis().getPool().getMaxIdle());
        genericObjectPoolConfig.setMaxTotal(redisConnectionsDefinition.getJedis().getPool().getMaxActive());
        genericObjectPoolConfig.setMinIdle(redisConnectionsDefinition.getJedis().getPool().getMinIdle());
        if (redisConnectionsDefinition.getJedis().getPool().getMaxWait() != null) {
            genericObjectPoolConfig.setMaxWaitMillis(redisConnectionsDefinition.getJedis().getPool().getMaxWait().toMillis());
        }
        return genericObjectPoolConfig;
    }
}
