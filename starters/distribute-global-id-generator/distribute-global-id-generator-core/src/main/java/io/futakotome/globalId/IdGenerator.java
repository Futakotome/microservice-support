package io.futakotome.globalId;

public interface IdGenerator {
    String nextSequenceId(String key) throws Exception;

}
