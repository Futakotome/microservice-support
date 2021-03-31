package io.futakotome.vertx.coreStudy.http.https;

import io.futakotome.vertx.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpMethod;

public class Client extends AbstractVerticle {
    public static void main(String... args) {
        Runner.runNormal(Client.class);
    }

    @Override
    public void start() throws Exception {
        HttpClientOptions httpClientOptions = new HttpClientOptions().setSsl(true).setTrustAll(true);
        HttpClient client = vertx.createHttpClient(httpClientOptions);
        client.request(HttpMethod.GET, 4443, "localhost", "/")
                .compose(httpClientRequest -> httpClientRequest.send()
                        .compose(httpClientResponse -> {
                            System.out.println("Got response " + httpClientResponse.statusCode());
                            return httpClientResponse.body();
                        })).onSuccess(event -> System.out.println("Got data " + event.toString()))
                .onFailure(Throwable::printStackTrace);
    }
}
