package com.example.http;



import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;


public class HttpVerticle extends AbstractVerticle {


    // UserService userService = new UserService(vertx);

    @Override
    public void start() {

    UserService userService = new UserService(vertx);
   

    System.out.println("Http verticle working... ");
    Router router = Router.router(vertx);

    router.post("/api/users").handler(userService::createUser);
    router.get("/api/users").handler(new UserService(vertx)::getUsers);
    router.put("/api/users/:id").handler(new UserService(vertx)::updateUser);
    router.delete("/api/users/:id").handler(new UserService(vertx)::deleteUser);
    vertx.createHttpServer()
        .requestHandler(router)
        .listen(8081, http -> {
            if (http.succeeded()) {
                System.out.println("HTTP server started on port 8081 ");
            } else {
                System.err.println("Failed to start HTTP server" + http.cause().getMessage());
            }
        });
    }      


}


 