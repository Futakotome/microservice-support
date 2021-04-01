package io.futakotome.vertx.coreStudy.http.sharing;

import io.vertx.core.AbstractVerticle;

public class HttpServerVerticle extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(request -> {
            request.response().putHeader("content-type", "text/html")
                    .end("<html><body><h1>Hello from " + this + "</h1></body></html>");
        }).listen(8080);
    }
}
