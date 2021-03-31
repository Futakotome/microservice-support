package io.futakotome.vertx.coreStudy.http.https;

import io.futakotome.vertx.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.net.JksOptions;

public class Server extends AbstractVerticle {

    public static void main(String... args) {
        Runner.runNormal(Server.class);
    }

    @Override
    public void start() throws Exception {

        HttpServer server =
                vertx.createHttpServer(new HttpServerOptions().setSsl(true).setKeyStoreOptions(
                        new JksOptions().setPath("server-keystore.jks").setPassword("wibble")
                ));

        server.requestHandler(req -> {
            req.response().putHeader("content-type", "text/html")
                    .end("<html><body><h1>Hello from ssl!</h1></body></html>");
        }).listen(4443);
    }
}
