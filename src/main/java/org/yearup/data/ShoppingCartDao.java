package org.yearup.data;

import org.yearup.models.ShoppingCart;



public interface ShoppingCartDao {
    ShoppingCart getByUserId(int userId);

    // Add these methods
    void addProduct(int userId, int productId, int quantity);
    void updateQuantity(int userId, int productId, int quantity);
    void clearCart(int userId);
}
