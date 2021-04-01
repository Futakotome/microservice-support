package io.futakotome.vertx.coreStudy.http.simpleformupload;

import io.futakotome.vertx.util.Runner;
import io.vertx.core.AbstractVerticle;

public class UploadServer extends AbstractVerticle {
    public static void main(String... args) {
        Runner.runNormal(UploadServer.class);
    }

    @Override
    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(req -> {
            if (req.uri().equals("/")) {
                req.response().sendFile("upload/upload.html");
            } else if (req.uri().startsWith("/form")) {
                req.setExpectMultipart(true);
                req.uploadHandler(upload -> {
                    upload.streamToFileSystem(upload.filename())
                            .onSuccess(v -> req.response().end("Successfully uploaded to " + upload.filename()))
                            .onFailure(err -> req.response().end("Upload failed"));
                });
            } else {
                req.response()
                        .setStatusCode(404)
                        .end();
            }
        }).listen(8080);
    }
}
