package com.example.database;

import io.vertx.sqlclient.Tuple;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.jdbcclient.JDBCPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.SqlConnection;
import io.vertx.jdbcclient.JDBCConnectOptions;


public class DatabaseService {

    
    private final Vertx vertx;
    private final JDBCPool client;

    private final String dbUrl;
    private final String dbUsername;
    private final String dbPassword;

    public DatabaseService(Vertx vertx, JsonObject config) {
        this.vertx = vertx;
        // this.dbUrl = config.getString("quarkus.datasource.jdbc.url");
        // this.dbUsername = config.getString("quarkus.datasource.username");
        // this.dbPassword = config.getString("quarkus.datasource.password");
        this.dbUrl = config.getString("db.url");
        this.dbUsername = config.getString("db.username");
        this.dbPassword = config.getString("db.password");
        this.client = createJDBCPool();
    }

    private JDBCPool createJDBCPool() {
        // Configure the JDBC connect options
        System.err.println("database connections are working... ");

        JDBCConnectOptions connectOptions = new JDBCConnectOptions()
                .setJdbcUrl(dbUrl)
                .setUser(dbUsername)
                .setPassword(dbPassword);

        // Configure the pool options
        PoolOptions poolOptions = new PoolOptions().setMaxSize(10);

        // Create the JDBC pool
        return JDBCPool.pool(vertx, connectOptions, poolOptions);
    }

    public void createUser(JsonObject user , Handler<AsyncResult<Void>> resultHandler) {
        System.out.println("Attempting to save user data...");
        client.getConnection(connHandler -> {
            if (connHandler.succeeded()) {
                System.out.println("Database connection obtained successfully.");
                SqlConnection conn = connHandler.result();
                // Execute the SQL statement to save the user data
                conn.preparedQuery("INSERT INTO user_infos (id, name, email, gender, status, timestamp) VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)")
                    .execute(Tuple.of(
                        user.getString("id"), 
                        user.getString("name"), 
                        user.getString("email"), 
                        user.getString("gender"), 
                        user.getString("status")), 
                        ar -> {
                            conn.close();
                            if (ar.succeeded()) {// Send the updated status back to the HTTP verticle
                                System.out.println("Sending status update to HTTP verticle...");
                                vertx.eventBus().send("database.save", new JsonObject()
                                        .put("id", user.getString("id"))
                                        .put("status", "SUCCESS"));
                            } else {// Handle failure
                                ar.cause().printStackTrace();
                                System.err.println("Failed to save user data.");
                            }
                        });
            } else {// Handle failure to obtain a connection
                connHandler.cause().printStackTrace();
                System.err.println("Failed to obtain database connection.");
            }
        });
    }
    

    // public void createUser(JsonObject user) {
    //     System.out.println("User000... ");
    //     client.getConnection(connHandler -> {
    //         System.out.println("User1111... ");
    //         if (connHandler.succeeded()) {
    //             SqlConnection conn = connHandler.result();
    //             // Execute the SQL statement to save the user data
    //             System.out.println("User2222 ");
    //             conn.preparedQuery("INSERT INTO user_infos (id, name, email, gender, status, timestamp) VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)")
    //                 .execute(Tuple.of(user.getString("id"), user.getString("name"), user.getString("email"), user.getString("gender"), user.getString("status")), ar -> {
    //                     conn.close();
    //                     if (ar.succeeded()) {
    //                         // Handle successful saving of user data within the same class
    //                         handleUserSaveSuccess(user);
    //                     } else {
    //                         // Handle failure
    //                         ar.cause().printStackTrace();
    //                         System.err.println("Failed to save user: " + user.encode());
    //                     }
    //                 });
    //         } else {
    //             // Handle failure to obtain a connection
    //             connHandler.cause().printStackTrace();
    //             System.err.println("Failed to obtain database connection while saving user: " + user.encode());
    //         }
    //     });
    // }
    
    // private void handleUserSaveSuccess(JsonObject user) {
    //     // Send the updated status back to the HTTP verticle or perform any other necessary actions
    //     System.out.println("User datas are going... ");
    //     System.out.println("User saved successfully: " + user.encode());
    // }
    

    public void updateUser(JsonObject user) {
        client.getConnection(connHandler -> {
            if (connHandler.succeeded()) {
                SqlConnection conn = connHandler.result();
                // Execute the SQL statement to update the user
                conn.preparedQuery("UPDATE user_infos SET name = ?, email = ?, gender = ?, status = ? WHERE id = ?")
                    .execute(Tuple.of(user.getString("name"), user.getString("email"), user.getString("gender"), user.getString("status"), user.getString("id")), ar -> {
                        conn.close();
                        if (ar.succeeded()) {
                            // Send the updated status back to the HTTP verticle
                            vertx.eventBus().send("database.update", new JsonObject()
                                    .put("id", user.getString("id"))
                                    .put("status", "SUCCESS"));
                            System.out.println("User updated successfully: " + user.encode());
                        } else {
                            // Handle failure
                            ar.cause().printStackTrace();
                            System.err.println("Failed to update user: " + user.encode());
                        }
                    });
            } else {
                // Handle failure to obtain a connection
                connHandler.cause().printStackTrace();
                System.err.println("Failed to obtain database connection while updating user: " + user.encode());
            }
        });
    }

    public void deleteUser(JsonObject user) {
        client.getConnection(connHandler -> {
            if (connHandler.succeeded()) {
                SqlConnection conn = connHandler.result();
                // Execute the SQL statement to delete the user
                conn.preparedQuery("DELETE FROM user_infos WHERE id = ?")
                    .execute(Tuple.of(user.getString("id")), ar -> {
                        conn.close();
                        if (ar.succeeded()) {
                            // Send the updated status back to the HTTP verticle
                            vertx.eventBus().send("database.delete", new JsonObject()
                                    .put("id", user.getString("id"))
                                    .put("status", "SUCCESS"));
                            System.out.println("User deleted successfully: " + user.encode());
                        } else {
                            // Handle failure
                            ar.cause().printStackTrace();
                            System.err.println("Failed to delete user: " + user.encode());
                        }
                    });
            } else {
                // Handle failure to obtain a connection
                connHandler.cause().printStackTrace();
                System.err.println("Failed to obtain database connection while deleting user: " + user.encode());
            }
        });
    }

    
    
}


