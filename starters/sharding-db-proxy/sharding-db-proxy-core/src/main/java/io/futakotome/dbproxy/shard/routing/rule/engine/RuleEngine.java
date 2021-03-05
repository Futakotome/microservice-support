package io.futakotome.dbproxy.shard.routing.rule.engine;

import java.util.Map;

public interface RuleEngine {

    Object eval(Map<String, Object> val);

}
