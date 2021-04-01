package io.futakotome.vertx.coreStudy.eventbus.messagecodec;

import io.futakotome.vertx.coreStudy.eventbus.messagecodec.util.CustomMessage;
import io.futakotome.vertx.coreStudy.eventbus.messagecodec.util.CustomMessageCodec;
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

        eventBus.registerDefaultCodec(CustomMessage.class, new CustomMessageCodec());

        CustomMessage clusterWideMessage = new CustomMessage(200, "a00000001", "Message sent from publisher!");
        CustomMessage localMessage = new CustomMessage(200, "a0000001", "Local message!");

        vertx.setPeriodic(1000, _id -> {
            eventBus.request("cluster-message-receiver", clusterWideMessage, reply -> {
                if (reply.succeeded()) {
                    CustomMessage replyMessage = (CustomMessage) reply.result().body();
                    System.out.println("Received reply: " + replyMessage.getSummary());
                } else {
                    System.out.println("No reply from cluster receiver");
                }
            });
        });

        vertx.deployVerticle(LocalReceiver.class.getName(), deployResult -> {
            if (deployResult.succeeded()) {
                vertx.setPeriodic(2000, _id -> {
                    eventBus.request("local-message-receiver", localMessage, reply -> {
                        if (reply.succeeded()) {
                            CustomMessage replyMessage = (CustomMessage) reply.result().body();
                            System.out.println("Received local reply: " + replyMessage);
                        } else {
                            System.out.println("No reply from local receiver");
                        }
                    });
                });
            } else {
                deployResult.cause().printStackTrace();
            }
        });
    }
}
