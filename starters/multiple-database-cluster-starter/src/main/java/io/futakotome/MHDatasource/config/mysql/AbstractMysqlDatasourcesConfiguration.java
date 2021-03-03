package io.futakotome.MHDatasource.config.mysql;

import io.futakotome.MHDatasource.config.DataSourceDefinition;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.convert.converter.Converter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

abstract class AbstractMysqlDatasourcesConfiguration implements InitializingBean {
    private final MysqlDynamicDatasourceProperties properties;
    private final Converter<DataSourceDefinition, DataSource> mysqlDatasourceConverter = new MysqlDatasourceConverter();
    private final Map<String, DataSource> MYSQL_DATASOURCES = new HashMap<>();
    private final Set<String> PACKAGES = new LinkedHashSet<>();

    public AbstractMysqlDatasourcesConfiguration(MysqlDynamicDatasourceProperties dynamicDatasourceProperties) {
        this.properties = dynamicDatasourceProperties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("[Mysql动态多数据源装配]>>>>>>>>");
        System.out.println(properties.toString());
        System.out.println("[complete]>>>>>>>>");

        Map<String, DataSourceDefinition> mysqlDataSourceDefinitions = new HashMap<>();
        properties.getDatasources()
                .forEach(mysqlDatasourceDefinition -> mysqlDataSourceDefinitions.put(mysqlDatasourceDefinition.getKey(), mysqlDatasourceDefinition));
        for (Map.Entry<String, DataSourceDefinition> entry : mysqlDataSourceDefinitions.entrySet()) {
            MYSQL_DATASOURCES.put(entry.getKey(), mysqlDatasourceConverter.convert(entry.getValue()));
            PACKAGES.add(((MysqlDatasourceDefinition) entry.getValue()).getEntityPackage());
        }
    }

    protected Map<String, DataSource> mysqlDatasources() {
        return MYSQL_DATASOURCES;
    }

    protected String[] packages() {
        return PACKAGES.toArray(new String[]{});
    }

    protected DataSource getFirstDataSource() {
        DataSource dataSource = null;
        for (Map.Entry<String, DataSource> entry : MYSQL_DATASOURCES.entrySet()) {
            dataSource = entry.getValue();
            if (dataSource != null) {
                break;
            }
        }
        return dataSource;
    }
}
