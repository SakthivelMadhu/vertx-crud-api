

package com.example;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import com.example.database.DatabaseVerticle;
import com.example.http.HttpVerticle;

public class MainVerticle {

    public static void main(String[] args) {

        System.out.println("Starting the application...");


        Vertx vertx = Vertx.vertx();
        // vertx.deployVerticle(new HttpVerticle());
        // vertx.deployVerticle(new DatabaseVerticle());


        // Deploy DatabaseVerticle with deployment options
        DeploymentOptions dbOptions = new DeploymentOptions().setInstances(2);
        vertx.deployVerticle(DatabaseVerticle.class.getName(), dbOptions);

        // Deploy HttpVerticle
        vertx.deployVerticle(HttpVerticle.class.getName());


        System.out.println("Application started successfully.");
    }
}
