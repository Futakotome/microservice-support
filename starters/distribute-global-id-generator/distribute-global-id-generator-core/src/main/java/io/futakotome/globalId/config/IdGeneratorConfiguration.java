package io.futakotome.globalId.config;

import io.futakotome.globalId.IdGenerator;

import java.util.function.Supplier;

public class IdGeneratorConfiguration {

    private final Supplier<IdGenerator> idGenerator;

    public IdGeneratorConfiguration(Supplier<IdGenerator> idGenerator) {
        this.idGenerator = idGenerator;
    }

    public IdGenerator idGenerator() {
        return idGenerator.get();
    }

}
