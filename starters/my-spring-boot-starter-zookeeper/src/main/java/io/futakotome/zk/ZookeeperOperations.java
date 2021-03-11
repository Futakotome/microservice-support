package io.futakotome.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.TimeUnit;

public interface ZookeeperOperations {

    void start();

    void startAndBlock() throws Exception;

    void startAndBlock(int maxWaitTime, TimeUnit timeUnit) throws Exception;

    void close();

    boolean isInitialized();

    boolean isStarted();

    void validateStartedStatus();

    void validateClosedStatus();

    CuratorFramework getCurator();

    boolean pathExist(String path) throws Exception;

    Stat getPathStat(String path) throws Exception;

    void createPath(String path) throws Exception;

    void createPath(String path, byte[] data) throws Exception;

    void createPath(String path, CreateMode mode) throws Exception;

    void createPath(String path, byte[] data, CreateMode mode) throws Exception;

    void deletePath(String path) throws Exception;

    List<String> getChildNameList(String path) throws Exception;

    List<String> getChildPathList(String path) throws Exception;

    String getRootPath(String prefix);

    String getPath(String prefix, String key);
}
