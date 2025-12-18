package org.yearup.data.mysql;

import org.springframework.stereotype.Repository;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;

@Repository
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {

    // Constructor receives DataSource from Spring
    public MySqlShoppingCartDao(javax.sql.DataSource dataSource) {
        super(dataSource);
    }

    // Get the shopping cart for a specific user
    @Override
    public ShoppingCart getByUserId(int userId) {
        ShoppingCart cart = new ShoppingCart(); // Create empty shopping cart

        // SQL query to join shopping cart with products
        String sql = "SELECT sc.product_id, sc.quantity, " +
                "p.name, p.price, p.category_id, p.description, p.sub_category, p.stock, p.image_url, p.featured " +
                "FROM shopping_cart sc " +
                "JOIN products p ON sc.product_id = p.product_id " +
                "WHERE sc.user_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId); // Set userId parameter

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Create Product object from database
                    Product product = new Product();
                    product.setProductId(rs.getInt("sc.product_id"));
                    product.setName(rs.getString("p.name"));
                    product.setPrice(rs.getBigDecimal("p.price"));
                    product.setCategoryId(rs.getInt("p.category_id"));
                    product.setDescription(rs.getString("p.description"));
                    product.setSubCategory(rs.getString("p.sub_category"));
                    product.setStock(rs.getInt("p.stock"));
                    product.setImageUrl(rs.getString("p.image_url"));
                    product.setFeatured(rs.getBoolean("p.featured"));

                    // Create ShoppingCartItem and assign product and quantity
                    ShoppingCartItem item = new ShoppingCartItem();
                    item.setProduct(product);
                    item.setQuantity(rs.getInt("sc.quantity")); // Set quantity from database
                    // discountPercent is default 0, no need to set

                    // Add item to cart map (key = productId)
                    cart.getItems().put(product.getProductId(), item);

                    // Add item total to cart total
                    BigDecimal cartTotal = cart.getTotal();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving shopping cart", e);
        }

        return cart; // Return filled shopping cart
    }

    // Add product to shopping cart
    @Override
    public void addProduct(int userId, int productId,int quantity) {
        // SQL inserts new row if product not in cart, otherwise increases quantity by 1
        String sql = "INSERT INTO shopping_cart (user_id, product_id, quantity) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE quantity = quantity + ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);      // set userId
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);
            stmt.setInt( 4,quantity);// set productId

            stmt.executeUpdate();        // execute SQL
        } catch (SQLException e) {
            throw new RuntimeException("Error adding product to cart", e);
        }
    }

    // Update quantity of an existing product in the cart
    @Override
    public void updateQuantity(int userId, int productId, int quantity) {
        String sql = "UPDATE shopping_cart SET quantity = ? WHERE user_id = ? AND product_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, quantity);   // new quantity
            stmt.setInt(2, userId);     // userId
            stmt.setInt(3, productId);  // productId

            stmt.executeUpdate();       // execute SQL
        } catch (SQLException e) {
            throw new RuntimeException("Error updating product quantity in cart", e);
        }
    }

    // Clear all products from a user's shopping cart
    @Override
    public void clearCart(int userId) {
        String sql = "DELETE FROM shopping_cart WHERE user_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId); // set userId
            stmt.executeUpdate();   // execute SQL
        } catch (SQLException e) {
            throw new RuntimeException("Error clearing shopping cart", e);
        }
    }
}
