package io.futakotome.layerCache.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(LayerCacheProperties.class)
@Import(LayerCacheRegister.class)
public class LayerCacheAutoConfiguration {

}
