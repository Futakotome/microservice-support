package io.futakotome.vertx.coreStudy.http.websocket;

import io.futakotome.vertx.util.Runner;
import io.vertx.core.AbstractVerticle;

public class Server extends AbstractVerticle {
    public static void main(String... args) {
        Runner.runNormal(Server.class);
    }

    @Override
    public void start() throws Exception {
        vertx.createHttpServer()
                .webSocketHandler(ws -> ws.handler(ws::writeBinaryMessage))
                .requestHandler(request -> {
                    if (request.uri().equals("/")) {
                        request.response().sendFile("websocket/ws.html");
                    }
                }).listen(8080);
    }
}
