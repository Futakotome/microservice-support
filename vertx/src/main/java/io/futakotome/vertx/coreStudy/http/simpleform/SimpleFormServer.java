package io.futakotome.vertx.coreStudy.http.simpleform;

import io.futakotome.vertx.util.Runner;
import io.vertx.core.AbstractVerticle;

public class SimpleFormServer extends AbstractVerticle {

    public static void main(String... args) {
        Runner.runNormal(SimpleFormServer.class);
    }

    @Override
    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(request -> {
            if (request.uri().equals("/")) {
                request.response().sendFile("simple/index.html");
            } else if (request.uri().startsWith("/form")) {
                request.response().setChunked(true);
                request.setExpectMultipart(true);
                request.endHandler(event -> {
                    for (String attr : request.formAttributes().names()) {
                        request.response().write("Got attr " + attr + " : " + request.formAttributes().get(attr) + "\n");
                    }
                    request.response().end();
                });

            } else {
                request.response().setStatusCode(404).end();
            }
        }).listen(8080);
    }
}
