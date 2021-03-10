package io.futakotome.globalId.config;

import io.futakotome.globalId.IdGenerator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class IdGeneratorConfiguration {

    private final @NonNull Supplier<IdGenerator> idGenerator;


    public IdGenerator idGenerator() {
        return idGenerator.get();
    }

}
