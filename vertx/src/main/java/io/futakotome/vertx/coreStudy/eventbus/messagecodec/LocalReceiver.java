package io.futakotome.vertx.coreStudy.eventbus.messagecodec;

import io.futakotome.vertx.coreStudy.eventbus.messagecodec.util.CustomMessage;
import io.futakotome.vertx.coreStudy.eventbus.messagecodec.util.CustomMessageCodec;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

public class LocalReceiver extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        EventBus eventBus = vertx.eventBus();

        eventBus.consumer("local-message-receiver", message -> {
            CustomMessage customMessage = (CustomMessage) message.body();
            System.out.println("Custom message received: " + customMessage.getSummary());

            CustomMessage replyMessage = new CustomMessage(200, "a00000002", "Message sent from local receiver!");
            message.reply(replyMessage);
        });
    }
}
