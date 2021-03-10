package io.futakotome.globalId.config;

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
@Import(EnableGlobalIdGenerator.GlobalIdGeneratorImportSelector.class)
public @interface EnableGlobalIdGenerator {

    class GlobalIdGeneratorImportSelector implements ResourceLoaderAware, ImportSelector {

        private ResourceLoader resourceLoader;

        @Override
        public String[] selectImports(AnnotationMetadata annotationMetadata) {
            List<String> result = new ArrayList<>();

            result.add(GlobalIdGeneratorConfiguration.class.getName());
            result.addAll(loadFactoryNames(GlobalIdGeneratorConfigurationExtension.class, resourceLoader.getClassLoader()));

            return result.toArray(new String[result.size()]);
        }

        @Override
        public void setResourceLoader(ResourceLoader resourceLoader) {
            this.resourceLoader = resourceLoader;
        }
    }
}
