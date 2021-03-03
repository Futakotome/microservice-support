package io.futakotome.cache.domain.entity;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.lang.Nullable;

import java.util.*;

public class LevelCacheManager implements CacheManager {
    private final List<CacheManager> cacheManagers;
    private final Comparator<CacheManager> cacheManagerComparator;

    public LevelCacheManager(List<CacheManager> cacheManagers, Comparator<CacheManager> cacheManagerComparator) {
        if (cacheManagers.size() == 0) {
            throw new IllegalArgumentException("Level cache manager's must at least one.");
        }
        this.cacheManagers = cacheManagers;
        this.cacheManagerComparator = cacheManagerComparator;
    }

    @Override
    @Nullable
    public Cache getCache(String name) {
        cacheManagers.sort(cacheManagerComparator);

        return null;
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
