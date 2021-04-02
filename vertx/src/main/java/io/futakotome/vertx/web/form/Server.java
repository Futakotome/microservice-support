package io.futakotome.vertx.web.form;

import io.futakotome.vertx.util.Runner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class Server extends AbstractVerticle {
    public static void main(String... args) {
        Runner.runNormal(Server.class);
    }

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());

        router.route("/").handler(routingContext -> {
            routingContext.response().putHeader("content-type", "text/html").end(
                    "<form action=\"/form\" method=\"post\">\n" +
                            "    <div>\n" +
                            "        <label for=\"name\">Enter your name:</label>\n" +
                            "        <input type=\"text\" id=\"name\" name=\"name\" />\n" +
                            "    </div>\n" +
                            "    <div class=\"button\">\n" +
                            "        <button type=\"submit\">Send</button>\n" +
                            "    </div>" +
                            "</form>"
            );
        });

        router.post("/form").handler(ctx -> {
            ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, "text/plain");
            ctx.response().end("Hello " + ctx.request().getParam("name") + "!");
        });

        vertx.createHttpServer().requestHandler(router).listen(8080);
    }
}
