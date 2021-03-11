package io.futakotome.zk;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.ExistsBuilder;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.utils.PathUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ZookeeperClientTemplate implements ZookeeperOperations {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperClientTemplate.class);
    private final @NonNull CuratorFramework curatorFramework;

    @Override
    public void start() {
        LOGGER.info("Zookeeper client[curator] starting with plain.. ");
        validateClosedStatus();
        curatorFramework.start();
    }

    @Override
    public void startAndBlock() throws Exception {
        LOGGER.info("Zookeeper client[curator] starting with block util connected but no params.. ");
        validateClosedStatus();
        curatorFramework.start();
        curatorFramework.blockUntilConnected();
    }

    @Override
    public void startAndBlock(int maxWaitTime, TimeUnit timeUnit) throws Exception {
        LOGGER.info("Zookeeper client[curator] starting with block util connected.. ");
        validateClosedStatus();
        curatorFramework.start();
        curatorFramework.blockUntilConnected(maxWaitTime, timeUnit);
    }

    @Override
    public void close() {
        LOGGER.info("Zookeeper client[curator] closing...");
        validateStartedStatus();
        curatorFramework.close();
    }

    @Override
    public boolean isInitialized() {
        return curatorFramework != null;
    }

    @Override
    public boolean isStarted() {
        return curatorFramework.getState() == CuratorFrameworkState.STARTED;
    }

    @Override
    public void validateStartedStatus() {
        if (curatorFramework == null) {
            throw new ZookeeperClientException("Zookeeper client[curator] must be initialized.");
        }

        if (!isStarted()) {
            throw new ZookeeperClientException("Zookeeper client[curator] has already closed.");
        }
    }

    @Override
    public void validateClosedStatus() {
        if (curatorFramework == null) {
            throw new ZookeeperClientException("Zookeeper client[curator] must be initialized.");
        }
        if (isStarted()) {
            throw new ZookeeperClientException("Zookeeper client[curator] has already started.");
        }
    }

    @Override
    public CuratorFramework getCurator() {
        return this.curatorFramework;
    }

    @Override
    public boolean pathExist(String path) throws Exception {
        return getPathStat(path) != null;
    }

    @Override
    public Stat getPathStat(String path) throws Exception {
        validateStartedStatus();
        PathUtils.validatePath(path);

        ExistsBuilder builder = curatorFramework.checkExists();
        if (builder == null) {
            return null;
        }

        return builder.forPath(path);
    }

    @Override
    public void createPath(String path) throws Exception {
        validateStartedStatus();
        PathUtils.validatePath(path);
        curatorFramework.create().creatingParentsIfNeeded().forPath(path, null);
    }

    @Override
    public void createPath(String path, byte[] data) throws Exception {
        validateStartedStatus();
        PathUtils.validatePath(path);
        curatorFramework.create().creatingParentsIfNeeded().forPath(path, data);
    }

    @Override
    public void createPath(String path, CreateMode mode) throws Exception {
        validateStartedStatus();
        PathUtils.validatePath(path);
        curatorFramework.create().creatingParentsIfNeeded().withMode(mode).forPath(path, null);
    }

    @Override
    public void createPath(String path, byte[] data, CreateMode mode) throws Exception {
        validateStartedStatus();
        PathUtils.validatePath(path);
        curatorFramework.create().creatingParentsIfNeeded().withMode(mode).forPath(path, data);
    }

    @Override
    public void deletePath(String path) throws Exception {
        validateStartedStatus();
        PathUtils.validatePath(path);
        curatorFramework.delete().deletingChildrenIfNeeded().forPath(path);
    }

    @Override
    public List<String> getChildNameList(String path) throws Exception {
        validateStartedStatus();
        PathUtils.validatePath(path);
        return curatorFramework.getChildren().forPath(path);
    }

    @Override
    public List<String> getChildPathList(String path) throws Exception {
        return getChildNameList(path).stream()
                .map(childName -> path + "/" + childName)
                .collect(Collectors.toList());
    }

    @Override
    public String getRootPath(String prefix) {
        return "/" + prefix;
    }

    @Override
    public String getPath(String prefix, String key) {
        return "/" + prefix + "/" + key;
    }

    private static class ZookeeperClientException extends RuntimeException {

        public ZookeeperClientException(String message) {
            super(message);
        }

        public ZookeeperClientException(String message, Throwable e) {
            super(message, e);
        }
    }
}
