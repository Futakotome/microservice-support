package io.futakotome.layerCache.config;

import io.futakotome.layerCache.cache.LayeringCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration(proxyBeanMethods = false)
public class LayerCacheRegister extends AbstractLayerCacheConfiguration {

    public LayerCacheRegister(LayerCacheProperties properties) {
        super(properties);
    }

    @Bean
    @Primary
    public CacheManager cacheManager() {
        return new LayeringCacheManager(cacheManagers());
    }

    @Bean
    @Primary
    public CacheResolver cacheResolver(CacheManager cacheManager) {
        return new SimpleCacheResolver(cacheManager);
    }

    @Bean
    @Primary
    public KeyGenerator keyGenerator() {
        return new SimpleKeyGenerator();
    }

    @Bean
    @Primary
    public CacheErrorHandler errorHandler() {
        return new SimpleCacheErrorHandler();
    }
}
