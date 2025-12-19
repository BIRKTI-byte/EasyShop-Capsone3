# EasyShop - E-Commerce Platform

A modern, full-stack e-commerce platform built with Spring Boot and MySQL, featuring a beautiful responsive UI with advanced shopping cart management and secure checkout functionality.

## 🎯 Features

### Core Functionality
- ✅ **User Authentication** - Secure login/logout with JWT token support
- ✅ **Product Catalog** - Browse products with filtering and search capabilities
- ✅ **Shopping Cart** - Add/remove items, adjust quantities
- ✅ **Selective Checkout** - Choose which items to checkout, leave others in cart
- ✅ **Order Management** - Create and track orders with order line items
- ✅ **User Profiles** - View and edit user profile information
- ✅ **Category & Color Filtering** - Filter products by category and color
- ✅ **Price Range Filtering** - Filter by minimum and maximum price

### User Interface
- 🎨 **Beautiful Gradient Design** - Purple gradient header and modern cards
- 📱 **Responsive Layout** - Works on desktop and mobile devices
- ✨ **Smooth Animations** - Hover effects and transitions
- 🎯 **Intuitive Navigation** - Easy-to-use menu and checkout flow
- 🛒 **Live Cart Counter** - Real-time cart item count in header

## 🛠️ Technology Stack

### Backend
- **Java 17 LTS** - Modern, stable Java version with long-term support
- **Spring Boot 2.7.3** - Rapid application development framework with embedded Tomcat
- **Spring Security** - Comprehensive authentication and authorization framework
- **Spring Data JPA** - Simplified data persistence layer (using JDBC in this project)
- **JWT (JSON Web Tokens)** - Token-based authentication mechanism for stateless API requests
- **MySQL 8.0.33** - Relational database for persistent data storage
- **Maven 3.9+** - Project build and dependency management tool

### Frontend
- **HTML5** - Semantic markup and structure
- **CSS3** - Modern styling with flexbox layouts
- **JavaScript (ES6+)** - Client-side interactivity and DOM manipulation
- **Axios** - Promise-based HTTP client for REST API calls
- **Mustache Templates** - Server-side template rendering for dynamic HTML
- **Bootstrap 5** - Responsive framework utilities (included in dependencies)

## 📋 Prerequisites

- Java 17 JDK
- Maven 3.9+
- MySQL 8.0+
- Git

## 🚀 Getting Started

### 1. Clone the Repository
```bash
git clone <repository-url>
cd capstone-api-starter
```

### 2. Database Setup
Create the database using one of the provided SQL files:
```bash
mysql -u root -p < database/create_database_easyshop.sql
```

### 3. Configure Database Connection
Update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/easyshop
spring.datasource.username=root
spring.datasource.password=your_password
```

### 4. Build the Project
```bash
mvn clean compile
```

### 5. Package the Application
```bash
mvn package -DskipTests
```

### 6. Run the Application
```bash
java -jar target/easyshop-capstone-starter-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:8080`

## 🔐 Authentication

### Default Test Credentials
- **Username:** admin
- **Password:** password

## 📊 Project Structure

```
src/
├── main/
│   ├── java/org/yearup/
│   │   ├── configurations/      # Spring configurations
│   │   ├── controllers/         # REST API endpoints
│   │   │   ├── ProductController.java
│   │   │   ├── ShoppingCartController.java
│   │   │   ├── OrdersController.java
│   │   │   └── ProfileController.java
│   │   ├── data/               # Data access layer
│   │   │   ├── ShoppingCartDao.java
│   │   │   ├── OrderDao.java
│   │   │   └── mysql/          # MySQL implementations
│   │   ├── models/             # Entity models
│   │   │   ├── Product.java
│   │   │   ├── ShoppingCart.java
│   │   │   ├── Order.java
│   │   │   └── OrderLineItem.java
│   │   └── security/           # Security configurations
│   └── resources/
│       ├── application.properties
│       ├── static/
│       │   ├── index.html       # Main entry point
│       │   ├── css/main.css     # Styling
│       │   ├── js/              # JavaScript services
│       │   ├── images/          # Logo and product images
│       │   └── templates/       # HTML templates
└── test/                        # Unit tests
```

## 🔌 API Endpoints

All endpoints require JWT authentication (Bearer token in Authorization header) except login/registration endpoints.

### Products
- **`GET /products`** - Retrieve all products with optional query parameters
  - Query params: `categoryId`, `maxPrice`, `minPrice`, `color`
  - Example: `GET /products?categoryId=1&minPrice=10&maxPrice=100`
  - Returns: Array of Product objects with id, name, description, price, category, color, image URL

- **`GET /products/{id}`** - Get detailed information for a specific product
  - Path param: `id` - Product ID
  - Returns: Single Product object with all details

- **`GET /products/category/{categoryId}`** - Get all products in a specific category
  - Path param: `categoryId` - Category ID
  - Returns: Array of Product objects filtered by category

- **`POST /products`** - Create a new product (admin-only)
  - Request body: Product JSON object
  - Returns: Created Product object
 the Application

### 1. Login to Your Account
- Enter your username and password on the login form
- Test credentials: `admin` / `password`
- JWT token is generated upon successful login and stored in browser
- Token is automatically sent with all subsequent API requests via Authorization header

### 2. Browse the Product Catalog
- The home page displays all available products in a responsive grid
- Products show image, name, price, and category information
- Use the filter sidebar to narrow down your search

### 3. Filter Products
- **By Category**: Select from available product categories using the category dropdown
- **By Color**: Filter products by available colors (displays matching products only)
- **By Price Range**: Use the price slider to set minimum and maximum price
- Filters update product display in real-time as you adjust them

### 4. View Product Details
- Click on any product image or name to see full details
- View comprehensive product information including description, specifications, and pricing

### 5. Add Items to Shopping Cart
- Click the "Add to Cart" button on any product
- Specify the quantity you want to purchase (default: 1)
- Product is immediately added to your cart
- Cart counter in the header updates in real-time

### 6. Manage Your Shopping Cart
- Click the cart icon in the header to view your shopping cart
- **View All Items**: See all products you've added with quantities and prices
- **Update Quantities**: Change the quantity of any item or remove it completely
- **Remove Items**: Click delete button next to any item to remove it
- **Clear Cart**: Clear all items at once to start over
- **Item Subtotals**: See calculated total for each item (price × quantity)
- **Cart Summary**: View total items and total price at the bottom

### 7. Proceed to Checkout
- Review your cart items and quantities
- Click the "Checkout" button to proceed
- Order is created with items from your cart
- Use your profile shipping address for delivery

### 8. Confirm and Complete Order
- Order is processed immediately upon checkout
- Ordered items are removed from your cart
- Any items you didn't order remain in cart for future purchasing
- Order confirmation shows order ID and order date

### 9. Manage Your Profile
- Click "Profile" or user menu in the header
- View your current profile information
- Edit your shipping address:
  - First Name and Last Name
  - Street Address
  - City, State, ZIP code
  - Phone Number
- Click "Save Changes" to update profile
- Profile information is used as shipping address when you create orderst object

- **`DELETE /cart/products/{productId}`** - Remove a specific product from the cart
  - Path param: `productId` - Product ID to remove
  - Returns: Updated ShoppingCart object

### Orders
- **`POST /orders`** - Create an order from current shopping cart items
  - Request body: Optional CheckoutRequest with selectedProductIds (for selective checkout)
  - Request format: `{ "selectedProductIds": [1, 2, 3] }`
  - Process:
    1. Creates an Order with user's profile shipping address
    2. Creates OrderLineItems for each cart item
    3. Removes only the ordered items from the shopping cart
    4. Unordered items remain in cart for future checkout
  - Returns: Created Order object with orderId, date, and shipping details

### User Profile
- **`GET /profile`** - Retrieve current authenticated user's profile
  - Returns: Profile object with firstName, lastName, address, city, state, zip, phone

- **`PUT /profile`** - Update current user's profile information
  - Request body: Profile JSON with fields to update
  - Example body:
    ```json
    {
      "firstName": "John",
      "lastName": "Doe",
      "address": "123 Main St",
      "city": "New York",
      "state": "NY",
      "zip": "10001",
      "phone": "555-0123"
    }
    ```
  - Returns: Updated Profile object

### Categories
- **`GET /categories`** - Retrieve all product categories
  - Returns: Array of Category objects with id, name, description

- **`GET /categories/{id}`** - Get specific category details
  - Path param: `id` - Category ID
  - Returns: Single Category object

- **`GET /categories/{categoryId}/products`** - Get all products in a category
  - Path param: `categoryId` - Category ID
  - Returns: Array of Product objects in that category

- **`POST /categories`** - Create a new category (admin-only)
  - Request body: Category JSON object
  - Returns: Created Category object

- **`PUT /categories/{id}`** - Update a category (admin-only)
  - Path param: `id` - Category ID
  - Request body: Updated Category JSON
  - Returns: Updated Category object

- **`DELETE /categories/{id}`** - Delete a category (admin-only)
  - Path param: `iAdd any product to your cart with custom quantities
- **Adjust Quantities** - Increase or decrease item quantities directly in the cart
- **Remove Individual Items** - Delete specific items from cart without affecting others
- **Clear Entire Cart** - Remove all items at once with a single action
- **Item Pricing Details** - See unit price, quantity, and line item subtotal for each product
- **Real-time Cart Totals** - Displays total item count and total price
- **Cart Persistence** - Cart items are saved in the database and persist between sessions
- **Quantity Validation** - Prevents invalid quantities and maintains data integrity
- **Quick Add from Product Page** - Add products directly from the product listing without navigating to car

### 2. Browse Products
- Use filters to find products by category, color, and price
- Click on product images to view details

### 3. Add to Cart
- Click "Add to Cart" button on any product
- Cart counter updates in real-time

### 4. Review Cart
- Click "View Cart" to see all items
- Adjust quantities as needed
- Each item shows a checkbox for selective checkout

### 5. Selective Checkout
- Uncheck items you don't want to checkout
- Only checked items will be ordered
- Unchecked items remain in cart for later

### 6. Confirm Order
- Click "Checkout" button
- Review order summary
- Confirm to complete purchase

## 📦 Shopping Cart Features

- **Add Items** - Search and add products to cart
- **Adjust Quantities** - Increase or decrease item quantities
- **Remove Items** - Delete specific items from cart
- **Clear Cart** - Remove all items at once
- **Item Selection** - Checkbox for each item to choose what to checkout
- **Cart Totals** - Shows total items and total price
- **Cart Persistence** - Items remain in cart after partial checkout

## 💳Review Cart** - View all items, quantities, and prices in your shopping cart
2. **Confirm Shipping Address** - Verify your profile shipping address will be used for delivery
3. **Initiate Checkout** - Click "Checkout" button to create order
4. **Order Creation** - System creates Order record with:
   - Order ID (auto-generated)
   - Current timestamp
   - Your user ID
   - Shipping address from profile
   - All cart items as OrderLineItems
5. **Cart Clearing** - All ordered items are removed from shopping cart
6. *Layout Architecture
- **Header Navigation** - Persistent header with EasyShop logo, navigation links, and cart counter
- **Main Content Area** - Responsive product grid that adapts to screen size
- **Sidebar Filters** - Collapsible filter panel for category, color, and price range
- **Footer** - Application footer with additional information and links

### Visual Design
- **Color Scheme**:
  - Professional neutrals for backgrounds and text
  - Color-coded action buttons (green for add, red for delete)
  - Blue links for navigation
  - Gray borders and dividers for section separation
- **Typography**: Clean, readable fonts with clear visual hierarchy
  - Large headings for section titles
  - Medium-sized product names and prices
  - Smaller gray text for secondary information

### User Interface Components
- **Product Cards** - Display product image, name, category, color, and price
- **Filter Sidebar** - Category dropdown, color filter, and price range slider
- **Shopping Cart Table** - Shows each item with image, name, quantity, price, and action buttons
- **Input Forms** - Clean, accessible forms for login, registration, and profile updates
- **Action Buttons** - Clearly labeled buttons for add to cart, checkout, save profile, etc.
- **Status Messages** - Alerts and notifications for user
- Modern, clean fonts
- Clear hierarchy with weighted headings
- Easy-to-read body text

### Components
- Gradient header with logo and navigation
- Product cards with hover effects
- Filter sidebar with range sliders
- Shopping cart interface with checkboxes
- Confirmation dialogs before actions

## 🧪 Testing

### Run Unit Tests
```bash
mvn test
```

Available test classes:
- `MySqlProductDaoTest.java` - Tests for product data access layer
- `BaseDaoTestClass.java` - Base class for data access testing with test database setup

### Manual Testing Checklist

#### Authentication
- [ ] Login with valid credentials (admin/password)
- [ ] Try login with invalid credentials - should fail
- [ ] Logout clears JWT token and redirects to login
- [ ] Accessing protected pages without token redirects to login

#### Product Browsing
- [ ] All products load on home page
- [ ] Product cards display image, name, category, and price
- [ ] Product filters are visible and functional
- [ ] Clicking product shows details in modal/detail view

#### Product Filtering
- [ ] Filter by category - only shows products in selected category
- [ ] Filter by color - only shows products with selected color
- [ ] Filter by price range - shows products within selected price range
- [ ] Multiple filters work together (category AND color AND price)
- [ ] Clear filters restores all products

#### Shopping Cart
- [ ] Add single item to cart - item appears in cart with quantity 1
- [ ] Add same item twice - quantity increases to 2 (not duplicate entry)
- [ ] Add multiple different items - all appear in cart
- [ ] Cart counter in header updates correctly
- [ ] Remove item from cart - item disappears
- [ ] Update item quantity - cart updates correctly
- [ ] Clear entire cart - all items removed
- [ ] Cart persists after page refresh (stored in database)

#### Checkout Process
- [ ] Checkout with empty cart - shows error message
- [ ] Checkout with items - order created successfully
- [ ] Order has correct items, quantities, and prices
- [ ] Order uses profile shipping address
- [ ] Order ID is generated and unique
- [ ] After checkout, cart is empty
- [ ] Multiple checkouts create separate order records

#### User Profile
- [ ] View profile shows current user information
- [ ] Edit profile fields - can modify name, address, city, state, zip, phone
- [ ] Save profile - updates are persisted
- [ ] Profile updates are reflected in new orders (shipping address)

## 🐛 Troubleshooting

### Port 8080 Already in Use
```bash
# Kill the process using port 8080
# On Windows:
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

### Database Connection Error
- Verify MySQL is running
- Check database credentials in application.properties
- Ensure database exists and is accessible
� Order Management

Each order contains the following information:
- **Order ID** - Unique identifier (auto-generated)
- **User ID** - Associated customer
- **Order Date** - Timestamp of order creation
- **Shipping Address** - Complete address from user's profile
- **Shipping Amount** - Default set to $0.00
- **Order Line Items** - Array of items ordered with:
  - Order Line Item ID
  - Product ID
  - Sales Price (price at time of order)
  - Quantity ordered
  - Discount percentage applied

Orders are permanent records in the database and cannot be modified after creation.

## 📱 Responsive Design

The application is built with responsive design principles:
- **Desktop (1024px+)**: Full 3-column product grid with expanded sidebar
- **Tablet (768px - 1023px)**: 2-column product grid with collapsible sidebar
- **Mobile (below 768px)**: 1-column product grid with mobile-optimized navigation
- **Navigation**: Hamburger menu on mobile devices
- **Filter Sidebar**: Collapses to icon on smaller screens
- **Product Cards**: Scale appropriately for all screen sizes
- **Images**: Responsive sizing with proper aspect ratio maintenance
[Select Items]
   ↓ (uncheck 1 item)
## 📚 Architecture Overview

### Model-View-Controller (MVC) Pattern
The application follows the classic MVC architecture:

#### Models (Data Layer)
Located in `src/main/java/org/yearup/models/`:
- Represent the core business objects (User, Product, Order, ShoppingCart, etc.)
- Plain Java classes with getters/setters (POJOs)
- No business logic - just data containers

#### Views (Presentation Layer)
Located in `src/main/resources/static/`:
- HTML templates rendered by Mustache template engine
- CSS styling for layout and appearance
- JavaScript for client-side interactivity

#### Controllers (Application Layer)
Located in `src/main/java/org/yearup/controllers/`:
- REST API endpoints using Spring @RestController
- Handle HTTP requests and responses
- Delegate business logic to DAOs
- Return JSON responses for API calls

### Data Access Layer (DAO Pattern)
Located in `src/main/java/org/yearup/data/`:
- **Interfaces**: Define contracts for data operations (ProductDao, ShoppingCartDao, etc.)
- **MySQL Implementations**: Concrete implementations using JDBC (MySqlProductDao, MySqlShoppingCartDao, etc.)
- Handles all database queries and transactions
- Provides CRUD operations for each entity

### Security Layer
Located in `src/main/java/org/yearup/security/`:
- JWT token generation and validation (TokenProvider)
- Spring Security configuration (WebSecurityConfig)
- Authentication entry point and access denied handler
- User details service for loading user information

### Configuration Layer
Located in `src/main/java/org/yearup/configurations/`:
- Database connection configuration
- Application-wide settings and beans
- Dependency injection setup

## 🔧 Development

### Project Structure Details
```
src/main/
├── java/org/yearup/
│   ├── EasyshopApplication.java          # Spring Boot entry point
│   ├── configurations/
│   │   └── DatabaseConfig.java           # Database connection setup
│   ├── controllers/
│   │   ├── ProductsController.java       # Product endpoints
│   │   ├── ShoppingCartController.java   # Cart endpoints
│   │   ├── OrdersController.java         # Order endpoints
│   │   ├── ProfileController.java        # Profile endpoints
│   │   └── CategoriesController.java     # Category endpoints
│   ├── data/
│   │   ├── *Dao.java                     # DAO interfaces
│   │   └── mysql/
│   │       ├── MySql*Dao.java            # MySQL implementations
│   │       └── ConnectionPool.java       # Connection management
│   ├── models/
│   │   ├── User.java
│   │   ├── Product.java
│   │   ├── Order.java
│   │   ├── OrderLineItem.java
│   │   ├── ShoppingCart.java
│   │   └── Profile.java
│   └── security/
│       ├── jwt/
│       │   ├── TokenProvider.java        # JWT token handling
│       │   └── JWTConfigurer.java        # JWT configuration
│       ├── WebSecurityConfig.java        # Security configuration
│       └── User details service
├── resources/
│   ├── application.properties             # Configuration properties
│   ├── banner.txt                         # Application startup banner
│   └── static/
│       ├── index.html                     # Main entry point
│       ├── css/
│       │   └── main.css                   # Application styling
│       ├── js/
│       │   ├── application.js             # Main app initialization
│       │   ├── config.js                  # Client configuration
│       │   ├── filter.js                  # Product filtering logic
│       │   ├── template-builder.js        # Template rendering
│       │   ├── services/
│       │   │   ├── user-service.js        # Auth and user operations
│       │   │   ├── product-service.js     # Product API calls
│       │   │   └── shoppingcart-service.js # Cart operations
│       │   └── lib/                       # Third-party libraries
│       ├── templates/                     # HTML templates
│       │   ├── home.html
│       │   ├── product.html
│       │   ├── cart.html
│       │   ├── profile.html
│       │   └── ...other templates
│       └── images/
│           └── products/                  # Product images
└── test/                                  # Unit tests
```

---

**Version:** 1.0.0  
**Last Updated:** December 18, 2025  
**Status:** Complete and Functional  
**Java Version Required:** 17 LTS  
**Spring Boot Version:** 2.7.3  
**MySQL Version Required:** 8.0+]
   ↓
[1**JWT Token Authentication** - Stateless token-based authentication with token timeout
- **Spring Security** - Comprehensive security framework with method-level access control
- **Password Encryption** - User passwords are encrypted using Spring Security's password encoder
- **CORS Protection** - Cross-Origin Resource Sharing properly configured with @CrossOrigin annotations
- **SQL Injection Prevention** - All database queries use prepared statements with parameterized queries
- **Authorization Checks** - @PreAuthorize annotations on protected endpoints
- **User Isolation** - Users can only access their own cart, profile, and orders via Principal authentication
- **Token Storage** - JWT tokens stored in browser local storage and sent via Authorization header
- **Secure Endpoints** - Admin-only endpoints restricted to authorized user

- Desktop: Full 3-column product grid
- Tablet: 2-column product grid
- Mobile: 1-column product grid
- Sidebar collapses on smaller screens

## 🔐 Security Features

- JWT token-based authentication
- Password encryption
- CORS protection
- SQL injection prevention with prepared statements

## 📄 License

This project is part of a Year Up capstone program.

## 👥 Contributing

This is an educational project. For questions or suggestions, please contact the development team.

## 📞 Support

For issues or questions:
1. Check the troubleshooting section
2. Review the API endpoints documentation
3. Check application logs in console

---

## 📚 Architecture Overview

### Model-View-Controller (MVC) Pattern
The application follows the classic MVC architecture:

#### Models (Data Layer)
Located in `src/main/java/org/yearup/models/`:
- Represent the core business objects (User, Product, Order, ShoppingCart, etc.)
- Plain Java classes with getters/setters (POJOs)
- No business logic - just data containers

#### Views (Presentation Layer)
Located in `src/main/resources/static/`:
- HTML templates rendered by Mustache template engine
- CSS styling for layout and appearance
- JavaScript for client-side interactivity

#### Controllers (Application Layer)
Located in `src/main/java/org/yearup/controllers/`:
- REST API endpoints using Spring @RestController
- Handle HTTP requests and responses
- Delegate business logic to DAOs
- Return JSON responses for API calls

### Data Access Layer (DAO Pattern)
Located in `src/main/java/org/yearup/data/`:
- **Interfaces**: Define contracts for data operations (ProductDao, ShoppingCartDao, etc.)
- **MySQL Implementations**: Concrete implementations using JDBC (MySqlProductDao, MySqlShoppingCartDao, etc.)
- Handles all database queries and transactions
- Provides CRUD operations for each entity

### Security Layer
Located in `src/main/java/org/yearup/security/`:
- JWT token generation and validation (TokenProvider)
- Spring Security configuration (WebSecurityConfig)
- Authentication entry point and access denied handler
- User details service for loading user information

### Configuration Layer
Located in `src/main/java/org/yearup/configurations/`:
- Database connection configuration
- Application-wide settings and beans
- Dependency injection setup

## 🔧 Development

### Project Structure Details
```
src/main/
├── java/org/yearup/
│   ├── EasyshopApplication.java          # Spring Boot entry point
│   ├── configurations/
│   │   └── DatabaseConfig.java           # Database connection setup
│   ├── controllers/
│   │   ├── ProductsController.java       # Product endpoints
│   │   ├── ShoppingCartController.java   # Cart endpoints
│   │   ├── OrdersController.java         # Order endpoints
│   │   ├── ProfileController.java        # Profile endpoints
│   │   └── CategoriesController.java     # Category endpoints
│   ├── data/
│   │   ├── *Dao.java                     # DAO interfaces
│   │   └── mysql/
│   │       ├── MySql*Dao.java            # MySQL implementations
│   │       └── ConnectionPool.java       # Connection management
│   ├── models/
│   │   ├── User.java
│   │   ├── Product.java
│   │   ├── Order.java
│   │   ├── OrderLineItem.java
│   │   ├── ShoppingCart.java
│   │   └── Profile.java
│   └── security/
│       ├── jwt/
│       │   ├── TokenProvider.java        # JWT token handling
│       │   └── JWTConfigurer.java        # JWT configuration
│       ├── WebSecurityConfig.java        # Security configuration
│       └── User details service
├── resources/
│   ├── application.properties             # Configuration properties
│   ├── banner.txt                         # Application startup banner
│   └── static/
│       ├── index.html                     # Main entry point
│       ├── css/
│       │   └── main.css                   # Application styling
│       ├── js/
│       │   ├── application.js             # Main app initialization
│       │   ├── config.js                  # Client configuration
│       │   ├── filter.js                  # Product filtering logic
│       │   ├── template-builder.js        # Template rendering
│       │   ├── services/
│       │   │   ├── user-service.js        # Auth and user operations
│       │   │   ├── product-service.js     # Product API calls
│       │   │   └── shoppingcart-service.js # Cart operations
│       │   └── lib/                       # Third-party libraries
│       ├── templates/                     # HTML templates
│       │   ├── home.html
│       │   ├── product.html
│       │   ├── cart.html
│       │   ├── profile.html
│       │   └── ...other templates
│       └── images/
│           └── products/                  # Product images
└── test/                                  # Unit tests
```

---

**Version:** 1.0.0  
**Last Updated:** December 18, 2025  
**Status:** Complete and Functional  
**Java Version Required:** 17 LTS  
**Spring Boot Version:** 2.7.3  
**MySQL Version Required:** 8.0+
