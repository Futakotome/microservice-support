package io.futakotome.dbproxy.config;

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
@Import(EnableShardDatasource.DatabaseTypeImporter.class)
public @interface EnableShardDatasource {

    @RequiredArgsConstructor
    class DatabaseTypeImporter implements ImportSelector, ResourceLoaderAware {

        private ResourceLoader resourceLoader;

        @Override
        public void setResourceLoader(ResourceLoader resourceLoader) {
            this.resourceLoader = resourceLoader;
        }

        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            List<String> result = new ArrayList<>();

            result.addAll(loadFactoryNames(DatasourceConfigurationExtension.class, resourceLoader.getClassLoader()));
            result.addAll(loadFactoryNames(ShardRuleConfigurationExtension.class, resourceLoader.getClassLoader()));

            return result.toArray(new String[result.size()]);
        }
    }
}
