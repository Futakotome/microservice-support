package io.futakotome.layerCache.chain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.List;

@Slf4j
public class LayerCacheManagerProcessChain {
    private final List<CacheManager> cacheManagers;

    public LayerCacheManagerProcessChain(List<CacheManager> cacheManagers) {
        cacheManagers.sort(new LayeringCacheManagerComparator(cacheManagers));
        this.cacheManagers = cacheManagers;
    }

    public Cache processGetCache(String name) {
        for (CacheManager cacheManager : cacheManagers) {
            log.info("缓存管理器:{}处理.", cacheManager.getClass().getName());
            Cache cache = cacheManager.getCache(name);
            if (cache != null) {
                log.info("缓存类型:{}", cache.getClass().getName());
                log.info("缓存:{}", cache.getName());
                return cache;
            }
        }
        return null;
    }
}
