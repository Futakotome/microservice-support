package io.futakotome.MHDatasource.config.mongo;

import com.mongodb.ClientSessionOptions;
import com.mongodb.DB;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoDbFactorySupport;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractRoutingMongoDbFactory extends MongoDbFactorySupport<MongoClient>
        implements DisposableBean, InitializingBean {

    @Nullable
    private Map<Object, Object> targetMongoDbFactory;

    @Nullable
    private Object defaultMongoDbFactory;

    private boolean lenientFallback = true;

    @Nullable
    private Map<Object, MongoDbFactory> resolvedMongoDbFactory;

    @Nullable
    private MongoDbFactory resolvedDefaultMongoDbFactory;

    protected AbstractRoutingMongoDbFactory(MongoClient mongoClient, String databaseName,
                                            boolean mongoInstanceCreated,
                                            PersistenceExceptionTranslator exceptionTranslator) {
        super(mongoClient, databaseName, mongoInstanceCreated, exceptionTranslator);
    }

    public void setTargetMongoDbFactory(Map<Object, Object> targetMongoDbFactory) {
        this.targetMongoDbFactory = targetMongoDbFactory;
    }

    public void setDefaultMongoDbFactory(Object defaultMongoDbFactory) {
        this.defaultMongoDbFactory = defaultMongoDbFactory;
    }

    public void setLenientFallback(boolean lenientFallback) {
        this.lenientFallback = lenientFallback;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.targetMongoDbFactory == null) {
            throw new IllegalArgumentException("Mongo targetMongoDbFactory is required");
        }
        this.resolvedMongoDbFactory = new HashMap<>(this.targetMongoDbFactory.size());
        this.targetMongoDbFactory.forEach((key, value) -> {
            Object lookupKey = resolveSpecifiedLookupKey(key);
            MongoDbFactory mongoDbFactory = resolveSpecifiedMongoDbFactory(value);
            this.resolvedMongoDbFactory.put(lookupKey, mongoDbFactory);
        });
        if (this.defaultMongoDbFactory != null) {
            this.resolvedDefaultMongoDbFactory = resolveSpecifiedMongoDbFactory(this.defaultMongoDbFactory);
        }
    }

    protected MongoDbFactory determineTargetMongoDbFactory() {
        Assert.notNull(this.resolvedMongoDbFactory, "MongoDbFactory router not initialized");
        Object lookupKey = determineCurrentLookupKey();
        MongoDbFactory mongoDbFactory = this.resolvedMongoDbFactory.get(lookupKey);
        if (mongoDbFactory == null && (this.lenientFallback || lookupKey == null)) {
            mongoDbFactory = this.resolvedDefaultMongoDbFactory;
        }
        if (mongoDbFactory == null) {
            throw new IllegalArgumentException("Cannot determine target MongoDbFactory for lookup key [" + lookupKey + "]");
        }
        return mongoDbFactory;
    }


    protected Object resolveSpecifiedLookupKey(Object lookupKey) {
        return lookupKey;
    }

    protected MongoDbFactory resolveSpecifiedMongoDbFactory(Object mongoDbFactory) {
        if (mongoDbFactory instanceof MongoDbFactory) {
            return (MongoDbFactory) mongoDbFactory;
        } else if (mongoDbFactory instanceof String) {
            //todo 各类lookup
            return null;
        } else {
            throw new IllegalArgumentException("Only [org.springframework.data.mongodb.MongoDbFactory] supported: " + mongoDbFactory);
        }
    }

    //----------------------------------------------------------------------------------

    @Override
    public ClientSession getSession(ClientSessionOptions options) {
        return determineTargetMongoDbFactory().getSession(options);
    }

    @Override
    protected MongoDatabase doGetMongoDatabase(String dbName) {
        return determineTargetMongoDbFactory().getDb();
    }

    @Override
    protected void closeClient() {
        getMongoClient().close();
    }

    @Override
    public DB getLegacyDb() {

        throw new UnsupportedOperationException(String.format(
                "%s does not support legacy DBObject API! Please consider using SimpleMongoDbFactory for that purpose.",
                MongoClient.class));
    }

    //----------------------------------------------------------------------------------
    @Nullable
    protected abstract Object determineCurrentLookupKey();
}
