package io.futakotome.vertx.coreStudy.http.future;

import io.futakotome.vertx.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;

public class FutureExample extends AbstractVerticle {
    public static void main(String... args) {
        Runner.runNormal(FutureExample.class);
    }

    @Override
    public void start() throws Exception {
//        Future<String> future = anAsyncAction();
//        future.compose(this::anotherAsyncAction)
//                .onComplete(ar -> {
//                    if (ar.failed()) {
//                        System.out.println("Something bad happened");
//                        ar.cause().printStackTrace();
//                    } else {
//                        System.out.println("Result: " + ar.result());
//                    }
//                });
        CompositeFuture.all(anAsyncAction(), anotherAsyncAction("zzz")).onComplete(
                ar -> {
                    if (ar.failed()) {
                        ar.cause().printStackTrace();
                    } else {
                        System.out.println("Result: " + ar.result());
                    }
                }
        );
    }

    private Future<String> anAsyncAction() {
        Promise<String> promise = Promise.promise();
        vertx.setTimer(100, event -> promise.complete("world"));
        return promise.future();
    }

    private Future<String> anotherAsyncAction(String name) {
        Promise<String> promise = Promise.promise();
        vertx.setTimer(100, event -> promise.complete("hello " + name));
        return promise.future();
    }
}
