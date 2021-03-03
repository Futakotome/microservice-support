package io.futakotome.events;

public interface EventSerializer {
    Object serializer(Object event);

    Object deserializer(Object serialized, Class<?> type);
}
