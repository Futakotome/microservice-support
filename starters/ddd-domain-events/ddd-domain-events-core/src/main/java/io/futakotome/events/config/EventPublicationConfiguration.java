package io.futakotome.events.config;

import io.futakotome.events.EventPublicationRegistry;
import io.futakotome.events.support.CompletionRegisteringBeanPostProcessor;
import io.futakotome.events.support.MapEventPublicationRegistry;
import io.futakotome.events.support.PersistentApplicationEventMulticaster;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class EventPublicationConfiguration {

    @Bean
    public PersistentApplicationEventMulticaster applicationEventMulticaster(ObjectProvider<EventPublicationRegistry> registries) {
        return new PersistentApplicationEventMulticaster(
                () -> registries.getIfAvailable(MapEventPublicationRegistry::new)
        );
    }

    @Bean
    public CompletionRegisteringBeanPostProcessor beanPostProcessor(ObjectFactory<EventPublicationRegistry> store) {
        return new CompletionRegisteringBeanPostProcessor(store::getObject);
    }
}
