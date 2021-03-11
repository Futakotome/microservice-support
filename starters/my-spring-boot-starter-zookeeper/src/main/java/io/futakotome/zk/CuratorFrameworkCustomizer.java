package io.futakotome.zk;

import org.apache.curator.framework.CuratorFrameworkFactory;

@FunctionalInterface
public interface CuratorFrameworkCustomizer {
    void customize(CuratorFrameworkFactory.Builder builder);
}
