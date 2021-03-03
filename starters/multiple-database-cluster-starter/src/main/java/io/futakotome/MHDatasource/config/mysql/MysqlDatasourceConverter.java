package io.futakotome.MHDatasource.config.mysql;

import io.futakotome.MHDatasource.config.DataSourceDefinition;
import io.futakotome.MHDatasource.ssh.SSHTunneledHikariDatasource;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.convert.converter.Converter;

import javax.sql.DataSource;

public final class MysqlDatasourceConverter implements Converter<DataSourceDefinition, DataSource> {

    @Override
    public DataSource convert(DataSourceDefinition dataSourceDefinition) {
        MysqlDatasourceDefinition mysqlDatasourceDefinition = (MysqlDatasourceDefinition) dataSourceDefinition;
        if (mysqlDatasourceDefinition.getSsh().isEnabled()) {
            SSHTunneledHikariDatasource tunneledHikariDatasource = DataSourceBuilder.create()
                    .username(mysqlDatasourceDefinition.getUsername())
                    .password(mysqlDatasourceDefinition.getPassword())
                    .url(mysqlDatasourceDefinition.getUrl())
                    .driverClassName(mysqlDatasourceDefinition.getDriverClassName())
                    .type(SSHTunneledHikariDatasource.class)
                    .build();
            tunneledHikariDatasource.setSshConfigProperties(mysqlDatasourceDefinition.getSsh());
            tunneledHikariDatasource.tunneled();
            return tunneledHikariDatasource;
        } else {
            return DataSourceBuilder.create()
                    .username(mysqlDatasourceDefinition.getUsername())
                    .password(mysqlDatasourceDefinition.getPassword())
                    .url(mysqlDatasourceDefinition.getUrl())
                    .driverClassName(mysqlDatasourceDefinition.getDriverClassName())
                    .type(HikariDataSource.class)//todo type 应该可以配置
                    .build();
        }
    }
}
