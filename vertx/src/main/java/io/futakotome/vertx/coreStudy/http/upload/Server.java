package io.futakotome.vertx.coreStudy.http.upload;

import io.futakotome.vertx.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.streams.Pump;

import java.util.UUID;

public class Server extends AbstractVerticle {
    public static void main(String... args) {
        Runner.runNormal(Server.class);
    }

    @Override
    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(req -> {
            req.pause();
            String filename = UUID.randomUUID() + ".uploaded";
            vertx.fileSystem().open(filename, new OpenOptions(), ares -> {
                AsyncFile file = ares.result();
                Pump pump = Pump.pump(req, file);
                req.endHandler(v1 -> file.close(v2 -> {
                    System.out.println("Uploaded to " + filename);
                    req.response().end();
                }));
                pump.start();
                req.resume();
            });
        }).listen(8080);
//        vertx.createHttpServer().requestHandler(request -> {
//            request.pause();
//            String fileName = UUID.randomUUID() + ".uploaded";
//            vertx.fileSystem().open(fileName, new OpenOptions(), ares -> {
//                AsyncFile file = ares.result();
//                Pump pump = Pump.pump(request, file);
//                request.endHandler(event -> file.close(event1 -> {
//                    System.out.println("Uploaded to " + fileName);
//                    request.response().end();
//                }));
//                pump.start();
//                request.resume();
//            });
//        }).listen(8080);
    }
}
