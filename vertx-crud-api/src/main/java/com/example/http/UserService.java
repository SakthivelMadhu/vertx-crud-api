package com.example.http;


import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;


@SuppressWarnings("deprecation")
public class UserService {


    private final Vertx vertx;

    public UserService(Vertx vertx) {
        this.vertx = vertx;
    }

    public void createUser(RoutingContext routingContext) {
        JsonObject newUser = routingContext.getBodyAsJson();
        if (newUser == null) {
            System.out.println("Sending message to address 'database.save'...");
            vertx.eventBus().request("database.save", newUser, reply -> {
                System.out.println("new user...");
                if (reply.succeeded()) {
                    System.out.println("Received reply for 'database.save' request...");
                    routingContext.response().setStatusCode(201)
                            .putHeader("content-Type", "application/json")
                            .end(new JsonObject().put("status", "SUCCESS").encode());
                } else {
                    System.out.println("Failed to receive reply for 'database.save' request...");
                    routingContext.response().setStatusCode(500)
                            .putHeader("content-type", "application/json")
                            .end(new JsonObject().put("status", "FAILURE")
                                    .put("failure_reason", reply.cause().getMessage()).encode());
                }
            });
        } else {
            System.out.println("Request body is null.");
            routingContext.response().setStatusCode(400).end("Request body is required.");
        }
    }

    // public void createUser(RoutingContext routingContext) {
    //     JsonObject newUser = routingContext.getBodyAsJson();
    //     if (newUser == null) {
    //         System.out.println("Sending message to address 'database.save'...");
            
    //         // Use publish instead of request
    //         vertx.eventBus().publish("database.save", newUser);
    
    //         // Respond immediately without waiting for a reply
    //         routingContext.response().setStatusCode(202) // Accepted status code
    //             .putHeader("Content-Type", "application/json")
    //             .end(new JsonObject().put("status", "PENDING").encode());
    //     } else {
    //         System.out.println("Request body is null.");
    //         routingContext.response().setStatusCode(400).end("Request body is required.");
    //     }
    // }
    
    // public void getUsers(RoutingContext routingContext) {
    //     String name = routingContext.request().getParam("name");
    //     String gender = routingContext.request().getParam("gender");
    //     String status = routingContext.request().getParam("status");
    
    //     JsonObject query = new JsonObject();
    //     if (name != null) query.put("name", name);
    //     if (gender != null) query.put("gender", gender);
    //     if (status != null) query.put("status", status);
    
    //     System.out.println("Publishing message to address 'database.query'...");
    
    //     vertx.eventBus().publish("database.query", query);
    
    //     // Send a response to the client indicating that the query has been initiated
    //     routingContext.response().setStatusCode(202) // Accepted status code
    //             .putHeader("content-type", "application/json")
    //             .end(new JsonObject().put("status", "QUERY_INITIATED").encode());
    // }
    
    // public void updateUser(RoutingContext routingContext) {
    //     String userId = routingContext.request().getParam("id");
    //     JsonObject updatedUser = routingContext.getBodyAsJson().put("id", userId);
    //     System.out.println("Publishing message to address 'database.update'...");
    //     vertx.eventBus().publish("database.update", updatedUser);
    
    //     // Send a response to the client indicating that the update request has been initiated
    //     routingContext.response().setStatusCode(202) // Accepted status code
    //             .putHeader("content-type", "application/json")
    //             .end(new JsonObject().put("status", "UPDATE_INITIATED").encode());
    // }
    
    // public void deleteUser(RoutingContext routingContext) {
    //     String userId = routingContext.request().getParam("id");
    //     JsonObject deleteUser = new JsonObject().put("id", userId);
    //     System.out.println("Publishing message to address 'database.delete'...");
    //     vertx.eventBus().publish("database.delete", deleteUser);
    
    //     // Send a response to the client indicating that the delete request has been initiated
    //     routingContext.response().setStatusCode(202) // Accepted status code
    //             .putHeader("content-type", "application/json")
    //             .end(new JsonObject().put("status", "DELETE_INITIATED").encode());
    // }
    


    public void getUsers(RoutingContext routingContext) {
        String name = routingContext.request().getParam("name");
        String gender = routingContext.request().getParam("gender");
        String status = routingContext.request().getParam("status");

        JsonObject query = new JsonObject();
        if (name != null) query.put("name", name);
        if (gender != null) query.put("gender", gender);
        if (status != null) query.put("status", status);

        System.out.println("getting message to address 'database.query...");

        vertx.eventBus().<JsonArray>request("database.query", query, reply -> {
            if (reply.succeeded()) {
                System.out.println("received message to address 'database.query'...");
                JsonArray users = reply.result().body();
                routingContext.response().setStatusCode(200)
                        .putHeader("content-type", "application/json")
                        .end(users.encode());
            } else {
                System.out.println("failed to receive replay message to address 'database.query'...");
                routingContext.response().setStatusCode(500)
                        .putHeader("content-type", "application/json")
                        .end(new JsonObject().put("status", "FAILURE")
                                .put("failure_reason", reply.cause().getMessage()).encode());
            }
        });
    }

    public void updateUser(RoutingContext routingContext) {
        String userId = routingContext.request().getParam("id");
        JsonObject updatedUser = routingContext.getBodyAsJson().put("id", userId);
        System.out.println("updating message to address 'database.update'...");
        vertx.eventBus().<JsonObject>request("database.update", updatedUser, reply -> {
            if (reply.succeeded()) {
                System.out.println("received updates message to address 'database.update'...");
                routingContext.response().setStatusCode(200)
                        .putHeader("content-type", "application/json")
                        .end(new JsonObject().put("status", "SUCCESS").encode());
            } else {
                System.out.println("failed to receive update message to address 'database.update'...");
                routingContext.response().setStatusCode(500)
                        .putHeader("content-type", "application/json")
                        .end(new JsonObject().put("status", "FAILURE")
                                .put("failure_reason", reply.cause().getMessage()).encode());
            }
        });
    }

    public void deleteUser(RoutingContext routingContext) {
        String userId = routingContext.request().getParam("id");
        JsonObject deleteUser = new JsonObject().put("id", userId);
        System.out.println("deleting message to address 'database.delete'...");
        vertx.eventBus().<JsonObject>request("database.delete", deleteUser, reply -> {
            if (reply.succeeded()) {
                System.out.println("received delete message to address 'database.delete'...");
                routingContext.response().setStatusCode(200)
                        .putHeader("content-type", "application/json")
                        .end(new JsonObject().put("status", "SUCCESS").encode());
            } else {
                System.out.println("failed to receive delete message to address 'database.delete'...");
                routingContext.response().setStatusCode(500)
                        .putHeader("content-type", "application/json")
                        .end(new JsonObject().put("status", "FAILURE")
                                .put("failure_reason", reply.cause().getMessage()).encode());
            }
        });
    }
}

