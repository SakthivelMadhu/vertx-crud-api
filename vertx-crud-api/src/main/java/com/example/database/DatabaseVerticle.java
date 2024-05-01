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
        // Connect to the database and set up database service
        DatabaseService dbService = new DatabaseService(vertx, null);
        System.out.println("Starting database verticle1...");

        vertx.eventBus().consumer("database.save", message -> {
            System.out.println("Starting database verticle2...");
            try {
                LOGGER.info("Received message on address 'database.save'.");
                dbService.createUser((JsonObject) message.body());
            } catch (Exception e) {
                LOGGER.error("Error processing message on address 'database.save': " + e.getMessage(), e);
                // Handle the exception as needed, such as sending an error response or retrying the operation
            }
        });

        vertx.eventBus().consumer("database.update", message -> {
            try {
                LOGGER.info("Received message on address 'database.update'.");
                dbService.updateUser((JsonObject) message.body());
            } catch (Exception e) {
                LOGGER.error("Error processing message on address 'database.update': " + e.getMessage(), e);
                // Handle the exception as needed
            }
        });

        vertx.eventBus().consumer("database.delete", message -> {
            try {
                LOGGER.info("Received message on address 'database.delete'.");
                dbService.deleteUser((JsonObject) message.body());
            } catch (Exception e) {
                LOGGER.error("Error processing message on address 'database.delete': " + e.getMessage(), e);
                // Handle the exception as needed
            }
        });
    }
}


