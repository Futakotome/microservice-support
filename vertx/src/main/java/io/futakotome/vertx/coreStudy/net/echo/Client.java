package io.futakotome.vertx.coreStudy.net.echo;

import io.futakotome.vertx.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.net.NetSocket;

public class Client extends AbstractVerticle {
    public static void main(String... args) {
        Runner.runNormal(Client.class);
    }

    @Override
    public void start() throws Exception {
        vertx.createNetClient().connect(1234, "localhost", ar -> {
            if (ar.succeeded()) {
                NetSocket netSocket = ar.result();
                netSocket.handler(buffer -> {
                    System.out.println("Net client receiving: " + buffer.toString("UTF-8"));
                });
                for (int i = 0; i < 10; i++) {
                    String str = "hello " + i + "\n";
                    System.out.println("Net client sending: " + str);
                    netSocket.write(str);
                }
            } else {
                ar.cause().printStackTrace();
            }
        });
    }
}
