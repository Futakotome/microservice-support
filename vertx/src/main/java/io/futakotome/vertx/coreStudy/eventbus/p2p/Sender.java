package io.futakotome.vertx.coreStudy.eventbus.p2p;

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
            eventBus.request("ping-address", "ping!", reply -> {
                if (reply.succeeded()) {
                    System.out.println("Received reply " + reply.result());
                } else {
                    System.out.println("No reply");
                }
            });
        });
    }
}
