package io.futakotome.MHDatasource.config.redis;

import io.futakotome.MHDatasource.config.DynamicDatasourceContextHolder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RedisConnectionFactoryRouter extends AbstractRoutingRedisConnectionFactory {
    @Override
    protected Object determineCurrentLookupKey() {
        String connectionFactoryKey = DynamicDatasourceContextHolder.getDatasourceKey();
        if (connectionFactoryKey != null) {
            log.info("线程[{}],Redis切换到连接工厂:[{}]", Thread.currentThread().getId(), connectionFactoryKey);
        }
        return connectionFactoryKey;
    }


}
