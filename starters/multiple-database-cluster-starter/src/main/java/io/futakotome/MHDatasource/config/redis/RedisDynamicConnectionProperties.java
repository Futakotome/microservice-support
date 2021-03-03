package io.futakotome.MHDatasource.config.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "multiple.redis")
public class RedisDynamicConnectionProperties implements Serializable {

    private boolean enabled = false;

    private List<RedisConnectionsDefinition> connections = new ArrayList<>();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<RedisConnectionsDefinition> getConnections() {
        return connections;
    }

    public void setConnections(List<RedisConnectionsDefinition> connections) {
        this.connections = connections;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[" + "\n");
        for (RedisConnectionsDefinition redisConnectionsDefinition : connections) {
            stringBuilder.append(redisConnectionsDefinition.toString())
                    .append("\n");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
