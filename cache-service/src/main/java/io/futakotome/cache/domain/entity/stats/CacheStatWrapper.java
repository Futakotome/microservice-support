package io.futakotome.cache.domain.entity.stats;

import org.springframework.cache.Cache;

public class CacheStatWrapper implements Cache.ValueWrapper {
    @Override
    public Object get() {
        return null;
    }
}
