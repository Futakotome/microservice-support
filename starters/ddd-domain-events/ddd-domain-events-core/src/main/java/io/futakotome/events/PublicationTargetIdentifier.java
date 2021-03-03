package io.futakotome.events;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.context.event.ApplicationListenerMethodAdapter;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Value
@RequiredArgsConstructor(staticName = "of")
public class PublicationTargetIdentifier {
    private static Field LISTENER_METHOD_FIELD;
    private static Map<Object, PublicationTargetIdentifier> IDENTIFIERS = new ConcurrentHashMap<>();

    static {
        LISTENER_METHOD_FIELD = ReflectionUtils.findField(ApplicationListenerMethodAdapter.class, "method");
        Objects.requireNonNull(LISTENER_METHOD_FIELD).setAccessible(true);
    }

    String value;

    public static PublicationTargetIdentifier forMethod(Method method) {
        return IDENTIFIERS.computeIfAbsent(method, function -> {
            String typeName = ClassUtils.getUserClass(method.getDeclaringClass()).getName();
            String methodName = method.getName();
            String parametersType = StringUtils.arrayToDelimitedString(method.getParameterTypes(), ",");
            return PublicationTargetIdentifier.of(String.format("%s.%s(%s)", typeName, methodName, parametersType));
        });
    }

    public static PublicationTargetIdentifier forListener(Object listener) {
        if (listener instanceof ApplicationListenerMethodAdapter) {
            Method method = (Method) ReflectionUtils.getField(LISTENER_METHOD_FIELD, listener);
            return PublicationTargetIdentifier.forMethod(method);
        }
        throw new IllegalArgumentException("Unsupported listener impl");
    }

    @Override
    public String toString() {
        return value;
    }
}
