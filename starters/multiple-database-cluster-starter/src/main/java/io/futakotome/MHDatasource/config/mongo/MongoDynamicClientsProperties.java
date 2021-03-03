package io.futakotome.MHDatasource.config.mongo;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "multiple.mongo")
public class MongoDynamicClientsProperties implements Serializable {

    private boolean enabled = false;

    private List<MongoDynamicClientsDefinition> clients = new ArrayList<>();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<MongoDynamicClientsDefinition> getClients() {
        return clients;
    }

    public void setClients(List<MongoDynamicClientsDefinition> clients) {
        this.clients = clients;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[" + "\n");
        for (MongoDynamicClientsDefinition mongoDynamicClientsDefinition : clients) {
            stringBuilder.append(mongoDynamicClientsDefinition.toString())
                    .append("\n");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

}
