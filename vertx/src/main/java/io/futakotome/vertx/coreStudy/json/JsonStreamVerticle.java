package io.futakotome.vertx.coreStudy.json;

import io.futakotome.vertx.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.parsetools.JsonEventType;
import io.vertx.core.parsetools.JsonParser;

import java.util.concurrent.atomic.AtomicInteger;

public class JsonStreamVerticle extends AbstractVerticle {
    public static void main(String... args) {
        Runner.runNormal(JsonStreamVerticle.class);
    }

    @Override
    public void start() throws Exception {
        vertx.fileSystem().open("large.json", new OpenOptions(), ar -> {
            if (ar.succeeded()) {
                AsyncFile asyncFile = ar.result();
                AtomicInteger count = new AtomicInteger();
                JsonParser jsonParser = JsonParser.newParser(asyncFile);
                jsonParser.objectValueMode()
                        .exceptionHandler(event -> {
                            event.printStackTrace();
                            asyncFile.close();
                        })
                        .endHandler(event -> {
                            System.out.println("Done!");
                            asyncFile.close();
                        }).handler(event -> {
                    if (event.type() == JsonEventType.VALUE) {
                        DataPoint dataPoint = event.mapTo(DataPoint.class);
                        if (count.incrementAndGet() % 100 == 0) {
                            System.out.println("Datapoint = " + dataPoint);
                        }
                    }
                });
            } else {
                ar.cause().printStackTrace();
            }
        });
    }
}
