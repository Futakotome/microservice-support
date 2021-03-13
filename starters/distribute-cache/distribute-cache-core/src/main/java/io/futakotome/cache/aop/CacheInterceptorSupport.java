package io.futakotome.cache.aop;

import io.futakotome.cache.AbstractCacheOperation;
import io.futakotome.cache.CacheOperation;
import io.futakotome.cache.annotation.CacheEvict;
import io.futakotome.cache.annotation.CachePut;
import io.futakotome.cache.annotation.Cacheable;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

public abstract class CacheInterceptorSupport extends AbstractCacheOperation implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Cacheable cacheable = getCacheableAnnotation(invocation);
        if (cacheable != null) {
            return doGet(invocation, cacheable.name(), cacheable.key(), cacheable.expire());
        }

        CachePut cachePut = getCachePutAnnotation(invocation);
        if (cachePut != null) {
            return doPut(invocation, cachePut.name(), cachePut.key(), cachePut.expire());
        }

        CacheEvict cacheEvict = getCacheEvictAnnotation(invocation);
        if (cacheEvict != null) {
            return doEvict(invocation, cacheEvict.name(), cacheEvict.key(), cacheEvict.allEntries(), cacheEvict.beforeInvocation())
        }
        return invocation.proceed();
    }

    private Cacheable getCacheableAnnotation(MethodInvocation invocation) {
        Method method = invocation.getMethod();
        return method.isAnnotationPresent(Cacheable.class) ?
                method.getAnnotation(Cacheable.class) : null;
    }

    private CachePut getCachePutAnnotation(MethodInvocation invocation) {
        Method method = invocation.getMethod();
        return method.isAnnotationPresent(CachePut.class) ?
                method.getAnnotation(CachePut.class) : null;
    }

    private CacheEvict getCacheEvictAnnotation(MethodInvocation invocation) {
        Method method = invocation.getMethod();
        return method.isAnnotationPresent(CacheEvict.class) ?
                method.getAnnotation(CacheEvict.class) : null;
    }
}
