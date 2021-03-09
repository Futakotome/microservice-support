package io.futakotome.lock.handler;

import io.futakotome.lock.annotation.Lock;
import io.futakotome.lock.annotation.LockType;
import io.futakotome.lock.annotation.ReadLock;
import io.futakotome.lock.annotation.WriteLock;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public abstract class AbstractLockInterceptor {

    @SuppressWarnings("unchecked")
    public <T extends Annotation> T findLock(JoinPoint joinPoint, LockType lockType) {
        Method interceptedMethod = ((MethodSignature) joinPoint.getSignature()).getMethod();
        if (LockType.LOCK.equals(lockType)) {
            return (T) interceptedMethod.getAnnotation(Lock.class);
        } else if (LockType.READ_LOCK.equals(lockType)) {
            return (T) interceptedMethod.getAnnotation(ReadLock.class);
        } else if (LockType.WRITE_LOCK.equals(lockType)) {
            return (T) interceptedMethod.getAnnotation(WriteLock.class);
        }
        throw new IllegalArgumentException("No suck lock type: " + lockType.name());
    }

}
