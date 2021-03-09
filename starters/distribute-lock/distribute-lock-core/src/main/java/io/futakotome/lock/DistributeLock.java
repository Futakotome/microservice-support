package io.futakotome.lock;

import io.futakotome.lock.annotation.LockType;
import org.aopalliance.intercept.MethodInvocation;

public interface DistributeLock {
    Object invoke(MethodInvocation methodInvocation, LockType lockType, String key) throws Exception;
}
