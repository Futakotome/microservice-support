package io.futakotome.vertx.coreStudy.http.simple;

import io.futakotome.vertx.util.Runner;
import io.vertx.core.AbstractVerticle;

public class Server extends AbstractVerticle {

    public static void main(String... args) {
        Runner.runNormal(Server.class);
    }

    @Override
    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(httpServerRequest -> {
            httpServerRequest.response()
                    .putHeader("content-type", "text/html")
                    .end("<html><body><h1>hello world</h1></body></html>");
        }).listen(8080);
    }
}
