package io.futakotome.globalId;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;
import java.util.function.Supplier;

public class HibernateGlobalIdGenerator implements Configurable,
        IdentifierGenerator, IdGenerator {

    private final Supplier<IdGenerator> idGenerator;

    public HibernateGlobalIdGenerator(Supplier<IdGenerator> idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public void configure(Type type, Properties properties, ServiceRegistry serviceRegistry) throws MappingException {

    }

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        try {
            return nextSequenceId("hibernate");
        } catch (Exception e) {
            throw new HibernateException(e);
        }
    }

    @Override
    public String nextSequenceId(String key) throws Exception {
        return idGenerator
                .get()
                .nextSequenceId(key);
    }

}
