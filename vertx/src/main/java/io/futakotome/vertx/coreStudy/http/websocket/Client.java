package io.futakotome.vertx.coreStudy.http.websocket;

import io.futakotome.vertx.util.Runner;
import io.netty.handler.codec.http.HttpClientCodec;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;

public class Client extends AbstractVerticle {
    public static void main(String... args) {
        Runner.runNormal(Client.class);
    }

    @Override
    public void start() throws Exception {
        HttpClient client = vertx.createHttpClient();

        client.webSocket(8080, "localhost", "/")
                .onSuccess(webSocket -> {
                    webSocket.handler(data -> {
                        System.out.println("Received data " + data.toString());
                        client.close();
                    });
                    webSocket.writeBinaryMessage(Buffer.buffer("Hello world"));
                }).onFailure(Throwable::printStackTrace);
    }
}
