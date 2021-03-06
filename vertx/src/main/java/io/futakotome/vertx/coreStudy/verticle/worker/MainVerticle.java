package io.futakotome.vertx.coreStudy.verticle.worker;

import io.futakotome.vertx.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;

public class MainVerticle extends AbstractVerticle {
    public static void main(String... args) {
        Runner.runNormal(MainVerticle.class);
    }

    @Override
    public void start() throws Exception {
        System.out.println("[Main] Running in " + Thread.currentThread().getName());

        vertx.deployVerticle("io.futakotome.vertx.coreStudy.verticle.worker.WorkerVerticle",
                new DeploymentOptions().setWorker(true));

        vertx.eventBus().request(
                "sample.data",
                "hello vert.x",
                r -> {
                    System.out.println("[Main] Receiving reply ' " + r.result().body()
                            + "' in " + Thread.currentThread().getName());
                }
        );

    }
}
