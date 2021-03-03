package io.futakotome.events.support;

import io.futakotome.events.EventPublication;
import io.futakotome.events.EventPublicationRegistry;
import io.futakotome.events.PublicationTargetIdentifier;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.AbstractApplicationEventMulticaster;
import org.springframework.context.event.ApplicationListenerMethodAdapter;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class PersistentApplicationEventMulticaster extends AbstractApplicationEventMulticaster
        implements SmartInitializingSingleton {

    private final @NonNull Supplier<EventPublicationRegistry> registry;

    private static final Map<Class<?>, Boolean> TX_EVENT_LISTENERS = new ConcurrentReferenceHashMap<>();
    private static final Field LISTENER_METHOD_FIELD;


    static {
        LISTENER_METHOD_FIELD = ReflectionUtils.findField(ApplicationListenerMethodAdapter.class, "method");
        ReflectionUtils.makeAccessible(Objects.requireNonNull(LISTENER_METHOD_FIELD));
    }

    @Override
    public void afterSingletonsInstantiated() {
        for (EventPublication publication : registry.get().findIncompletePublications()) {
            invokeTargetListener(publication);
        }
    }

    @Override
    public void multicastEvent(ApplicationEvent event) {
        multicastEvent(event, ResolvableType.forInstance(event));
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void multicastEvent(ApplicationEvent event, ResolvableType eventType) {
        ResolvableType type = eventType == null ? ResolvableType.forInstance(event) : eventType;
        Collection<ApplicationListener<?>> listeners = getApplicationListeners(event, type);
        if (listeners.isEmpty()) {
            return;
        }
        List<ApplicationListener<?>> transactionalListeners = listeners.stream()
                .filter(PersistentApplicationEventMulticaster::isTransactionalApplicationEventListener)
                .collect(Collectors.toList());

        if (!transactionalListeners.isEmpty()) {
            Object eventToPersist = getEventToPersist(event);

            registry.get().store(eventToPersist, transactionalListeners);
        }

        for (ApplicationListener listener : listeners) {
            listener.onApplicationEvent(event);
        }
    }

    @SuppressWarnings({"unckecked", "rawtypes"})
    private void invokeTargetListener(EventPublication publication) {
        for (ApplicationListener listener : getApplicationListeners()) {
            if (publication.isIdentifierBy(PublicationTargetIdentifier.forListener(listener))) {
                executeListenerWithCompletion(publication, listener);
                return;
            }
        }

        log.debug("Listener {} not found ! ", publication.getTargetIdentifier());
    }

    private void executeListenerWithCompletion(EventPublication publication,
                                               ApplicationListener<ApplicationEvent> listener) {
        try {
            listener.onApplicationEvent(publication.getApplicationEvent());
            registry.get().markCompleted(publication);
        } catch (Exception e) {
            log.debug("Publication {} not completed due to exception {}.", publication.getTargetIdentifier(), e.getMessage());
        }
    }

    private static boolean isTransactionalApplicationEventListener(ApplicationListener<?> listener) {
        Class<?> targetClass = AopUtils.getTargetClass(listener);
        return TX_EVENT_LISTENERS.computeIfAbsent(targetClass, function -> {
            if (!ApplicationListenerMethodAdapter.class.isAssignableFrom(targetClass)) {
                return false;
            }
            Method method = (Method) ReflectionUtils.getField(LISTENER_METHOD_FIELD, listener);

            return AnnotatedElementUtils.hasAnnotation(Objects.requireNonNull(method), TransactionalEventListener.class);
        });
    }

    private static Object getEventToPersist(ApplicationEvent event) {
        return PayloadApplicationEvent.class.isInstance(event)
                ? ((PayloadApplicationEvent<?>) event).getPayload()
                : event;
    }
}
