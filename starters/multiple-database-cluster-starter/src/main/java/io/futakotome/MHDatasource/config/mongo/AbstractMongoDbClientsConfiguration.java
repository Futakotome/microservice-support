package io.futakotome.MHDatasource.config.mongo;

import io.futakotome.MHDatasource.config.DataSourceDefinition;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.MongoDbFactory;

import java.util.HashMap;
import java.util.Map;

abstract class AbstractMongoDbClientsConfiguration implements InitializingBean {
    private final MongoDynamicClientsProperties properties;
    private final Map<String, MongoDbFactory> MONGO_DB_FACTORIES = new HashMap<>();
    private final Converter<DataSourceDefinition, MongoDbFactory> mongoDbFactoryConverter = new MongoDbFactoryConverter();

    public AbstractMongoDbClientsConfiguration(MongoDynamicClientsProperties dynamicClientsProperties) {
        this.properties = dynamicClientsProperties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("[MongoDb动态多数据源装配]>>>>>>>>");
        System.out.println(properties.toString());
        System.out.println("[complete]>>>>>>>>");

        Map<String, DataSourceDefinition> mongodbFactoryDefinitions = new HashMap<>();
        properties.getClients()
                .forEach(mongoDynamicDbDefinition -> mongodbFactoryDefinitions.put(mongoDynamicDbDefinition.getKey(), mongoDynamicDbDefinition));
        for (Map.Entry<String, DataSourceDefinition> entry : mongodbFactoryDefinitions.entrySet()) {
            MONGO_DB_FACTORIES.put(entry.getKey(), mongoDbFactoryConverter.convert(entry.getValue()));
        }
    }

    protected MongoDbFactory getFirstMongoDbFactory() {
        MongoDbFactory firstMongoDbFactory = null;
        for (Map.Entry<String, MongoDbFactory> entry : MONGO_DB_FACTORIES.entrySet()) {
            firstMongoDbFactory = entry.getValue();
            if (firstMongoDbFactory != null) {
                break;
            }
        }
        return firstMongoDbFactory;
    }

    protected String firstMongoUri() {
        String firstUri = null;
        for (MongoDynamicClientsDefinition mongoDynamicClientsDefinition : properties.getClients()) {
            firstUri = mongoDynamicClientsDefinition.getUri();
            if (firstUri != null) {
                break;
            }
        }
        return firstUri;
    }

    protected Map<String, MongoDbFactory> mongoDbFactoryMap() {
        return MONGO_DB_FACTORIES;
    }

}
