package io.futakotome.vertx.util;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public class Runner {
    private static final String CORE_DIR = "core";
    private static final String CORE_JAVA_DIR = CORE_DIR + "/src/main/java/";

    public static void runClustered(Class<?> clz) {
        run(CORE_JAVA_DIR, clz.getName(), new VertxOptions(), null, true);
    }

    public static void runClustered(Class<?> clz, VertxOptions vertxOptions) {
        run(CORE_JAVA_DIR, clz.getName(), vertxOptions, null, true);
    }

    public static void runNormal(Class<?> clz) {
        run(CORE_JAVA_DIR, clz.getName(), new VertxOptions(), null, false);
    }

    public static void runNormal(Class<?> clz, DeploymentOptions deploymentOptions) {
        run(CORE_JAVA_DIR, clz.getName(), new VertxOptions(), deploymentOptions, false);
    }

    public static void runNormal(String dir, Class<?> clz, VertxOptions vertxOptions, DeploymentOptions deploymentOptions, boolean clustered) {
        run(dir + clz.getPackage().getName().replace(".", "/"), clz.getName(), vertxOptions, deploymentOptions, clustered);
    }

    public static void run(String dir,
                           String verticleID,
                           VertxOptions options,
                           DeploymentOptions deploymentOptions,
                           boolean clustered) {
        if (options == null) {
            options = new VertxOptions();
        }
        try {
            File current = new File(".").getCanonicalFile();
            if (dir.startsWith(current.getName()) && !dir.equals(current.getName())) {
                dir = dir.substring(current.getName().length() + 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.setProperty("vertx.cwd", dir);
        Consumer<Vertx> runner = vertx -> {
            try {
                if (deploymentOptions != null) {
                    vertx.deployVerticle(verticleID, deploymentOptions);
                } else {
                    vertx.deployVerticle(verticleID);
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        };
        if (clustered) {
            Vertx.clusteredVertx(options, vertxAsyncResult -> {
                if (vertxAsyncResult.succeeded()) {
                    Vertx vertx = vertxAsyncResult.result();
                    runner.accept(vertx);
                } else {
                    vertxAsyncResult.cause().printStackTrace();
                }
            });
        } else {
            Vertx vertx = Vertx.vertx(options);
            runner.accept(vertx);
        }
    }
}
