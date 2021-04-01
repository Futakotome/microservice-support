package io.futakotome.vertx.coreStudy.eventbus.p2p;

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

        eventBus.consumer("ping-address", message -> {
            System.out.println("Received message: " + message.body());
            message.reply("pong!");
        });

        System.out.println("Receiver ready!");
    }
}
