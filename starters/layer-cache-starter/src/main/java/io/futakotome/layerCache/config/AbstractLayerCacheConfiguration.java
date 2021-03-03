package io.futakotome.layerCache.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;

import java.util.*;

public abstract class AbstractLayerCacheConfiguration implements InitializingBean {
    private final LayerCacheProperties properties;
    private static final Map<CacheType, CacheManager> MAPPINGS;
    private static final List<CacheManager> CACHE_MANAGER_LIST = new ArrayList<>();

    static {
        Map<CacheType, CacheManager> mappings = new EnumMap<>(CacheType.class);
        mappings.put(CacheType.CAFFEINE, new CaffeineCacheManager());
        mappings.put(CacheType.SIMPLE, new ConcurrentMapCacheManager());
//        mappings.put(CacheType.REDIS, new RedisCacheManager());
        MAPPINGS = Collections.unmodifiableMap(mappings);
    }

    public AbstractLayerCacheConfiguration(LayerCacheProperties properties) {
        this.properties = properties;
    }

    protected List<CacheManager> cacheManagers() {
        return CACHE_MANAGER_LIST;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        for (Map.Entry<String, LayerCacheProperties.LayerConfiguration> entry : properties.getLayers().entrySet()) {
            CacheType cacheType = entry.getValue().getCacheType();
            CACHE_MANAGER_LIST.add(MAPPINGS.get(cacheType));
        }
    }
}
