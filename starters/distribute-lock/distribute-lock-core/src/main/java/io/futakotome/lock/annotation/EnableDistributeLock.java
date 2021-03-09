package io.futakotome.lock.annotation;

import io.futakotome.lock.configuration.DistributeLockConfigurationExtension;
import io.futakotome.lock.configuration.DistributeLockExecutorConfigurationExtension;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.annotation.*;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.core.io.support.SpringFactoriesLoader.loadFactoryNames;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Import(EnableDistributeLock.DistributeLockImportSelector.class)
public @interface EnableDistributeLock {

    class DistributeLockImportSelector implements ImportSelector, ResourceLoaderAware {

        private ResourceLoader resourceLoader;

        @Override
        public void setResourceLoader(ResourceLoader resourceLoader) {
            this.resourceLoader = resourceLoader;
        }

        @Override
        public String[] selectImports(AnnotationMetadata annotationMetadata) {
            List<String> result = new ArrayList<>();

            result.add(DistributeLockImportSelector.class.getName());
            result.addAll(loadFactoryNames(DistributeLockConfigurationExtension.class, resourceLoader.getClassLoader()));
            result.addAll(loadFactoryNames(DistributeLockExecutorConfigurationExtension.class, resourceLoader.getClassLoader()));

            return result.toArray(new String[result.size()]);
        }
    }
}
