package io.futakotome.MHDatasource.config.mongo;

import io.futakotome.MHDatasource.config.DataSourceDefinition;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;

public final class MongoDbFactoryConverter implements Converter<DataSourceDefinition, MongoDbFactory> {

    @Override
    public MongoDbFactory convert(DataSourceDefinition source) {
        MongoDynamicClientsDefinition mongoDynamicClientsDefinition = (MongoDynamicClientsDefinition) source;
        return new SimpleMongoClientDbFactory(mongoDynamicClientsDefinition.getUri());
    }
}
