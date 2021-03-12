package io.futakotome.lock.impl;

import io.futakotome.lock.DistributeLockExecutor;
import io.futakotome.lock.annotation.LockType;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ZookeeperDistributeLockExecutor implements DistributeLockExecutor<InterProcessMutex> {
    private volatile Map<String, InterProcessMutex> mutexMap = new ConcurrentHashMap<>();
    private volatile Map<String, InterProcessReadWriteLock> readWriteLockMap = new ConcurrentHashMap<>();
    private final boolean lockCached = true;

    @Override
    public InterProcessMutex tryLock(LockType lockType, String name, String key) throws Exception {
        return null;
    }

    @Override
    public void unlock(InterProcessMutex interProcessMutex) throws Exception {

    }


}
