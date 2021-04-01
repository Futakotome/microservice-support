package io.futakotome.vertx.coreStudy.http.proxy;

import io.futakotome.vertx.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpServerResponse;

public class Proxy extends AbstractVerticle {
    public static void main(String... args) {
        Runner.runNormal(Proxy.class);
    }

    @Override
    public void start() throws Exception {
        HttpClient client = vertx.createHttpClient(new HttpClientOptions());
        vertx.createHttpServer().requestHandler(request -> {
            System.out.println("Proxying request:" + request.uri());
            request.pause();
            HttpServerResponse response = request.response();
            client.request(request.method(), 8282, "localhost", request.uri())
                    .onSuccess(clientRequest -> {
                        clientRequest.headers().setAll(request.headers());
                        clientRequest.send(request).onSuccess(clientResponse -> {
                            System.out.println("Proxying response: " + clientResponse.statusCode());
                            response.setStatusCode(clientResponse.statusCode());
                            response.headers().setAll(clientResponse.headers());
                            response.send(clientResponse);
                        }).onFailure(event -> {
                            System.out.println("Backend failure");
                            response.setStatusCode(500).end();
                        });
                    }).onFailure(event -> {
                System.out.println("Could not connect to localhost ");
                response.setStatusCode(500).end();
            });
        }).listen(8080);
    }
}
