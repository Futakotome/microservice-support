package io.futakotome.dbproxy.jpa.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "proxy.jpa.rules")
public class JpaShardRuleProperties implements Serializable {

    private List<TableShardRule> rules;

    @Getter
    @Setter
    static class TableShardRule implements Serializable {

        private String tableName;

        private List<TableShardDimensionConfig> dimensionConfigs;

        @Getter
        @Setter
        static class TableShardDimensionConfig implements Serializable {

            private String dbRule;

            private String dbIndexes;

            private String tbRule;

            private String tbSuffix;

            private boolean tbSuffixZeroPadding;

            private boolean isMaster;

            private String tableName;

        }
    }
}
