package io.futakotome.globalId;

import java.util.UUID;

public class DefaultIdGenerator implements IdGenerator {
    @Override
    public String nextSequenceId(String key) throws Exception {
        return UUID.randomUUID().toString();
    }

}
