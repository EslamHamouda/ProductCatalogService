# Product Catalog Service

A Spring Boot microservice for managing productEntity listings, categories, and inventoryEntity in an e-commerce system.

## Features

- **Product Management**: Create, read, update, and delete productEntity listings
- **Category Management**: Organize productEntities into hierarchical categories
- **Inventory Management**: Track productEntity stock levels, reserve inventoryEntity, and manage stock updates
- **RESTful API**: Well-documented API endpoints for all operations
- **Swagger Documentation**: Interactive API documentation
- **External Authentication**: Integration with User Service for authentication and authorization

## Technologies

- Java 17
- Spring Boot 3.5.0
- Spring Data JPA
- Spring Security
- MySQL Database
- Lombok
- Swagger/OpenAPI

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven
- MySQL

### Database Setup

The application is configured to automatically create the database if it doesn't exist. Make sure MySQL is running and update the database configuration in `application.properties` if needed:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/product_catalog?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=
```

### Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Run the application using Maven:

```bash
mvn spring-boot:run
```

The application will start on port 8080 by default.

## API Documentation

Once the application is running, you can access the Swagger UI at:

```
http://localhost:8080/swagger-ui.html
```

This provides interactive documentation for all API endpoints.

## Authentication & Authorization

This service is designed as a pure REST API that relies on an external User Service for authentication and authorization. Key points:

- The Product Catalog Service does not handle user management, authentication, or authorization directly
- All authentication and user management is delegated to the User Service
- Integration with the User Service is configured in `application.properties`
- In production, API endpoints would be secured using JWT tokens issued by the User Service
- Currently, all API endpoints are publicly accessible for development purposes

### User Service Integration Configuration

To configure the integration with the User Service, update the following properties in `application.properties`:

```properties
user-service.base-url=http://localhost:8081/api/auth
user-service.validate-token-endpoint=/validate-token
user-service.api-key=your-api-key-here
```

## API Endpoints

### Categories

- `GET /api/categories` - Get all categories
- `GET /api/categories/root` - Get root categories
- `GET /api/categories/{id}` - Get categoryEntity by ID
- `GET /api/categories/{id}/subcategories` - Get subcategories of a categoryEntity
- `GET /api/categories/name/{name}` - Get categoryEntity by name
- `POST /api/categories` - Create a new categoryEntity
- `PUT /api/categories/{id}` - Update a categoryEntity
- `DELETE /api/categories/{id}` - Delete a categoryEntity

### Products

- `GET /api/productEntities` - Get all productEntities (paginated)
- `GET /api/productEntities/all` - Get all productEntities (list)
- `GET /api/productEntities/{id}` - Get productEntity by ID
- `GET /api/productEntities/sku/{sku}` - Get productEntity by SKU
- `GET /api/productEntities/categoryEntity/{categoryId}` - Get productEntities by categoryEntity
- `GET /api/productEntities/search` - Search productEntities by name
- `GET /api/productEntities/price-range` - Get productEntities in a price range
- `GET /api/productEntities/in-stock` - Get in-stock productEntities
- `POST /api/productEntities` - Create a new productEntity
- `PUT /api/productEntities/{id}` - Update a productEntity
- `DELETE /api/productEntities/{id}` - Delete a productEntity

### Inventory

- `GET /api/inventoryEntity` - Get all inventoryEntity items
- `GET /api/inventoryEntity/{id}` - Get inventoryEntity by ID
- `GET /api/inventoryEntity/productEntity/{productId}` - Get inventoryEntity by productEntity ID
- `GET /api/inventoryEntity/low-stock` - Get productEntities with low stock
- `GET /api/inventoryEntity/in-stock` - Get in-stock productEntities
- `GET /api/inventoryEntity/out-of-stock` - Get out-of-stock productEntities
- `GET /api/inventoryEntity/total-count` - Get total inventoryEntity count
- `POST /api/inventoryEntity` - Create a new inventoryEntity item
- `PUT /api/inventoryEntity/{id}` - Update an inventoryEntity item
- `PATCH /api/inventoryEntity/productEntity/{productId}/stock` - Update stock quantity
- `PATCH /api/inventoryEntity/productEntity/{productId}/reserve` - Reserve stock
- `PATCH /api/inventoryEntity/productEntity/{productId}/release` - Release reserved stock
- `DELETE /api/inventoryEntity/{id}` - Delete an inventoryEntity item

## License

This project is licensed under the Apache License 2.0 - see the LICENSE file for details.
