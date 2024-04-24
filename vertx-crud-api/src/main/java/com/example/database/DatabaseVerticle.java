package com.example.database;


import io.vertx.core.AbstractVerticle;

public class DatabaseVerticle extends AbstractVerticle {

    @Override
    public void start() {
        // Connect to the database and set up database service
        DatabaseService dbService = new DatabaseService(vertx, null);
        vertx.eventBus().consumer("database.save", message -> dbService.saveUser(message.body()));
    }
}
