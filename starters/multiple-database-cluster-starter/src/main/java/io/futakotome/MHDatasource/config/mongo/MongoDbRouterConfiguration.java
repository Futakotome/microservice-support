package io.futakotome.MHDatasource.config.mongo;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.HashMap;

@Configuration
@ConditionalOnProperty(prefix = "multiple.mongo", name = "enabled", havingValue = "true")
public class MongoDbRouterConfiguration extends AbstractMongoDbClientsConfiguration {

    public MongoDbRouterConfiguration(MongoDynamicClientsProperties dynamicClientsProperties) {
        super(dynamicClientsProperties);
    }

    @Bean
    @Primary
    public MongoMappingContext mongoMappingContext() {
        return new MongoMappingContext();
    }

    @Bean
    @Primary
    public MappingMongoConverter mappingMongoConverter() {
        DefaultDbRefResolver defaultDbRefResolver = new DefaultDbRefResolver(mongoDbFactory());
        MappingMongoConverter converter = new MappingMongoConverter(defaultDbRefResolver, mongoMappingContext());
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return converter;
    }

    @Bean
    @Primary
    public MongoDbFactory mongoDbFactory() {
        RoutingMongoClientDbFactory routingMongoClientDbFactory = new RoutingMongoClientDbFactory(firstMongoUri());
        routingMongoClientDbFactory.setDefaultMongoDbFactory(getFirstMongoDbFactory());
        routingMongoClientDbFactory.setTargetMongoDbFactory(new HashMap<Object, Object>() {{
            putAll(mongoDbFactoryMap());
        }});
        return routingMongoClientDbFactory;
    }

    @Bean
    @Primary
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoDbFactory(), mappingMongoConverter());
    }


    //todo gridFsTemplate
//    @Bean
//    @Primary
//    public GridFsTemplate gridFsTemplate() {
//
//    }
}
