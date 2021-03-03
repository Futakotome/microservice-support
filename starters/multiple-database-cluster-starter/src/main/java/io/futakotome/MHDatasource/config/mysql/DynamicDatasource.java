package io.futakotome.MHDatasource.config.mysql;

import io.futakotome.MHDatasource.config.DynamicDatasourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@Slf4j
public class DynamicDatasource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        String datasourceKey = DynamicDatasourceContextHolder.getDatasourceKey();
        if (datasourceKey != null) {
            log.info("线程[{}],Mysql切换到数据源:[{}]", Thread.currentThread().getId(), datasourceKey);
        }
        return datasourceKey;
    }
}
