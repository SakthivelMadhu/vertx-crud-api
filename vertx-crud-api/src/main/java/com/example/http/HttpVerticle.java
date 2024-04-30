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


    
    // private final DatabaseService databaseService;

    // public HttpVerticle(DatabaseService databaseService) {
    //     this.databaseService = databaseService;
    // }

    //     int port = 8080; // Initial port number
    //     HttpServer server = vertx.createHttpServer();
    //     server.requestHandler(router);
    //     server.listen(port, http -> {
    //         if (http.succeeded()) {
    //             System.out.println("HTTP server started on port " + port);
    //         } else {
    //             System.err.println("Failed to start HTTP server on port " + port);
    //             // Try a different port
    //             startServerOnNextAvailablePort(server, port + 1);
    //         }
    //     });
    // }

    // private void startServerOnNextAvailablePort(HttpServer server, int nextPort) {
    //     // Attempt to start the server on the next available port
    //     server.listen(nextPort, http -> {
    //         if (http.succeeded()) {
    //             System.out.println("HTTP server started on port " + nextPort);
    //         } else {
    //             System.err.println("Failed to start HTTP server on port " + nextPort);
    //             // Try the next port
    //             startServerOnNextAvailablePort(server, nextPort + 1);
    //         }
    //     });
    // }
//     }
    
// }
