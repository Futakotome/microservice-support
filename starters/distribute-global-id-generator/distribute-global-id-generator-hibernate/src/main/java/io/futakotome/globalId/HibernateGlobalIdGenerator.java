package io.futakotome.globalId;

import io.futakotome.globalId.config.IdGeneratorConfiguration;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
public class HibernateGlobalIdGenerator implements Configurable,
        IdentifierGenerator, IdGenerator {

    private final @NonNull Supplier<IdGeneratorConfiguration> idGeneratorConfiguration;

    @Override
    public void configure(Type type, Properties properties, ServiceRegistry serviceRegistry) throws MappingException {

    }

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return null;
    }

    @Override
    public String nextSequenceId() throws Exception {
        return idGeneratorConfiguration
                .get()
                .idGenerator()
                .nextSequenceId();
    }
}