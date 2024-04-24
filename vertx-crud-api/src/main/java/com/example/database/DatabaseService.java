package com.example.database;

import io.vertx.sqlclient.Tuple;

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
        this.dbUrl = config.getString("db.url");
        this.dbUsername = config.getString("db.username");
        this.dbPassword = config.getString("db.password");
        this.client = createJDBCPool();
    }

    private JDBCPool createJDBCPool() {
        // Configure the JDBC connect options
        JDBCConnectOptions connectOptions = new JDBCConnectOptions()
                .setJdbcUrl(dbUrl)
                .setUser(dbUsername)
                .setPassword(dbPassword);

        // Configure the pool options
        PoolOptions poolOptions = new PoolOptions().setMaxSize(10);

        // Create the JDBC pool
        return JDBCPool.pool(vertx, connectOptions, poolOptions);
    }

    public void saveUser(JsonObject user) {
        client.getConnection(connHandler -> {
            if (connHandler.succeeded()) {
                SqlConnection conn = connHandler.result();
                // Execute the SQL statement to save the user data
                conn.preparedQuery("INSERT INTO user_info (id, name, email, gender, status, timestamp) VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)")
                    .execute(Tuple.of(user.getString("id"), user.getString("name"), user.getString("email"), user.getString("gender"), user.getString("status")), ar -> {
                        conn.close();
                        if (ar.succeeded()) {
                            // Send the updated status back to the HTTP verticle
                            vertx.eventBus().send("http.response", new JsonObject()
                                    .put("id", user.getString("id"))
                                    .put("status", "SUCCESS"));
                        } else {
                            // Handle failure
                            ar.cause().printStackTrace();
                        }
                    });
            } else {
                // Handle failure to obtain a connection
                connHandler.cause().printStackTrace();
            }
        });
    }

    
    

	public Object saveUser(Object body) {
		// TODO Auto-generated method stub
		return null;
	}
}


