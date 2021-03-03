package io.futakotome.cache.domain.entity.stats;

import lombok.Data;

import java.io.Serializable;

@Data
public class CacheStatInfo implements Serializable {
    private String cacheName;
    private String key;
    private long requestCount;
    private long missCount;
    private double hitRate;
    private long firstCacheRequestCount;
    private long firstCacheSize;
    private long firstCacheMissCount;
    private long secondCacheRequestCount;
    private long secondCacheSize;
    private long secondCacheMissCount;

}
