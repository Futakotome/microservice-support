package io.futakotome.events.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.annotation.*;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.core.io.support.SpringFactoriesLoader.loadFactoryNames;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(EnablePersistentDomainEvents.PersistentDomainEventsImportSelector.class)
public @interface EnablePersistentDomainEvents {

    @RequiredArgsConstructor
    class PersistentDomainEventsImportSelector implements ImportSelector, ResourceLoaderAware {

        private ResourceLoader resourceLoader;

        @Override
        public void setResourceLoader(ResourceLoader resourceLoader) {
            this.resourceLoader = resourceLoader;
        }

        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            List<String> result = new ArrayList<>();

            result.add(EventPublicationConfiguration.class.getName());
            result.addAll(loadFactoryNames(EventPublicationConfigurationExtension.class, resourceLoader.getClassLoader()));
            result.addAll(loadFactoryNames(EventSerializationConfigurationExtension.class, resourceLoader.getClassLoader()));

            return result.toArray(new String[result.size()]);
        }
    }
}
