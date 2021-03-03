package io.futakotome.events.support;

import io.futakotome.events.EventPublicationRegistry;
import io.futakotome.events.PublicationTargetIdentifier;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.util.Assert;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class CompletionRegisteringBeanPostProcessor implements BeanPostProcessor {
    private final @NonNull Supplier<EventPublicationRegistry> store;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        ProxyCreatingMethodCallback callback = new ProxyCreatingMethodCallback(store, bean);
        ReflectionUtils.doWithMethods(AopProxyUtils.ultimateTargetClass(bean), callback);
        return callback.methodFound ? callback.getBean() : bean;
    }

    private static class ProxyCreatingMethodCallback implements ReflectionUtils.MethodCallback {

        private final CompletionRegisteringMethodInterceptor interceptor;

        @Getter
        private Object bean;

        private boolean methodFound = false;

        public ProxyCreatingMethodCallback(Supplier<EventPublicationRegistry> registry, Object bean) {
            Assert.notNull(registry, "EventPublicationRegistry must not be null!");
            Assert.notNull(bean, "Bean must not be null!");
            this.bean = bean;
            this.interceptor = new CompletionRegisteringMethodInterceptor(registry);
        }

        @Override
        public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
            if (methodFound || !CompletionRegisteringMethodInterceptor.isCompletingMethod(method)) {
                return;
            }
            this.methodFound = true;

            bean = createCompletionRegisteringProxy(bean);
        }

        private Object createCompletionRegisteringProxy(Object bean) {
            if (bean instanceof Advised) {
                Advised advised = (Advised) bean;
                advised.addAdvice(advised.getAdvisors().length, interceptor);
                return bean;
            }

            ProxyFactory factory = new ProxyFactory(bean);
            factory.setProxyTargetClass(true);
            factory.addAdvice(interceptor);

            return factory.getProxy();
        }

        @Slf4j
        @RequiredArgsConstructor
        private static class CompletionRegisteringMethodInterceptor implements MethodInterceptor, Ordered {

            private static final Map<Method, Boolean> COMPLETING_METHOD = new ConcurrentReferenceHashMap<>();

            private final @NonNull Supplier<EventPublicationRegistry> registry;

            @Override
            public Object invoke(MethodInvocation methodInvocation) throws Throwable {

                Object result = null;
                try {
                    result = methodInvocation.proceed();
                } catch (Exception exception) {
                    log.debug("Invocation of listener {} failed,Leaving event publication uncompleted.", methodInvocation.getMethod());
                    throw exception;
                }

                Method method = methodInvocation.getMethod();


                if (!isCompletingMethod(method)) {
                    return result;
                }

                PublicationTargetIdentifier identifier = PublicationTargetIdentifier.forMethod(method);
                registry.get().markCompleted(methodInvocation.getArguments()[0], identifier);
                return result;
            }

            @Override
            public int getOrder() {
                return Ordered.HIGHEST_PRECEDENCE - 10;
            }

            static boolean isCompletingMethod(Method method) {
                Assert.notNull(method, "Method must not be null!");

                return COMPLETING_METHOD.computeIfAbsent(method, function -> {
                    TransactionalEventListener annotation = AnnotatedElementUtils.findMergedAnnotation(method, TransactionalEventListener.class);

                    return annotation != null && annotation.phase().equals(TransactionPhase.AFTER_COMMIT);
                });
            }
        }
    }

}
