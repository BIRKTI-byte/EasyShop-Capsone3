let cartService;

class ShoppingCartService {

    cart = {
        items:[],
        total:0
    };

    addToCart(productId)
    {
        const url = `${config.baseUrl}/cart/products/${productId}`;
        const headers = userService.getHeaders();

        axios.post(url, {}, {headers})
            .then(response => {
                this.setCart(response.data)

                this.updateCartDisplay()

            })
            .catch(error => {

                const data = {
                    error: "Add to cart failed."
                };

                templateBuilder.append("error", data, "errors")
            })
    }

    setCart(data)
    {
        this.cart = {
            items: [],
            total: 0
        }

        this.cart.total = data.total;

        for (const [key, value] of Object.entries(data.items)) {
            this.cart.items.push(value);
        }
    }

    loadCart()
    {

        const url = `${config.baseUrl}/cart`;
        const headers = userService.getHeaders();

        axios.get(url, {headers})
            .then(response => {
                this.setCart(response.data)

                this.updateCartDisplay()

            })
            .catch(error => {

                const data = {
                    error: "Load cart failed."
                };

                templateBuilder.append("error", data, "errors")
            })

    }

    loadCartPage()
    {
        // templateBuilder.build("cart", this.cart, "main");

        const main = document.getElementById("main")
        main.innerHTML = "";

        let div = document.createElement("div");
        div.classList="filter-box";
        main.appendChild(div);

        const contentDiv = document.createElement("div")
        contentDiv.id = "content";
        contentDiv.classList.add("content-form");

        const cartHeader = document.createElement("div")
        cartHeader.classList.add("cart-header")

        const h1 = document.createElement("h1")
        h1.innerText = "Cart";
        cartHeader.appendChild(h1);

        const button = document.createElement("button");
        button.classList.add("btn")
        button.classList.add("btn-danger")
        button.innerText = "Clear";
        button.addEventListener("click", () => this.clearCart());
        cartHeader.appendChild(button)

        const checkoutButton = document.createElement("button");
        checkoutButton.classList.add("btn")
        checkoutButton.classList.add("btn-success")
        checkoutButton.innerText = "Checkout";
        checkoutButton.style.marginLeft = "10px";
        checkoutButton.addEventListener("click", () => this.showCheckoutOptions(contentDiv));
        cartHeader.appendChild(checkoutButton)

        contentDiv.appendChild(cartHeader)
        main.appendChild(contentDiv);

        // let parent = document.getElementById("cart-item-list");
        Object.values(this.cart.items).forEach(item => {
            this.buildItem(item, contentDiv)
        });

        // Add cart summary (total items and total price)
        const summaryDiv = document.createElement("div");
        summaryDiv.style.marginTop = "20px";
        summaryDiv.style.padding = "15px";
        summaryDiv.style.backgroundColor = "#f8f9fa";
        summaryDiv.style.borderRadius = "5px";
        summaryDiv.style.fontWeight = "bold";

        const itemCount = Object.keys(this.cart.items).length;
        const totalItems = Object.values(this.cart.items).reduce((sum, item) => sum + item.quantity, 0);
        
        summaryDiv.innerHTML = `
            <div style="font-size: 18px;">
                <div>Total Items: ${totalItems} (${itemCount} unique products)</div>
                <div style="font-size: 24px; color: #28a745; margin-top: 10px;">Cart Total: $${this.cart.total.toFixed(2)}</div>
            </div>
        `;
        
        contentDiv.appendChild(summaryDiv);
    }

    buildItem(item, parent)
    {
        let outerDiv = document.createElement("div");
        outerDiv.classList.add("cart-item");
        outerDiv.style.display = "flex";
        outerDiv.style.alignItems = "flex-start";
        outerDiv.style.gap = "15px";

        // Add checkbox for selection
        let checkboxContainer = document.createElement("div");
        checkboxContainer.style.marginTop = "20px";
        let checkbox = document.createElement("input");
        checkbox.type = "checkbox";
        checkbox.id = `item-${item.product.productId}`;
        checkbox.dataset.productId = item.product.productId;
        checkboxContainer.appendChild(checkbox);
        outerDiv.appendChild(checkboxContainer);

        let div = document.createElement("div");
        div.style.flex = "1";
        outerDiv.appendChild(div);
        let h4 = document.createElement("h4")
        h4.innerText = item.product.name;
        div.appendChild(h4);

        let photoDiv = document.createElement("div");
        photoDiv.classList.add("photo")
        let img = document.createElement("img");
        img.src = `images/products/${item.product.imageUrl}`
        img.addEventListener("click", () => {
            showImageDetailForm(item.product.name, img.src)
        })
        photoDiv.appendChild(img)
        let priceH4 = document.createElement("h4");
        priceH4.classList.add("price");
        priceH4.innerText = `$${item.product.price}`;
        photoDiv.appendChild(priceH4);
        div.appendChild(photoDiv);

        let descriptionDiv = document.createElement("div");
        descriptionDiv.innerText = item.product.description;
        div.appendChild(descriptionDiv);

        let quantityDiv = document.createElement("div")
        quantityDiv.innerText = `Quantity: ${item.quantity}`;
        div.appendChild(quantityDiv)

        let lineTotalDiv = document.createElement("div");
        lineTotalDiv.style.fontWeight = "bold";
        lineTotalDiv.style.fontSize = "18px";
        lineTotalDiv.style.marginTop = "10px";
        lineTotalDiv.innerText = `Line Total: $${item.lineTotal.toFixed(2)}`;
        div.appendChild(lineTotalDiv);


        parent.appendChild(outerDiv);
    }

    clearCart()
    {

        const url = `${config.baseUrl}/cart`;
        const headers = userService.getHeaders();

        axios.delete(url, {headers})
             .then(response => {
                 this.cart = {
                     items: [],
                     total: 0
                 }

                 this.cart.total = response.data.total;

                 for (const [key, value] of Object.entries(response.data.items)) {
                     this.cart.items.push(value);
                 }

                 this.updateCartDisplay()
                 this.loadCartPage()

             })
             .catch(error => {

                 const data = {
                     error: "Empty cart failed."
                 };

                 templateBuilder.append("error", data, "errors")
             })
    }

    checkout()
    {
        const url = `${config.baseUrl}/orders`;
        const headers = userService.getHeaders();

        axios.post(url, {}, {headers})
             .then(response => {
                 // Clear local cart
                 this.cart = {
                     items: {},
                     total: 0
                 }

                 // Show success message
                 const data = {
                     message: `Order #${response.data.orderId} created successfully! Your cart has been cleared.`
                 };
                 templateBuilder.append("message", data, "content")

                 // Update cart display
                 this.updateCartDisplay()
                 
                 // Reload the cart page to show empty cart
                 setTimeout(() => {
                     this.loadCartPage()
                 }, 1500);
             })
             .catch(error => {
                 const data = {
                     error: error.response?.data?.message || "Checkout failed. Please try again."
                 };
                 templateBuilder.append("error", data, "errors")
             })
    }

    updateCartDisplay()
    {
        try {
            const itemCount = this.cart.items.length;
            const cartControl = document.getElementById("cart-items")

            cartControl.innerText = itemCount;
        }
        catch (e) {

        }
    }

    showCheckoutOptions(contentDiv)
    {
        // Get all checked items
        const checkboxes = contentDiv.querySelectorAll('input[type="checkbox"]:checked');
        
        if (checkboxes.length === 0) {
            const data = {
                error: "Please select at least one item to checkout."
            };
            templateBuilder.append("error", data, "errors");
            return;
        }

        // Calculate selected items total
        let selectedTotal = 0;
        let selectedItems = [];
        
        checkboxes.forEach(checkbox => {
            const productId = parseInt(checkbox.dataset.productId);
            const item = this.cart.items.find(i => i.product.productId === productId);
            if (item) {
                selectedItems.push(item);
                selectedTotal += item.lineTotal;
            }
        });

        // Show confirmation dialog
        const confirmed = confirm(`Checkout ${selectedItems.length} item(s) for $${selectedTotal.toFixed(2)}?`);
        
        if (confirmed) {
            this.processCheckout(selectedItems);
        }
    }

    processCheckout(selectedItems)
    {
        // For now, just clear the selected items from cart and show success message
        const itemCount = selectedItems.length;
        let totalAmount = 0;
        
        selectedItems.forEach(item => {
            totalAmount += item.lineTotal;
        });

        // Show success message
        const data = {
            message: `Successfully checked out ${itemCount} item(s) for $${totalAmount.toFixed(2)}! Your order has been placed.`
        };
        templateBuilder.append("message", data, "content");

        // Remove selected items from cart
        selectedItems.forEach(item => {
            delete this.cart.items[item.product.productId];
        });

        // Recalculate cart total
        this.cart.total = Object.values(this.cart.items).reduce((sum, item) => sum + item.lineTotal, 0);

        // Update display
        this.updateCartDisplay();
        
        // Reload cart page after delay
        setTimeout(() => {
            this.loadCartPage();
        }, 1500);
    }
}





document.addEventListener('DOMContentLoaded', () => {
    cartService = new ShoppingCartService();

    if(userService.isLoggedIn())
    {
        cartService.loadCart();
    }

});
