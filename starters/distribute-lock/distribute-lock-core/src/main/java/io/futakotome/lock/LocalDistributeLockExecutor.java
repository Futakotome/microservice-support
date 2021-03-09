package io.futakotome.lock;

import io.futakotome.lock.annotation.LockType;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LocalDistributeLockExecutor implements DistributeLockExecutor<Lock> {

    private volatile Map<String, Lock> lockMap = new ConcurrentHashMap<>();
    private volatile Map<String, ReadWriteLock> readWriteLockMap = new ConcurrentHashMap<>();
    private final boolean lockCached = true;

    @Override
    public Lock tryLock(LockType lockType, String name, String key) throws Exception {
        Assert.notNull(name, "Name is must be not null!");
        Assert.notNull(key, "Key is must be not null!");
        Lock lock = getLock(lockType, key);
        boolean acquired = lock.tryLock();
        return acquired ? lock : null;
    }

    @Override
    public void unlock(Lock lock) throws Exception {
        if (lock != null) {
            if (lock instanceof ReentrantLock) {
                ReentrantLock reentrantLock = (ReentrantLock) lock;
                if (reentrantLock.isLocked()) {
                    reentrantLock.unlock();
                }
            } else {
                lock.unlock();
            }
        }
    }

    private Lock getLock(LockType lockType, String key) {
        if (lockCached) {
            return getCachedLock(lockType, key);
        } else {
            return getNewLock(lockType, key);
        }
    }

    private Lock getCachedLock(LockType lockType, String key) {
        String newKey = lockType + "-" + key;
        Lock lock = lockMap.get(newKey);
        if (lock == null) {
            Lock newLock = getNewLock(lockType, key);
            lock = lockMap.putIfAbsent(newKey, newLock);
            if (lock == null) {
                lock = newLock;
            }
        }
        return lock;
    }

    private Lock getNewLock(LockType lockType, String key) {
        switch (lockType) {
            case LOCK:
                return new ReentrantLock();
            case READ_LOCK:
                return getCachedReadWriteLock(lockType, key).readLock();
            case WRITE_LOCK:
                return getCachedReadWriteLock(lockType, key).writeLock();
        }
        throw new IllegalArgumentException("Invalid lock type for " + lockType);
    }

    private ReadWriteLock getCachedReadWriteLock(LockType lockType, String key) {
        String newKey = lockType + "-" + key;
        ReadWriteLock readWriteLock = readWriteLockMap.get(key);
        if (readWriteLock == null) {
            ReadWriteLock newReadWriteLock = new ReentrantReadWriteLock();
            readWriteLock = readWriteLockMap.putIfAbsent(newKey, newReadWriteLock);
            if (readWriteLock == null) {
                readWriteLock = newReadWriteLock;
            }
        }
        return readWriteLock;
    }
}
