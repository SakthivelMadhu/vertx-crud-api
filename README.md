# Vert.x CRUD API Project

This project implements a simple CRUD API using Vert.x, PostgreSQL, and Maven.

## Project Structure
```bash
vertx-crud-api/
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ ├── com/
│ │ │ │ ├── example/
│ │ │ │ │ ├── MainVerticle.java # Main entry point of the application
│ │ │ │ │ ├── database/
│ │ │ │ │ │ ├── DatabaseService.java # Service for database interactions
│ │ │ │ │ │ ├── DatabaseVerticle.java # Verticle to deploy database service
│ │ │ │ │ ├── http/
│ │ │ │ │ │ ├── HttpVerticle.java # Verticle for handling HTTP requests
│ │ │ │ │ │ ├── UserService.java # Service for user-related HTTP endpoints
│ │ │ │ │ ├── model/
│ │ │ │ │ │ ├── User.java # Model class for User
│ │ ├── resources/
│ │ │ ├── application.properties # Application configuration properties
│ │ │ ├── log4j2.xml # Log4j2 configuration file
│ ├── test/
│ │ ├── java/
│ │ │ ├── com/
│ │ │ │ ├── example/
│ │ │ │ │ ├── http/
│ │ │ │ │ │ ├── UserServiceTest.java # Unit tests for UserService
├── target/ # Output directory for compiled classes and packaged JAR
├── pom.xml # Maven project configuration file
├── README.md # Project documentation
```


## How to Run

1. Make sure you have PostgreSQL installed and running.
2. Update the database connection settings in `src/main/resources/application.properties`.
3. Build the project using Maven:
```bash 
mvn clean package
```
4. Run the JAR file generated in the `target` directory:
```bash
java -jar target/vertx-crud-api-0.0.1-SNAPSHOT.jar
```

## Endpoints

- `POST /api/users`: Create a new user.
- `GET /api/users`: Retrieve all users or filter by query parameters (`name`, `gender`, `status`).
- `PUT /api/users/{id}`: Update an existing user.
- `DELETE /api/users/{id}`: Delete a user by ID.

