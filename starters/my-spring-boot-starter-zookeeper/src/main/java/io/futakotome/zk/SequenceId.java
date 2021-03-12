package io.futakotome.zk;

public interface SequenceId {

    int withPersistentSequentialZnode(String path) throws Exception;

    int withZnodeVersion(String path) throws Exception;
}
