package com.example.http;



import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

public class HttpVerticle extends AbstractVerticle {

    @Override
    public void start() {
        Router router = Router.router(vertx);
        router.post("/api/users").handler(new UserService(vertx)::createUser);
        router.get("/api/users").handler(new UserService(vertx)::getUsers);
        router.put("/api/users/:id").handler(new UserService(vertx)::updateUser);

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8080, http -> {
                if (http.succeeded()) {
                    System.out.println("HTTP server started on port 8080");
                } else {
                    System.err.println("Failed to start HTTP server");
                }
            });
    }
}
