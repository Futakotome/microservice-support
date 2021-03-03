package io.futakotome.MHDatasource.config.mysql;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "multiple.mysql")
public class MysqlDynamicDatasourceProperties implements Serializable {

    private boolean enabled = false;

    private List<MysqlDatasourceDefinition> datasources = new ArrayList<>();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<MysqlDatasourceDefinition> getDatasources() {
        return datasources;
    }

    public void setDatasources(List<MysqlDatasourceDefinition> datasources) {
        this.datasources = datasources;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[" + "\n");
        for (MysqlDatasourceDefinition mysqlDatasourceDefinition : datasources) {
            stringBuilder.append(mysqlDatasourceDefinition.toString())
                    .append("\n");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
