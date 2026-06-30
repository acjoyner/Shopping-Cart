# Dream Shops

A Spring Boot e-commerce REST API for managing products, categories, shopping carts, orders, and users.

## Tech Stack

- **Java 17**
- **Spring Boot 4.1.0**
- **Spring Data JPA**
- **Spring WebMVC**
- **MySQL**
- **Lombok**
- **ModelMapper**
- **Maven**

## Project Structure

```
com.acjoyner.dream_shops
├── config          # Application configuration
├── controller      # REST controllers
├── dto             # Data transfer objects
├── enums           # Enumerations (e.g., OrderStatus)
├── exceptions      # Custom exceptions
├── model           # JPA entities
├── repository      # Spring Data repositories
├── request         # Request DTOs
├── response        # API response wrappers
└── service         # Business logic
    ├── cart
    ├── category
    ├── image
    ├── order
    ├── product
    └── user
```

## Prerequisites

- Java 17 or later
- Maven 3.8+
- MySQL 8.0+ running locally

## Database Setup

The application expects a MySQL database named `dream_shops_db`:

```sql
CREATE DATABASE IF NOT EXISTS dream_shops_db;
```

Default connection settings (defined in `src/main/resources/application.yaml`):

| Property | Value |
|----------|-------|
| URL | `jdbc:mysql://localhost:3306/dream_shops_db` |
| Username | `root` |
| Password | *(empty)* |

Update these values in `application.yaml` if your local MySQL setup differs.

## Running the Application

1. Clone or open the project in your IDE.
2. Ensure MySQL is running and the `dream_shops_db` database exists.
3. Build the project:

   ```bash
   mvn clean install
   ```

4. Run the application:

   ```bash
   mvn spring-boot:run
   ```

The API will be available at `http://localhost:8080`.

## API Prefix

All endpoints are prefixed with `/api/v1` (configured in `application.yaml`):

```yaml
api:
  prefix: /api/v1
```

Example: `GET http://localhost:8080/api/v1/products`

## Key Features

- **Product Management** — CRUD operations for products
- **Category Management** — Organize products by category
- **Cart & Cart Items** — Add, update, remove items from a shopping cart
- **Order Processing** — Place orders from a cart
- **User Management** — Create and manage users
- **Image Handling** — Product image upload support

## Running Tests

```bash
mvn test
```

## Configuration Highlights

- **JPA/Hibernate**: `ddl-auto: update` automatically creates/updates database tables based on entities.
- **SQL Logging**: Enabled with formatted SQL output for development.
- **File Uploads**: Multipart uploads enabled with a max file size of 10MB and max request size of 50MB.

## Notes

- This is a learning/demo project. Before production use, consider:
  - Adding proper authentication and authorization
  - Using environment variables or a secrets manager for database credentials
  - Setting `ddl-auto` to `validate` or removing it in favor of migrations (e.g., Flyway/Liquibase)
  - Adding integration tests and input validation
