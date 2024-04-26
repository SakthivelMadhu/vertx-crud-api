

package com.example;

import io.vertx.core.Vertx;
import com.example.database.DatabaseVerticle;

import com.example.http.HttpVerticle;

public class MainVerticle {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new HttpVerticle());
        vertx.deployVerticle(new DatabaseVerticle());
    }
}
