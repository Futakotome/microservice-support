package io.futakotome.dbproxy.shard.routing.rule.dimension;

import io.futakotome.dbproxy.shard.routing.rule.ShardEvalContext;
import io.futakotome.dbproxy.shard.routing.rule.ShardEvalResult;

import java.util.Map;
import java.util.Set;

public interface DimensionRule {

    ShardEvalResult eval(ShardEvalContext context);

    Map<String, Set<String>> getAllDatabaseAndContainer();

}
