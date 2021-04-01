package io.futakotome.vertx.coreStudy.http.proxy;

import io.futakotome.vertx.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpMethod;

public class Client extends AbstractVerticle {
    public static void main(String... args) {
        Runner.runNormal(Client.class);
    }

    @Override
    public void start() throws Exception {
        HttpClient client = vertx.createHttpClient();
        client.request(HttpMethod.GET, 8080, "localhost", "/")
                .compose(httpClientRequest -> {
                    httpClientRequest.setChunked(true);
                    for (int i = 0; i < 10; i++) {
                        httpClientRequest.write("client-chunk-" + i);
                    }
                    httpClientRequest.end();
                    return httpClientRequest.response().compose(httpClientResponse -> {
                        System.out.println("Got response " + httpClientResponse.statusCode());
                        return httpClientResponse.body();
                    });
                }).onSuccess(body -> System.out.println("Got data " + body.toString()))
                .onFailure(Throwable::printStackTrace);
    }
}
