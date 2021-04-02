package io.futakotome.vertx.coreStudy.net.echo;

import io.futakotome.vertx.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.streams.Pump;

public class Server extends AbstractVerticle {
    public static void main(String... args) {
        Runner.runNormal(Server.class);
    }

    @Override
    public void start() throws Exception {
        vertx.createNetServer().connectHandler(sock -> {
            Pump.pump(sock, sock).start();
        }).listen(1234);
        System.out.println("Echo server is now listening");
    }
}
