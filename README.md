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
- **Java 17** - Programming language
- **Spring Boot 2.7.3** - Application framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Data persistence
- **MySQL 8.0.33** - Database
- **Maven** - Build tool

### Frontend
- **HTML5** - Structure
- **CSS3** - Styling with gradients and animations
- **JavaScript (ES6+)** - Interactivity
- **Axios** - HTTP requests
- **Mustache** - Templating

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

### Products
- `GET /products` - Get all products (with optional filtering)
- `GET /products/{id}` - Get product by ID
- `GET /products/category/{categoryId}` - Get products by category

### Shopping Cart
- `GET /cart` - Get current user's shopping cart
- `POST /cart/products/{productId}` - Add product to cart
- `PUT /cart/products/{productId}` - Update product quantity
- `DELETE /cart` - Clear entire cart
- `DELETE /cart/products/{productId}` - Remove specific product

### Orders
- `POST /orders` - Create order from cart (selective checkout supported)

### User Profile
- `GET /profile` - Get current user's profile
- `PUT /profile` - Update user profile

## 🛍️ How to Use

### 1. Login
- Enter credentials (admin/password for testing)

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

## 💳 Checkout Process

1. **Select Items** - Check/uncheck items for checkout
2. **Review Order** - See items and total before confirming
3. **Create Order** - Order is created with selected items only
4. **Cart Update** - Selected items removed, unselected items remain
5. **Order Confirmation** - Receive order confirmation with order ID

## 🎨 UI Design

### Color Scheme
- **Primary:** Purple (#667eea to #764ba2 gradient)
- **Success:** Green (for add/checkout actions)
- **Danger:** Red (for delete/clear actions)
- **Neutral:** Light gray backgrounds and borders

### Typography
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

### Run Tests
```bash
mvn test
```

### Manual Testing
1. Add 3 items to cart
2. Uncheck 2 items in cart
3. Click "Checkout"
4. Verify only 1 item ordered
5. Verify remaining 2 items stay in cart

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

### CSS Not Loading
- Hard refresh browser (Ctrl+Shift+R)
- Clear browser cache
- Check that static files are in target/classes/static/

## 📝 Database Schema

### Tables
- **users** - User accounts and authentication
- **profiles** - User profile information (address, city, state, zip)
- **products** - Product catalog
- **shopping_cart** - Current user shopping carts
- **orders** - Customer orders
- **order_line_items** - Items in each order
- **categories** - Product categories

## 🔄 Partial Checkout Flow

```
[Shopping Cart]
   ↓ (3 items)
[Select Items]
   ↓ (uncheck 1 item)
[Checkout Selected]
   ↓ (2 items selected)
[Create Order] → Order #123 created
   ↓
[Remove Selected Items from DB]
   ↓
[1 Item Remains in Cart]
```

## 📱 Responsive Design

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

**Version:** 1.0.0  
**Last Updated:** December 18, 2025  
**Status:** Active Development
