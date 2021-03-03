package io.futakotome.cache.domain.entity.stats;

import lombok.Data;

import java.io.Serializable;
import java.util.concurrent.atomic.LongAdder;

@Data
public class CacheOptStat implements Serializable {
    private LongAdder cacheRequestCount;
    private LongAdder cacheMethodRequestCount;
    private LongAdder cacheMethodRequestTime;

    public CacheOptStat() {
        this.cacheRequestCount = new LongAdder();
        this.cacheMethodRequestCount = new LongAdder();
        this.cacheMethodRequestTime = new LongAdder();
    }

    public void addCachedRequest(long num) {
        cacheRequestCount.add(num);
    }

    public void addCachedMethodRequestCount(long num) {
        cacheMethodRequestCount.add(num);
    }

    public void addCachedMethodRequestTime(long time) {
        cacheMethodRequestTime.add(time);
    }

    public long getAndResetCachedRequestCount() {
        long longValue = cacheRequestCount.longValue();
        cacheRequestCount.reset();
        return longValue;
    }

    public long getAndResetCachedMethodRequestCount() {
        long longValue = cacheMethodRequestCount.longValue();
        cacheMethodRequestCount.reset();
        return longValue;
    }

    public long getAndResetCachedMethodRequestTime() {
        long longValue = cacheMethodRequestTime.longValue();
        cacheMethodRequestTime.reset();
        return longValue;
    }
}
