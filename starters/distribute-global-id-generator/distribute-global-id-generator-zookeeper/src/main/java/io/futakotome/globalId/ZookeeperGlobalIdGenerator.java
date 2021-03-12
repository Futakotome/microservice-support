package io.futakotome.globalId;

import io.futakotome.zk.ZookeeperClientTemplate;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.common.PathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.util.Assert;

import java.util.function.Supplier;

public class ZookeeperGlobalIdGenerator implements IdGenerator, DisposableBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperGlobalIdGenerator.class);

    private final Supplier<ZookeeperClientTemplate> zookeeperClientTemplate;

    public ZookeeperGlobalIdGenerator(Supplier<ZookeeperClientTemplate> template) {
        this.zookeeperClientTemplate = template;
    }

    private static final String PREFIX = "/zk/globalId";

    //todo 并发场景应该加锁
    @Override
    public String nextSequenceId(String key) throws Exception {
        Assert.notNull(key, "Key must not be null.");
        zookeeperClientTemplate.get().validateStartedStatus();
        PathUtils.validatePath(pathConnect(key));
        if (!zookeeperClientTemplate.get().pathExist(pathConnect(key))) {
            String createdPath = zookeeperClientTemplate.get().createPath(pathConnect(key), CreateMode.PERSISTENT);
            LOGGER.info("Created znode : {}", createdPath);
        }
        int nextSequenceId = zookeeperClientTemplate.get().withZnodeVersion(pathConnect(key));
        return String.valueOf(nextSequenceId);
    }

    private String pathConnect(String key) {
        return ZookeeperGlobalIdGenerator.PREFIX + "/" + key;
    }

    @Override
    public void destroy() throws Exception {
        try {
            zookeeperClientTemplate.get().close();
        } catch (Exception e) {
            throw new ZookeeperGlobalIdGeneratorException(e.getMessage(), e);
        }
    }

    static class ZookeeperGlobalIdGeneratorException extends RuntimeException {
        public ZookeeperGlobalIdGeneratorException(String message, Throwable throwable) {
            super(message, throwable);
        }
    }
}
