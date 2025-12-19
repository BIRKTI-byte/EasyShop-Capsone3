package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.*;
import org.yearup.models.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/orders")
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class OrdersController
{
    private final OrderDao orderDao;
    private final OrderLineItemDao orderLineItemDao;
    private final ShoppingCartDao shoppingCartDao;
    private final ProfileDao profileDao;
    private final UserDao userDao;

    @Autowired
    public OrdersController(
            OrderDao orderDao,
            OrderLineItemDao orderLineItemDao,
            ShoppingCartDao shoppingCartDao,
            ProfileDao profileDao,
            UserDao userDao)
    {
        this.orderDao = orderDao;
        this.orderLineItemDao = orderLineItemDao;
        this.shoppingCartDao = shoppingCartDao;
        this.profileDao = profileDao;
        this.userDao = userDao;
    }

    // POST /orders - checkout (convert cart to order)
    @PostMapping("")
    public Order checkout(Principal principal, @RequestBody(required = false) CheckoutRequest checkoutRequest)
    {
        try
        {
            // Get current user
            User user = userDao.getByUserName(principal.getName());
            int userId = user.getId();

            // Get user's shopping cart
            ShoppingCart cart = shoppingCartDao.getByUserId(userId);

            if (cart == null || cart.getItems().isEmpty())
            {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Shopping cart is empty.");
            }

            // Get selected product IDs from request (or use all if not specified)
            java.util.List<Integer> selectedProductIds = null;
            if (checkoutRequest != null && checkoutRequest.getSelectedProductIds() != null && !checkoutRequest.getSelectedProductIds().isEmpty())
            {
                selectedProductIds = checkoutRequest.getSelectedProductIds();
            }

            // Get user's profile for shipping address
            Profile profile = profileDao.getByUserId(userId);

            if (profile == null)
            {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User profile not found.");
            }

            // Create new order
            Order order = new Order();
            order.setUserId(userId);
            order.setDate(LocalDateTime.now());
            order.setAddress(profile.getAddress());
            order.setCity(profile.getCity());
            order.setState(profile.getState());
            order.setZip(profile.getZip());
            order.setShippingAmount(BigDecimal.ZERO); // Default shipping amount

            // Insert order into database
            Order createdOrder = orderDao.create(order);

            // Create order line items from cart items (only selected ones)
            for (ShoppingCartItem cartItem : cart.getItems().values())
            {
                // If we have selected product IDs, only process those
                if (selectedProductIds != null && !selectedProductIds.contains(cartItem.getProduct().getProductId()))
                {
                    continue; // Skip unselected items
                }

                OrderLineItem lineItem = new OrderLineItem();
                lineItem.setOrderId(createdOrder.getOrderId());
                lineItem.setProductId(cartItem.getProduct().getProductId());
                lineItem.setSalesPrice(cartItem.getProduct().getPrice());
                lineItem.setQuantity(cartItem.getQuantity());
                lineItem.setDiscount(cartItem.getDiscountPercent());

                orderLineItemDao.create(lineItem);

                // Remove only the ordered item from cart
                shoppingCartDao.removeCartItem(userId, cartItem.getProduct().getProductId());
            }

            return createdOrder;
        }
        catch (ResponseStatusException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }
}
