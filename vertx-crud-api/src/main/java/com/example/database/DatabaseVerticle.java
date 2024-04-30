package com.example.database;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

public class DatabaseVerticle extends AbstractVerticle {

    @Override
    public void start() {
        // Connect to the database and set up database service
        DatabaseService dbService = new DatabaseService(vertx, null);

        vertx.eventBus().consumer("database.save", message -> dbService.saveUser((JsonObject) message.body()));

        
        vertx.eventBus().consumer("database.update", message -> dbService.updateUser((JsonObject) message.body()));
        

        vertx.eventBus().consumer("database.delete", message -> dbService.deleteUser((JsonObject) message.body()));

    }
}
