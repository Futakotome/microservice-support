package io.futakotome.vertx.coreStudy.eventbus.pubsub;

import io.futakotome.vertx.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

public class Sender extends AbstractVerticle {
    public static void main(String... args) {
        Runner.runClustered(Sender.class);
    }

    @Override
    public void start() throws Exception {
        EventBus eventBus = vertx.eventBus();
        vertx.setPeriodic(1000, event -> {
            eventBus.publish("news-feed", "Some message!");
        });
    }
}
