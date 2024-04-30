# Vert.x CRUD API Project

This project demonstrates a simple CRUD (Create, Read, Update, Delete) API implementation using Vert.x, a reactive toolkit for building reactive applications on the JVM.

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

## project Explanation demo video

<https://drive.google.com/file/d/1Z4z80P4Bi1SxMWs_cL84KOQhDTq6LH6L/view?usp=sharing>

## Overview

The project consists of the following components:

- **MainVerticle**: Main entry point for the Vert.x application. Deploys HTTP and Database verticles.
- **HttpVerticle**: Verticle responsible for handling HTTP requests and routing them to appropriate handlers.
- **DatabaseVerticle**: Verticle responsible for managing database operations and communication with the database service.
- **DatabaseService**: Service class managing database interactions for CRUD operations on the user entity.

## Setup

To run the application locally, follow these steps:

1. **Prerequisites**: Ensure you have Java Development Kit (JDK) 8 or higher installed on your system.
2. **Database Setup**:
   - Install PostgreSQL and create a database named `user_info`.
   - Update the database connection settings in the `application.properties` file.
3.**Building the Project**:

```bash
mvn clean package
```

4.**Running the Application**

```bash
java -jar target/vertx.jar
```

5.**Accessing the API**

- Once the application is running, you can access the API using `http://localhost:8080/api/users`.

## API Endpoints

- **POST /api/users**: Create a new user.
- **GET /api/users**: Retrieve all users or filter by query parameters (name, gender, status).
- **PUT /api/users/:id**: Update an existing user.
- **DELETE /api/users/:id**: Delete a user by ID.

## Dependencies

- **Vert.x Core**: Reactive core for building reactive applications.
- **Vert.x Web**: Web routing and handling support for Vert.x applications.
- **Vert.x JDBC Client**: Asynchronous JDBC client for database access.
- **PostgreSQL Driver**: JDBC driver for PostgreSQL database.
