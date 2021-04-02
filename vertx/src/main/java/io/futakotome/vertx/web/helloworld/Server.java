package io.futakotome.vertx.web.helloworld;

import io.futakotome.vertx.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

public class Server extends AbstractVerticle {
    public static void main(String... args) {
        Runner.runNormal(Server.class);
    }

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        router.route().handler(routingContext -> {
            routingContext.response().putHeader("content-type", "text/html").end("Hello world");

        });
        vertx.createHttpServer().requestHandler(router).listen(8080);
    }
}
