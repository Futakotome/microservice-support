package io.futakotome.vertx.coreStudy.verticle.deploy;

import io.futakotome.vertx.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;

public class DepolyVerticle extends AbstractVerticle {
    public static void main(String... args) {
        Runner.runNormal(DepolyVerticle.class);
    }

    @Override
    public void start() throws Exception {
        System.out.println("Main verticle has started, let's deploy some others...");

        vertx.deployVerticle("io.futakotome.vertx.coreStudy.verticle.deploy.OtherVerticle");

        vertx.deployVerticle("io.futakotome.vertx.coreStudy.verticle.deploy.OtherVerticle", result -> {
            if (result.succeeded()) {
                String deploymentID = result.result();
                System.out.println("Other verticle deployed ok, deploymentID = " + deploymentID);
                vertx.undeploy(deploymentID, res2 -> {
                    if (res2.succeeded()) {
                        System.out.println("Undeployed ok!");
                    } else {
                        res2.cause().printStackTrace();
                    }
                });
            } else {
                result.cause().printStackTrace();
            }
        });
        JsonObject config = new JsonObject().put("foo", "bar");
        vertx.deployVerticle("io.futakotome.vertx.coreStudy.verticle.deploy.OtherVerticle", new DeploymentOptions().setConfig(config));
        vertx.deployVerticle("io.futakotome.vertx.coreStudy.verticle.deploy.OtherVerticle", new DeploymentOptions().setInstances(10));
        vertx.deployVerticle("io.futakotome.vertx.coreStudy.verticle.deploy.OtherVerticle", new DeploymentOptions().setWorker(true));
    }
}
