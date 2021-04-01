package io.futakotome.vertx.coreStudy.eventbus.pubsub;

import io.futakotome.vertx.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

public class Receiver extends AbstractVerticle {
    public static void main(String... args) {
        Runner.runClustered(Receiver.class);
    }

    @Override
    public void start() throws Exception {
        EventBus eventBus = vertx.eventBus();

        eventBus.consumer("news-feed", message -> System.out.println("Received news on consumer 1: " + message.body()));
        eventBus.consumer("news-feed", message -> System.out.println("Received news on consumer 2: " + message.body()));
        eventBus.consumer("news-feed", message -> System.out.println("Received news on consumer 3: " + message.body()));

        System.out.println("Ready!");
    }
}
