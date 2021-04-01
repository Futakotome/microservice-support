package io.futakotome.vertx.coreStudy.http.proxyconnect;

import io.futakotome.vertx.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.net.ProxyOptions;
import io.vertx.core.net.ProxyType;

public class Client extends AbstractVerticle {
    public static void main(String... args) {
        Runner.runNormal(Client.class);
    }

    @Override
    public void start() throws Exception {
        HttpClientOptions clientOptions = new HttpClientOptions()
                .setSsl(true)
                .setTrustAll(true)
                .setVerifyHost(false)
                .setProxyOptions(new ProxyOptions()
                        .setType(ProxyType.HTTP)
                        .setHost("localhost")
                        .setPort(8080));
        HttpClient client = vertx.createHttpClient(clientOptions);
        client.request(HttpMethod.GET, 8080, "localhost", "/")
                .compose(httpClientRequest -> {
                    httpClientRequest.setChunked(true);
                    for (int i = 0; i < 10; i++) {
                        httpClientRequest.write("client-chunk-" + i);
                    }
                    httpClientRequest.end();
                    return httpClientRequest.response().compose(httpClientResponse -> {
                        System.out.println("Gor response " + httpClientResponse.statusCode());
                        return httpClientResponse.body();
                    });
                }).onSuccess(body -> System.out.println("Got data " + body.toString()))
                .onFailure(Throwable::printStackTrace);
    }
}
