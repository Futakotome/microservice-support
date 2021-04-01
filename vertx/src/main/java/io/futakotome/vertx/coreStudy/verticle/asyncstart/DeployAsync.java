package io.futakotome.vertx.coreStudy.verticle.asyncstart;

import io.futakotome.vertx.util.Runner;
import io.vertx.core.AbstractVerticle;

public class DeployAsync extends AbstractVerticle {
    public static void main(String... args) {
        Runner.runNormal(DeployAsync.class);
    }

    @Override
    public void start() throws Exception {
        System.out.println("Main verticle has started, let's deploy some others...");

        vertx.deployVerticle("io.futakotome.vertx.coreStudy.verticle.asyncstart.OtherVerticle", result -> {
            if (result.succeeded()) {
                String deploymentId = result.result();
                System.out.println("Other verticle deployed ok, deploymentID = " + deploymentId);

                vertx.undeploy(deploymentId, result2 -> {
                    if (result2.succeeded()) {
                        System.out.println("Undeployed ok!");
                    } else {
                        result2.cause().printStackTrace();
                    }
                });
            } else {
                result.cause().printStackTrace();
            }
        });
    }
}
