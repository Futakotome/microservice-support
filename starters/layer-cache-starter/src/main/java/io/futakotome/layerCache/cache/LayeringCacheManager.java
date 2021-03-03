package io.futakotome.layerCache.cache;

import io.futakotome.layerCache.chain.LayerCacheManagerProcessChain;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.*;

public class LayeringCacheManager implements CacheManager {

    private final List<CacheManager> cacheManagers;

    private final LayerCacheManagerProcessChain chain;

    public LayeringCacheManager(List<CacheManager> cacheManagers) {
        this.cacheManagers = cacheManagers;
        this.chain = new LayerCacheManagerProcessChain(cacheManagers);
    }

    @Override
    public Cache getCache(String name) {
        return chain.processGetCache(name);
    }

    @Override
    public Collection<String> getCacheNames() {
        Set<String> names = new LinkedHashSet<>();
        for (CacheManager manager : this.cacheManagers) {
            names.addAll(manager.getCacheNames());
        }
        return Collections.unmodifiableSet(names);
    }


}
