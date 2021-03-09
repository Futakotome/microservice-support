package io.futakotome.lock.impl;

import io.futakotome.lock.DistributeLockExecutor;
import io.futakotome.lock.annotation.LockType;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.curator.retry.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class ZookeeperDistributeLockExecutor implements DistributeLockExecutor<InterProcessMutex> {
    private final ZookeeperHandler zookeeperHandler = new ZookeeperHandler();
    private volatile Map<String, InterProcessMutex> mutexMap = new ConcurrentHashMap<>();
    private volatile Map<String, InterProcessReadWriteLock> readWriteLockMap = new ConcurrentHashMap<>();
    private final boolean lockCached = true;

    @PreDestroy
    public void destroy() {
        try {
            zookeeperHandler.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public InterProcessMutex tryLock(LockType lockType, String name, String key) throws Exception {
        return null;
    }

    @Override
    public void unlock(InterProcessMutex interProcessMutex) throws Exception {

    }

    static class ZookeeperHandler {
        private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperHandler.class);
        private CuratorFramework curator;

        @Value("${zookeeper.address}")
        private String zkAddress;

        @Value("${zookeeper.retryPolicy}")
        private RetryPolicyEnum retryPolicyEnum;

        @Value("${zookeeper.sessionTimeoutMs}")
        private int sessionTimeoutMs;

        @Value("${zookeeper.connectionTimeoutMs}")
        private int connectionTimeoutMs;

        //============EXPONENTIAL_BACKOFF_RETRY
        private int exponentialBackoffRetryBaseSleepTimeMs;

        private int exponentialBackoffRetryMaxRetries;

        //============BOUNDED_EXPONENTIAL_BACKOFF_RETRY
        private int boundedExponentialBackoffRetryBaseSleepTimeMs;

        private int boundedExponentialBackoffRetryMaxSleepTimeMs;

        private int boundedExponentialBackoffRetryMaxRetries;
        //============RETRY_NTIMES
        private int retryNTimesCount;

        private int retryNTimesSleepMsBetweenRetries;
        //============RETRY_FOREVER
        private int retryForeverRetryIntervalMs;

        //============RETRY_UNTIL_ELAPSED
        private int retryUntilElapsedMaxElapsedTimeMs;

        private int retryUntilElapsedSleepMsBetweenRetries;

        @PostConstruct
        private void initialize() throws Exception {
            try {
                create();
            } catch (Exception e) {
                LOGGER.error("Initialize curator failed.", e);
                e.printStackTrace();
            }
        }

        private void create() throws Exception {
            Assert.notNull(zkAddress, "zookeeper address must not be null");
            RetryPolicy retryPolicy = null;
            switch (retryPolicyEnum) {
                case EXPONENTIAL_BACKOFF_RETRY: {
                    retryPolicy = new ExponentialBackoffRetry(exponentialBackoffRetryBaseSleepTimeMs, exponentialBackoffRetryMaxRetries);
                    break;
                }

                case BOUNDED_EXPONENTIAL_BACKOFF_RETRY: {
                    retryPolicy = new BoundedExponentialBackoffRetry(boundedExponentialBackoffRetryBaseSleepTimeMs, boundedExponentialBackoffRetryMaxSleepTimeMs, boundedExponentialBackoffRetryMaxRetries);
                    break;
                }

                case RETRY_NTIMES: {
                    retryPolicy = new RetryNTimes(retryNTimesCount, retryNTimesSleepMsBetweenRetries);
                    break;
                }
                case RETRY_FOREVER: {
                    retryPolicy = new RetryForever(retryForeverRetryIntervalMs);
                    break;
                }
                case RETRY_UNTIL_ELAPSED: {
                    retryPolicy = new RetryUntilElapsed(retryUntilElapsedMaxElapsedTimeMs, retryUntilElapsedSleepMsBetweenRetries);
                    break;
                }
            }

            if (retryPolicy == null) {
                throw new IllegalStateException("Invalid config value for retryType=" + retryPolicyEnum);
            }
            create(zkAddress, sessionTimeoutMs, connectionTimeoutMs, retryPolicy);

            startAndBlock();
        }


        private void create(String zkAddress, int sessionTimeoutMs, int connectionTimeoutMs, RetryPolicy retryPolicy) {
            LOGGER.info("Starting to initialize Curator.");

            if (curator != null) {
                throw new IllegalStateException("Curator has already start up.");
            }

            curator = CuratorFrameworkFactory.newClient(zkAddress, sessionTimeoutMs, connectionTimeoutMs, retryPolicy);
        }

        private void startAndBlock() throws Exception {
            LOGGER.info("Start and block curator...");

            validateClosedStatus();

            curator.start();
            curator.blockUntilConnected();
        }

        private void startAndBlock(int maxWaitTime, TimeUnit unit) throws Exception {
            LOGGER.info("Start and block curator...");

            validateClosedStatus();

            curator.start();
            curator.blockUntilConnected(maxWaitTime, unit);
        }

        private void close() {
            LOGGER.info("Start to close curator.");

            validateStartedStatus();

            curator.close();
        }
    }
}
