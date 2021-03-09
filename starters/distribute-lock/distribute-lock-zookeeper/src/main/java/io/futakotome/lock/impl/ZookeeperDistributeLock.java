package io.futakotome.lock.impl;

import io.futakotome.lock.DistributeLock;
import io.futakotome.lock.annotation.LockType;
import org.aopalliance.intercept.MethodInvocation;

public class ZookeeperDistributeLock implements DistributeLock {
    @Override
    public Object invoke(MethodInvocation methodInvocation, LockType lockType, String key) throws Exception {
        return null;
    }
}
