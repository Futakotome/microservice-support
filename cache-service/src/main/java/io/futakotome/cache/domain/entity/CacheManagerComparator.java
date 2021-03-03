package io.futakotome.cache.domain.entity;

import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public final class CacheManagerComparator implements Comparator<CacheManager>, Serializable {
    private static final int INITIAL_ORDER = 100;
    private static final int ORDER_STEP = 100;
    private final Map<String, Integer> cacheManagerToOrder = new HashMap<>();

    public CacheManagerComparator() {
        Step order = new Step(INITIAL_ORDER, ORDER_STEP);
        put(CaffeineCacheManager.class, order.next());
        put(ConcurrentMapCacheManager.class, order.next());
    }

    public boolean isRegister(Class<? extends CacheManager> cacheManager) {
        return getOrder(cacheManager) != null;
    }

    public void registerAfter(Class<? extends CacheManager> cacheManager, Class<? extends CacheManager> afterCacheManager) {
        Integer position = getOrder(cacheManager);
        if (position == null) {
            throw new IllegalArgumentException("Cannot register after unregister cacheManager " + afterCacheManager);
        }
        put(cacheManager, position + 1);
    }

    public void registerAt(Class<? extends CacheManager> cacheManager, Class<? extends CacheManager> atCacheManager) {
        Integer position = getOrder(atCacheManager);
        if (position == null) {
            throw new IllegalArgumentException("Cannot register after unregister cacheManager " + atCacheManager);
        }
        put(cacheManager, position);
    }

    public void register(Class<? extends CacheManager> cacheManager, Class<? extends CacheManager> beforeCacheManager) {
        Integer position = getOrder(beforeCacheManager);
        if (position == null) {
            throw new IllegalArgumentException("Cannot register after unregister cacheManager " + beforeCacheManager);
        }
        put(cacheManager, position - 1);
    }

    private void put(Class<? extends CacheManager> cacheManager, int position) {
        String clzName = cacheManager.getName();
        cacheManagerToOrder.put(clzName, position);
    }

    private Integer getOrder(Class<?> clz) {
        while (clz != null) {
            Integer result = cacheManagerToOrder.get(clz.getName());
            if (result != null) {
                return result;
            }
            clz = clz.getSuperclass();
        }
        return null;
    }

    @Override
    public int compare(CacheManager o1, CacheManager o2) {
        return getOrder(o1.getClass()) - getOrder(o2.getClass());
    }

    private static class Step {
        private int value;
        private final int stepSize;

        Step(int initValue, int stepSize) {
            this.value = initValue;
            this.stepSize = stepSize;
        }

        int next() {
            int value = this.value;
            this.value += this.stepSize;
            return value;
        }
    }

}
