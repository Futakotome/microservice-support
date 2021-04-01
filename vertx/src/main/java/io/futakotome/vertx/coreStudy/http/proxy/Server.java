package io.futakotome.vertx.coreStudy.http.proxy;

import io.futakotome.vertx.util.Runner;
import io.vertx.core.AbstractVerticle;

public class Server extends AbstractVerticle {
    public static void main(String... args) {
        Runner.runNormal(Server.class);
    }

    @Override
    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(request -> {
            System.out.println("Got request " + request.uri());
            for (String name : request.headers().names()) {
                System.out.println(name + " : " + request.headers().get(name));
            }
            request.handler(data -> System.out.println("Got data " + data.toString()));
            request.endHandler(event -> {
                request.response().setChunked(true);
                for (int i = 0; i < 10; i++) {
                    request.response().write("server-data-chunk-" + i);
                }
                request.response().end();
            });

        }).listen(8282);
    }
}
