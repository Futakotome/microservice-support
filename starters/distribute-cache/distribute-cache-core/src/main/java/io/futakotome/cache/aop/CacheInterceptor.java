package io.futakotome.cache.aop;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class CacheInterceptor extends CacheInterceptorSupport
        implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheInterceptor.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return null;
    }


}
