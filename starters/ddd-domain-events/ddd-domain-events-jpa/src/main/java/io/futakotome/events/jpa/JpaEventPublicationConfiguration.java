package io.futakotome.events.jpa;

import io.futakotome.events.EventSerializer;
import io.futakotome.events.config.EventPublicationConfigurationExtension;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.util.ClassUtils;

@Configuration(proxyBeanMethods = false)
@Import(JpaEventPublicationConfiguration.RepositoriesEnablingImportSelector.class)
@RequiredArgsConstructor
public class JpaEventPublicationConfiguration implements EventPublicationConfigurationExtension {

    private final JpaEventPublicationRepository repository;
    private final ObjectFactory<EventSerializer> serializer;

    @Bean
    public JpaEventPublicationRegistry jpaEventPublicationRegistry() {
        return new JpaEventPublicationRegistry(repository, serializer.getObject());
    }

    static class RepositoriesEnablingImportSelector implements ImportSelector {
        private static final boolean IN_SPRING_BOOT = ClassUtils.isPresent("org.springframework.boot.SpringApplication",
                RepositoriesEnablingImportSelector.class.getClassLoader());

        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            return IN_SPRING_BOOT ? new String[0] : new String[]{EnablingConfig.class.getName()};
        }

        @Configuration
        @EnableJpaRepositories
        static class EnablingConfig {
        }
    }
}
