package io.futakotome.cache.config;

import io.futakotome.cache.aop.CacheInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class CacheConfiguration {

    @Bean
    public CacheInterceptor distributeCacheInterceptor() {
        return new CacheInterceptor();
    }
}
