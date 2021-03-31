package io.futakotome.vertx.coreStudy.http.simple;

import io.futakotome.vertx.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpMethod;

public class Client extends AbstractVerticle {
    public static void main(String... args) {
        Runner.runNormal(Client.class);
    }

    @Override
    public void start() throws Exception {
        HttpClient httpClient = vertx.createHttpClient();
        httpClient.request(HttpMethod.GET, 8080, "localhost", "/")
                .compose(httpClientRequest -> httpClientRequest.send()
                        .compose(httpClientResponse -> {
                            System.out.println("Got response " + httpClientResponse.statusCode());
                            return httpClientResponse.body();
                        })).onSuccess(event -> System.out.println("Got data " + event.toString()))
                .onFailure(Throwable::printStackTrace);

    }
}
