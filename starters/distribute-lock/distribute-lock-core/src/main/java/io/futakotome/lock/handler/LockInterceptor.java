package io.futakotome.lock.handler;

import io.futakotome.lock.DistributeLockExecutor;
import io.futakotome.lock.annotation.Lock;
import io.futakotome.lock.annotation.LockType;
import io.futakotome.lock.DistributeLock;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.function.Supplier;

@Aspect
@RequiredArgsConstructor
public class LockInterceptor extends AbstractLockInterceptor {

    private final @NonNull Supplier<DistributeLock> delegate;
    private final @NonNull Supplier<DistributeLockExecutor<?>> executor;

    @Pointcut(value = "@annotation(io.futakotome.lock.annotation.Lock)")
    public void pointcut() {

    }

    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint) {
        Lock lock = findLock(joinPoint, LockType.LOCK);

    }

    @After("pointcut()")
    public void doAfter(JoinPoint joinPoint) {

    }
}
