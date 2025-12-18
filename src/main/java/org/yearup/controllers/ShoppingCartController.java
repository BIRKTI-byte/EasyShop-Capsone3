package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;

import java.security.Principal;

@RestController
@RequestMapping("/cart")
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class ShoppingCartController
{
    private final ShoppingCartDao shoppingCartDao;
    private final UserDao userDao;
    private final ProductDao productDao;

    @Autowired
    public ShoppingCartController(
            ShoppingCartDao shoppingCartDao,
            UserDao userDao,
            ProductDao productDao)
    {
        this.shoppingCartDao = shoppingCartDao;
        this.userDao = userDao;
        this.productDao = productDao;
    }

    // GET /cart - return current user's shopping cart
    @GetMapping("")
    public ShoppingCart getCart(Principal principal)//Json
    {
        try
        {
            User user = userDao.getByUserName(principal.getName());
            return shoppingCartDao.getByUserId(user.getId());
        }
        catch(Exception e)
        {
            e.printStackTrace(); // This will show the real error in console
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    // POST /cart/products/{productId} - add product to cart
    @PostMapping("/products/{productId}")
    public ShoppingCart  addProduct(@PathVariable int productId, Principal principal) {
        try {
            User user = userDao.getByUserName(principal.getName());
            int userId = user.getId();

            shoppingCartDao.addProduct(userId, productId,1);
            // return the updated cart
            return shoppingCartDao.getByUserId(userId);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Oops... our bad."
            );
        }

    }
        // PUT /cart/products/{productId} - update quantity of product
    @PutMapping("/products/{productId}")
    public ShoppingCart updateProduct(
            @PathVariable int productId,
            @RequestBody ShoppingCartItem item,
            Principal principal)
    {
        try
        {
            User user = userDao.getByUserName(principal.getName());
            int userId = user.getId();

            shoppingCartDao.updateQuantity(userId, productId, item.getQuantity());

            // return the updated cart
            return shoppingCartDao.getByUserId(userId);
        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    // DELETE /cart - clear all products from cart
    @DeleteMapping("")
    public ShoppingCart clearCart(Principal principal)
    {
        try
        {
            User user = userDao.getByUserName(principal.getName());
            int userId = user.getId();

            shoppingCartDao.clearCart(userId);

            // return the updated (empty) cart
            return shoppingCartDao.getByUserId(userId);
        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }
}
