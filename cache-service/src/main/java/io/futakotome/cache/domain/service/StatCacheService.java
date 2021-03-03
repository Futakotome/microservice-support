//package io.futakotome.cache.domain.service;
//
//import io.futakotome.cache.domain.entity.stats.CacheStatInfo;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.support.AbstractCacheManager;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//import org.springframework.util.StringUtils;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Set;
//
//@Slf4j
//@Service
//public class StatCacheService {
//    public static final String CACHE_STATS_KEY_PREFIX = "cache:cache_stats_info:";
//
//    private CacheManager cacheManager;
//
//    public List<CacheStatInfo> listCacheStats(String cacheNameParam) {
//        Set<String> cacheKeys = (Set<String>) cacheManager.getCacheNames();
//        if (log.isDebugEnabled()) {
//            log.debug("获取缓存统计数据");
//            log.debug(String.valueOf(cacheKeys));
//        }
//        if (CollectionUtils.isEmpty(cacheKeys)) {
//            return Collections.emptyList();
//        }
//        List<CacheStatInfo> statInfos = new ArrayList<>();
//        for (String key : cacheKeys) {
//            if (StringUtils.hasText(cacheNameParam) && key.startsWith(CACHE_STATS_KEY_PREFIX + cacheNameParam)) {
//                continue;
//            }
//            CacheStatInfo cacheStatInfo = cacheManager.getCache();
//
//        }
//
//    }
//
//    public void resetCacheStat(String key) {
//
//    }
//
//    public void setCacheManager(AbstractCacheManager abstractCacheManager) {
//        this.cacheManager = abstractCacheManager;
//    }
//}
