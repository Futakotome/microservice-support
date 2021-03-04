package io.futakotome.dbproxy.jpa.config;

import org.springframework.util.Assert;

public abstract class AbstractProxyDatasourceConfiguration {
    private JpaProxyDatasourceProperties properties;

    public AbstractProxyDatasourceConfiguration(JpaProxyDatasourceProperties properties) {
        Assert.notNull(properties, "Jpa proxy datasource properties must not be null !");

    }
}
