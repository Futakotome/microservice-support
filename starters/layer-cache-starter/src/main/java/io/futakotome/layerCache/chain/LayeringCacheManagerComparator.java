package io.futakotome.layerCache.chain;

import org.springframework.cache.CacheManager;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class LayeringCacheManagerComparator implements Comparator<CacheManager>, Serializable {
    private static final int INITIAL_ORDER = 100;
    private static final int ORDER_STEP = 100;
    private final Map<String, Integer> filterToOrder = new HashMap<>();

    public LayeringCacheManagerComparator(List<CacheManager> cacheManagers) {
        Step order = new Step(INITIAL_ORDER, ORDER_STEP);
        //todo many interceptor ,such as bloom filter?
//        put(L1Cache.class, order.next());
//        put(L2Cache.class, order.next());
        for (CacheManager cacheManager : cacheManagers) {
            put(cacheManager.getClass(), order.next());
        }
    }

    public boolean isRegistered(Class<? extends CacheManager> cacheManager) {
        return getOrder(cacheManager) != null;
    }

    public void registerAfter(Class<? extends CacheManager> cacheManager,
                              Class<? extends CacheManager> after) {
        Integer position = getOrder(after);
        if (position == null) {
            throw new IllegalArgumentException(
                    "Cannot register after unregistered cacheManager " + after);
        }

        put(cacheManager, position + 1);
    }

    public void registerAt(Class<? extends CacheManager> cacheManager,
                           Class<? extends CacheManager> at) {
        Integer position = getOrder(at);
        if (position == null) {
            throw new IllegalArgumentException(
                    "Cannot register at unregistered cacheManager " + at);
        }

        put(cacheManager, position);
    }

    public void registerBefore(Class<? extends CacheManager> cacheManager,
                               Class<? extends CacheManager> before) {
        Integer position = getOrder(before);
        if (position == null) {
            throw new IllegalArgumentException("Cannot register before unregistered cacheManager " + before);
        }
        put(cacheManager, position - 1);
    }

    private static class Step {
        private int value;
        private final int stepSize;

        Step(int initialValue, int stepSize) {
            this.value = initialValue;
            this.stepSize = stepSize;
        }

        int next() {
            int value = this.value;
            this.value += this.stepSize;
            return value;
        }
    }

    private void put(Class<? extends CacheManager> cacheManager, int position) {
        filterToOrder.put(cacheManager.getName(), position);
    }

    private Integer getOrder(Class<?> clz) {
        while (clz != null) {
            Integer result = filterToOrder.get(clz.getName());
            if (result != null) {
                return result;
            }
            clz = clz.getSuperclass();
        }
        return null;
    }

    @Override
    public int compare(CacheManager o1, CacheManager o2) {
        return 0;
    }
}
