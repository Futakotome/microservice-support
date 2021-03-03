package io.futakotome.cache.domain.entity;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.lang.Nullable;

import java.util.Collection;

public class StatsCacheManagerProxy implements CacheManager {
    private CacheManager target;

    public StatsCacheManagerProxy(CacheManager cacheManager) {
        this.target = cacheManager;
    }

    @Override
    @Nullable
    public Cache getCache(String name) {
        System.out.println(target.getClass().getName() + "---getCache");
        return target.getCache(name);
    }

    @Override
    public Collection<String> getCacheNames() {
        System.out.println(target.getClass().getName() + "---getCacheNames");
        return target.getCacheNames();
    }
}
