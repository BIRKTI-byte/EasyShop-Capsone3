let cartService;

class ShoppingCartService {

    cart = {
        items:[],
        total:0
    };

    addToCart(productId)
    {
        const url = `${config.baseUrl}/cart/products/${productId}`;
        // const headers = userService.getHeaders();

        axios.post(url, {})// ,{headers})
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

        axios.get(url)
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
        checkoutButton.addEventListener("click", () => this.checkout());
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

        let div = document.createElement("div");
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
        outerDiv.appendChild(photoDiv);

        let descriptionDiv = document.createElement("div");
        descriptionDiv.innerText = item.product.description;
        outerDiv.appendChild(descriptionDiv);

        let quantityDiv = document.createElement("div")
        quantityDiv.innerText = `Quantity: ${item.quantity}`;
        outerDiv.appendChild(quantityDiv)

        let lineTotalDiv = document.createElement("div");
        lineTotalDiv.style.fontWeight = "bold";
        lineTotalDiv.style.fontSize = "18px";
        lineTotalDiv.style.marginTop = "10px";
        lineTotalDiv.innerText = `Line Total: $${item.lineTotal.toFixed(2)}`;
        outerDiv.appendChild(lineTotalDiv);


        parent.appendChild(outerDiv);
    }

    clearCart()
    {

        const url = `${config.baseUrl}/cart`;

        axios.delete(url)
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

        axios.post(url)
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
}





document.addEventListener('DOMContentLoaded', () => {
    cartService = new ShoppingCartService();

    if(userService.isLoggedIn())
    {
        cartService.loadCart();
    }

});
