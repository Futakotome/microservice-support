package io.futakotome.vertx.coreStudy.net.batch;

import io.futakotome.vertx.util.Runner;
import io.vertx.core.AbstractVerticle;

public class Server extends AbstractVerticle {
    public static void main(String... args) {
        Runner.runNormal(Server.class);
    }

    @Override
    public void start() throws Exception {
        vertx.createNetServer().connectHandler(socket -> {
            BatchStream batchStream = new BatchStream(socket, socket);
            batchStream.pause();
            batchStream.handler(batch -> {
                System.out.println("Server Received : " + batch.getRaw().toString());
                batchStream.write(batch);
                if (batchStream.writeQueueFull()) {
                    batchStream.pause();

                    batchStream.drainHandler(done -> {
                        batchStream.resume();
                    });
                }
            }).endHandler(event -> batchStream.end())
                    .exceptionHandler(event -> {
                        event.printStackTrace();
                        batchStream.end();
                    });
            batchStream.resume();
        }).listen(1234);
        System.out.println("Batch server is now listening to port : 1234");
    }
}
