package com.example.database;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

public class DatabaseVerticle extends AbstractVerticle {

    @Override
    public void start() {
        // Connect to the database and set up database service
        DatabaseService dbService = new DatabaseService(vertx, null);
        vertx.eventBus().consumer("database.save", message -> dbService.saveUser(message.body()));

        vertx.eventBus().consumer("database.update", message -> {
            JsonObject updatedUser = (JsonObject) message.body();
            dbService.updateUser(updatedUser);
        });
        

        vertx.eventBus().consumer("database.delete", message -> {
            JsonObject deleteUser = (JsonObject) message.body();
            dbService.deleteUser(deleteUser);
        });

    }
}
