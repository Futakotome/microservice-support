package io.futakotome.lock;

import io.futakotome.lock.annotation.LockType;
import org.aopalliance.intercept.MethodInvocation;

public class LocalDistributeLock implements DistributeLock{
    @Override
    public Object invoke(MethodInvocation methodInvocation, LockType lockType, String key) throws Exception {
        return null;
    }
}
