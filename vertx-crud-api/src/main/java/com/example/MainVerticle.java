package com.example;

import com.example.database.DatabaseVerticle;

import com.example.http.HttpVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) {
        vertx.deployVerticle(new HttpVerticle());
        vertx.deployVerticle(new DatabaseVerticle());
    }
}