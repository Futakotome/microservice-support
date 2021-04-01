package io.futakotome.vertx.coreStudy.http.sharing;

import io.futakotome.vertx.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.http.HttpMethod;

public class Client extends AbstractVerticle {
    public static void main(String... args) {
        Runner.runNormal(Client.class);
    }

    @Override
    public void start() throws Exception {
        vertx.setPeriodic(1000, event -> {
            HttpClient client = vertx.createHttpClient();
            client.request(HttpMethod.GET, 8080, "localhost", "/")
                    .compose(httpClientRequest -> httpClientRequest.send()
                            .compose(HttpClientResponse::body))
                    .onSuccess(body -> System.out.println(body.toString()))
                    .onFailure(Throwable::printStackTrace);
        });
    }
}
