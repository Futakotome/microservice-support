package io.futakotome.vertx.coreStudy.http.sharing;

import io.futakotome.vertx.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;

public class Server extends AbstractVerticle {
    public static void main(String... args) {
        Runner.runNormal(Server.class);
    }

    @Override
    public void start() throws Exception {
        vertx.deployVerticle(
                "io.futakotome.vertx.coreStudy.http.sharing.HttpServerVerticle",
                new DeploymentOptions().setInstances(2)
        );
    }
}
