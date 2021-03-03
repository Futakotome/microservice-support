package io.futakotome.layerCache.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "cache")
public class LayerCacheProperties implements InitializingBean {

    private Map<String, LayerConfiguration> layers = new LinkedHashMap<>();

    public Map<String, LayerConfiguration> getLayers() {
        return layers;
    }

    public void setLayers(Map<String, LayerConfiguration> layers) {
        this.layers = layers;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("多级缓存配置====>");
        System.out.println(toString());
        System.out.println("多级缓存配置完成====>");
    }

    public static class LayerConfiguration {

        private boolean enabled;

        private CacheType cacheType;

        public CacheType getCacheType() {
            return cacheType;
        }

        public void setCacheType(CacheType cacheType) {
            this.cacheType = cacheType;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        @Override
        public String toString() {
            return "\t\t" + "enabled:" + enabled + "\n" +
                    "\t\t" + "cacheType:" + cacheType +
                    "\n";
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[").append("\n");
        for (Map.Entry<String, LayerConfiguration> entry : layers.entrySet()) {
            stringBuilder.append("\t").append(entry.getKey()).append(":")
                    .append("\n")
                    .append(entry.getValue().toString());
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
