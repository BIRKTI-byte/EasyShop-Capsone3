# EasyShop - E-Commerce Application

A Spring Boot REST API e-commerce application with product catalog, shopping cart, and order management.

![Homepage](screenshots/homepage.png)

## Overview

EasyShop is a full-stack e-commerce platform built with Spring Boot and MySQL. Users can browse products, filter by category and price, add items to a shopping cart, and create orders.

## Tech Stack

- **Backend:** Java 17, Spring Boot 2.7.3, Spring Security, JWT
- **Database:** MySQL 8.0.33
- **Frontend:** HTML5, CSS3, JavaScript, Axios, Mustache templates
- **Build Tool:** Maven

## Project Structure

```
src/main/
├── java/org/yearup/
│   ├── controllers/          # REST API endpoints
│   │   ├── AuthenticationController.java
│   │   ├── ProductsController.java
│   │   ├── ShoppingCartController.java
│   │   ├── OrdersController.java
│   │   ├── ProfileController.java
│   │   └── CategoriesController.java
│   ├── models/              # Data models
│   │   ├── User.java
│   │   ├── Product.java
│   │   ├── Category.java
│   │   ├── ShoppingCart.java
│   │   ├── ShoppingCartItem.java
│   │   ├── Order.java
│   │   ├── OrderLineItem.java
│   │   ├── Profile.java
│   │   └── CheckoutRequest.java
│   ├── data/                # Data access layer
│   ├── security/            # JWT & Security config
│   ├── configurations/      # Spring configurations
│   └── EasyshopApplication.java
├── resources/
│   ├── application.properties
│   ├── static/
│   │   ├── index.html       # Main page
│   │   ├── css/main.css
│   │   ├── js/              # Client-side scripts
│   │   ├── templates/       # HTML templates
│   │   └── images/
│   └── banner.txt
└── test/
```

## Getting Started

### Prerequisites
- Java 17 JDK
- Maven 3.9+
- MySQL 8.0+

### Setup

1. **Create database:**
   ```bash
   mysql -u root -p < database/create_database_easyshop.sql
   ```

2. **Configure database connection** in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/easyshop
   spring.datasource.username=root
   spring.datasource.password=your_password
   ```

3. **Build and run:**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Access the application:**
   - Navigate to `http://localhost:8080`
   - Login with: `admin` / `password`

![Login Page](screenshots/login.png)

## API Endpoints

### Authentication
- `POST /authenticate` - Login and receive JWT token

### Products
- `GET /products` - Get all products (with optional filters: `cat`, `minPrice`, `maxPrice`, `subCategory`)
- `GET /products/{id}` - Get product by ID
- `POST /products` - Create product (admin)
- `PUT /products/{id}` - Update product (admin)
- `DELETE /products/{id}` - Delete product (admin)

### Shopping Cart
- `GET /cart` - Get user's shopping cart
- `POST /cart/products/{productId}` - Add item to cart
- `PUT /cart/products/{productId}` - Update cart item quantity
- `DELETE /cart/products/{productId}` - Remove item from cart
- `DELETE /cart` - Clear cart

### Orders
- `POST /orders` - Create order from cart items

### Profile
- `GET /profile` - Get user profile
- `PUT /profile` - Update user profile

### Categories
- `GET /categories` - Get all categories
- `GET /categories/{id}` - Get category by ID

![Products Page](screenshots/products.png)

## Features

**Product Browsing**
- View all products in catalog
- Filter by category, price range, and subcategory
- View product details

**Shopping Cart**
- Add items to cart
- Update item quantities
- Remove individual items
- Clear entire cart
- Real-time cart totals

![Shopping Cart](screenshots/cart.png)

**Orders**
- Create orders from cart items
- Automatic order line item generation
- Cart clears after checkout

**User Profile**
- View user profile information
- Update profile details

**Security**
- JWT token-based authentication
- Spring Security configuration
- Role-based access control

## Database Schema

The application uses 7 tables:
- `users` - User accounts
- `profiles` - User profile information
- `products` - Product catalog
- `categories` - Product categories
- `shopping_cart` - User shopping cart items
- `orders` - Customer orders
- `order_line_items` - Items within orders

## Running Tests

```bash
mvn test
```

## Build and Package

```bash
mvn package -DskipTests
```

A JAR file will be created in the `target/` directory.

## Notes

- All API endpoints except login require JWT authentication (Bearer token)
- Shopping cart is user-specific and persists between sessions
- Orders are created from current shopping cart items
- Profile information is used as shipping address for orders
