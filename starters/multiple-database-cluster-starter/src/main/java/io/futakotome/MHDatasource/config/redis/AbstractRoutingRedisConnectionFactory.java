package io.futakotome.MHDatasource.config.redis;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractRoutingRedisConnectionFactory extends AbstractRedisConnectionFactory
        implements InitializingBean {

    @Nullable
    private Map<Object, Object> targetRedisConnectionFactory;

    @Nullable
    private Object defaultRedisConnectionFactory;

    private boolean lenientFallback = true;

    @Nullable
    private Map<Object, RedisConnectionFactory> resolvedRedisConnectionFactory;

    @Nullable
    private RedisConnectionFactory resolvedDefaultRedisConnectionFactory;

    public void setTargetRedisConnectionFactory(Map<Object, Object> targetRedisConnectionFactory) {
        this.targetRedisConnectionFactory = targetRedisConnectionFactory;
    }

    public void setDefaultRedisConnectionFactory(Object defaultRedisConnectionFactory) {
        this.defaultRedisConnectionFactory = defaultRedisConnectionFactory;
    }

    public void setLenientFallback(boolean lenientFallback) {
        this.lenientFallback = lenientFallback;
    }

    @Override
    public void afterPropertiesSet() {
        if (this.targetRedisConnectionFactory == null) {
            throw new IllegalArgumentException("Redis targetRedisConnectionFactory is required");
        }
        this.resolvedRedisConnectionFactory = new HashMap<>(this.targetRedisConnectionFactory.size());
        this.targetRedisConnectionFactory.forEach((key, value) -> {
            Object lookupKey = resolveSpecifiedLookupKey(key);
            RedisConnectionFactory redisConnectionFactory = resolveSpecifiedRedisConnection(value);
            this.resolvedRedisConnectionFactory.put(lookupKey, redisConnectionFactory);
        });
        if (this.defaultRedisConnectionFactory != null) {
            this.resolvedDefaultRedisConnectionFactory = resolveSpecifiedRedisConnection(this.defaultRedisConnectionFactory);
        }
    }

    @Override
    public RedisConnection getConnection() {
        return determineTargetRedisConnection().getConnection();
    }

    @Override
    public RedisClusterConnection getClusterConnection() {
        return determineTargetRedisConnection().getClusterConnection();
    }

    @Override
    public RedisSentinelConnection getSentinelConnection() {
        return determineTargetRedisConnection().getSentinelConnection();
    }


    protected Object resolveSpecifiedLookupKey(Object lookupKey) {
        return lookupKey;
    }

    protected RedisConnectionFactory resolveSpecifiedRedisConnection(Object connectionFactory) throws IllegalArgumentException {
        if (connectionFactory instanceof RedisConnectionFactory) {
            return (RedisConnectionFactory) connectionFactory;
        } else if (connectionFactory instanceof String) {
            //todo 各类lookup
            return null;
        } else {
            throw new IllegalArgumentException("Only [org.springframework.data.redis.connection.RedisConnection] supported: " + connectionFactory);
        }
    }

    protected RedisConnectionFactory determineTargetRedisConnection() {
        Assert.notNull(this.resolvedRedisConnectionFactory, "RedisConnectionFactory router not initialized");
        Object lookupKey = determineCurrentLookupKey();
        RedisConnectionFactory redisConnectionFactory = this.resolvedRedisConnectionFactory.get(lookupKey);
        if (redisConnectionFactory == null && (this.lenientFallback || lookupKey == null)) {
            redisConnectionFactory = this.resolvedDefaultRedisConnectionFactory;
        } else if (redisConnectionFactory != null) {
            if (redisConnectionFactory.getClass().equals(LettuceConnectionFactory.class)) {
                ((LettuceConnectionFactory) redisConnectionFactory).afterPropertiesSet();
            }
        }
        if (redisConnectionFactory == null) {
            throw new IllegalArgumentException("Cannot determine target RedisConnectionFactory for lookup key [" + lookupKey + "]");
        }
        return redisConnectionFactory;
    }

    @Nullable
    protected abstract Object determineCurrentLookupKey();
}
