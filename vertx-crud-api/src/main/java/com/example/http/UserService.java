package com.example.http;


import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class UserService {

    private final Vertx vertx;

    public UserService(Vertx vertx) {
        this.vertx = vertx;
    }

    public void createUser(RoutingContext routingContext) {
        @SuppressWarnings("deprecation")
        JsonObject newUser = routingContext.getBodyAsJson();
        vertx.eventBus().request("database.save", newUser, reply -> {
            if (reply.succeeded()) {
                routingContext.response().setStatusCode(201)
                        .putHeader("content-type", "application/json")
                        .end(new JsonObject().put("status", "SUCCESS").encode());
            } else {
                routingContext.response().setStatusCode(500)
                        .putHeader("content-type", "application/json")
                        .end(new JsonObject().put("status", "FAILURE")
                                .put("failure_reason", reply.cause().getMessage()).encode());
            }
        });
    }

    public void getUsers(RoutingContext routingContext) {
        String name = routingContext.request().getParam("name");
        String gender = routingContext.request().getParam("gender");
        String status = routingContext.request().getParam("status");

        JsonObject query = new JsonObject();
        if (name != null) query.put("name", name);
        if (gender != null) query.put("gender", gender);
        if (status != null) query.put("status", status);

        vertx.eventBus().<JsonArray>request("database.query", query, reply -> {
            if (reply.succeeded()) {
                JsonArray users = reply.result().body();
                routingContext.response().setStatusCode(200)
                        .putHeader("content-type", "application/json")
                        .end(users.encode());
            } else {
                routingContext.response().setStatusCode(500)
                        .putHeader("content-type", "application/json")
                        .end(new JsonObject().put("status", "FAILURE")
                                .put("failure_reason", reply.cause().getMessage()).encode());
            }
        });
    }

    public void updateUser(RoutingContext routingContext) {
        String userId = routingContext.request().getParam("id");
        @SuppressWarnings("deprecation")
        JsonObject updatedUser = routingContext.getBodyAsJson().put("id", userId);

        vertx.eventBus().<JsonObject>request("database.update", updatedUser, reply -> {
            if (reply.succeeded()) {
                routingContext.response().setStatusCode(200)
                        .putHeader("content-type", "application/json")
                        .end(new JsonObject().put("status", "SUCCESS").encode());
            } else {
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

        vertx.eventBus().<JsonObject>request("database.delete", deleteUser, reply -> {
            if (reply.succeeded()) {
                routingContext.response().setStatusCode(200)
                        .putHeader("content-type", "application/json")
                        .end(new JsonObject().put("status", "SUCCESS").encode());
            } else {
                routingContext.response().setStatusCode(500)
                        .putHeader("content-type", "application/json")
                        .end(new JsonObject().put("status", "FAILURE")
                                .put("failure_reason", reply.cause().getMessage()).encode());
            }
        });
    }
}