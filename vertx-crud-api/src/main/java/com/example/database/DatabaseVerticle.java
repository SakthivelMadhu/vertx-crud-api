package com.example.database;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

@SuppressWarnings("deprecation")
public class DatabaseVerticle extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseVerticle.class);

    @Override
    public void start() {
        System.out.println("Starting database verticle...");

        DatabaseService dbService = new DatabaseService(vertx, null);
        System.out.println("Starting database verticle1...");

        vertx.eventBus().consumer("database.save", message -> {
            try {
                LOGGER.info("Received message on address 'database.save'.");
                JsonObject user = (JsonObject) message.body();
                dbService.createUser(user, resultHandler -> {
                    if (resultHandler.succeeded()) {
                        message.reply("User created successfully");
                    } else {
                        message.fail(500, resultHandler.cause().getMessage());
                    }
                });
            } catch (Exception e) {
                LOGGER.error("Error processing message on address 'database.save': " + e.getMessage(), e);
                message.fail(500, e.getMessage());
            }
        });
        
        
        

        vertx.eventBus().consumer("database.update", message -> {
            try {
                LOGGER.info("Received message on address 'database.update'.");
                dbService.updateUser((JsonObject) message.body());
            } catch (Exception e) {
                LOGGER.error("Error processing message on address 'database.update': " + e.getMessage(), e);
                
            }
        });

        vertx.eventBus().consumer("database.delete", message -> {
            try {
                LOGGER.info("Received message on address 'database.delete'.");
                dbService.deleteUser((JsonObject) message.body());
            } catch (Exception e) {
                LOGGER.error("Error processing message on address 'database.delete': " + e.getMessage(), e);
                
            }
        });
    }
}


