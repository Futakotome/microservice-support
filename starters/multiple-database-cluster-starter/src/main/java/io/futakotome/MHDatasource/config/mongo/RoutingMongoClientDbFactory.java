package io.futakotome.MHDatasource.config.mongo;

import io.futakotome.MHDatasource.config.DynamicDatasourceContextHolder;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoExceptionTranslator;

@Slf4j
public class RoutingMongoClientDbFactory extends AbstractRoutingMongoDbFactory {

    public RoutingMongoClientDbFactory(String connectionString) {
        this(new ConnectionString(connectionString));
    }

    public RoutingMongoClientDbFactory(ConnectionString connectionString) {
        this(MongoClients.create(connectionString), connectionString.getDatabase(), true);
    }

    public RoutingMongoClientDbFactory(MongoClient mongoClient, String databaseName, boolean mongoInstanceCreated) {
        super(mongoClient, databaseName, mongoInstanceCreated, new MongoExceptionTranslator());
    }


    @Override
    protected Object determineCurrentLookupKey() {
        String clientKey = DynamicDatasourceContextHolder.getDatasourceKey();
        if (clientKey != null) {
            log.info("线程[{}],MongoDb切换到工厂:[{}]", Thread.currentThread().getId(), clientKey);
        }
        return clientKey;
    }


}
