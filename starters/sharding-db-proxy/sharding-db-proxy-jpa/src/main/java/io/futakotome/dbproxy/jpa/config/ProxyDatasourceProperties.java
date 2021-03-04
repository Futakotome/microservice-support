package io.futakotome.dbproxy.jpa.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@ConfigurationProperties(prefix = "proxy.jpa")
public class ProxyDatasourceProperties implements Serializable {

    private Map<String, DatasourceInfo> datasources;

    @Getter
    @Setter
    static class DatasourceInfo {
        private String url;
        private String username;
        private String password;

    }

}
