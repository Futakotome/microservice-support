package io.futakotome.zk.config;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Import(EnableZookeeperClient.ZookeeperClientImportSelector.class)
public @interface EnableZookeeperClient {

    class ZookeeperClientImportSelector implements ImportSelector, ResourceLoaderAware {

        private ResourceLoader resourceLoader;

        @Override
        public void setResourceLoader(ResourceLoader resourceLoader) {
            this.resourceLoader = resourceLoader;
        }

        @Override
        public String[] selectImports(AnnotationMetadata annotationMetadata) {
            List<String> result = new ArrayList<>();

            result.add(ZookeeperAutoConfiguration.class.getName());
            result.add(ZookeeperHealthAutoConfiguration.class.getName());

            return result.toArray(new String[result.size()]);
        }
    }

}
