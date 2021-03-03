package io.futakotome.events.starter;

import io.futakotome.events.EventPublication;
import io.futakotome.events.config.EnablePersistentDomainEvents;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

@Configuration
@AutoConfigureBefore(HibernateJpaAutoConfiguration.class)
@Import(DomainEventsAutoConfiguration.EnableAutoConfigForEventsPackage.class)
@EnablePersistentDomainEvents
public class DomainEventsAutoConfiguration {

    static class EnableAutoConfigForEventsPackage implements ImportBeanDefinitionRegistrar {
        @Override
        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
            AutoConfigurationPackages.register(registry, EventPublication.class.getPackage().getName());
        }
    }
}
