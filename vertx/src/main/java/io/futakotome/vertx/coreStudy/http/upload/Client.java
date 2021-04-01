package io.futakotome.vertx.coreStudy.http.upload;

import io.futakotome.vertx.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.file.FileSystem;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.http.HttpMethod;

public class Client extends AbstractVerticle {
    public static void main(String... args) {
        Runner.runNormal(Client.class);
    }

    @Override
    public void start() throws Exception {
        vertx.createHttpClient(new HttpClientOptions())
                .request(HttpMethod.PUT, 8080, "localhost", "/")
                .compose(httpClientRequest -> {
                    String fileName = "upload.txt";
                    FileSystem fileSystem = vertx.fileSystem();
                    return fileSystem.props(fileName).compose(fileProps -> {
                        System.out.println("Props is " + fileProps);
                        long size = fileProps.size();
                        httpClientRequest.headers().set("content-length", "" + size);
                        return fileSystem.open(fileName, new OpenOptions());
                    }).compose(asyncFile -> httpClientRequest.send(asyncFile)
                            .map(HttpClientResponse::statusCode));
                }).onSuccess(statusCode -> {
            System.out.println("Response " + statusCode);
        }).onFailure(Throwable::printStackTrace);
    }
}
