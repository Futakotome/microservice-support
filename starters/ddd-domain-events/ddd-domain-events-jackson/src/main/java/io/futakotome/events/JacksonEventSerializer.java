package io.futakotome.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

@RequiredArgsConstructor
class JacksonEventSerializer implements EventSerializer {

    private final Supplier<ObjectMapper> mapper;

    @Override
    public Object serializer(Object event) {
        try {
            return mapper.get().writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object deserializer(Object serialized, Class<?> type) {
        try {
            return mapper.get().readerFor(type).readValue(serialized.toString());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
