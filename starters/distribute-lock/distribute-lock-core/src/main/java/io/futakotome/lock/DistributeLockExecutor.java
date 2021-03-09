package io.futakotome.lock;

import io.futakotome.lock.annotation.LockType;

public interface DistributeLockExecutor<T> {

    T tryLock(LockType lockType, String name, String key) throws Exception;

    void unlock(T t) throws Exception;

}
