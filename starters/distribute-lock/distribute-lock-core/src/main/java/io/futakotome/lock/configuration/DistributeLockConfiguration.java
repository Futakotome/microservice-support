package io.futakotome.lock.configuration;

import io.futakotome.lock.DistributeLock;
import io.futakotome.lock.DistributeLockExecutor;
import io.futakotome.lock.LocalDistributeLock;
import io.futakotome.lock.LocalDistributeLockExecutor;
import io.futakotome.lock.handler.LockInterceptor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration(proxyBeanMethods = false)
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DistributeLockConfiguration {
    @Bean
    public LockInterceptor lockInterceptor(ObjectProvider<DistributeLock> distributeLock,
                                           ObjectProvider<DistributeLockExecutor<?>> distributeLockExecutor) {
        return new LockInterceptor(
                () -> distributeLock.getIfAvailable(LocalDistributeLock::new)
                , () -> distributeLockExecutor.getIfAvailable(LocalDistributeLockExecutor::new)
        );
    }

}
