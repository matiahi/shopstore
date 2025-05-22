
// //load navbar
// document.addEventListener("DOMContentLoaded", async function () {
//   try {
//     const res = await fetch("/nav.html");
//     const data = await res.text();

//     const container = document.createElement("div");
//     container.innerHTML = data;
//     document.body.insertBefore(container, document.body.firstChild);

//     updateCartCount();
//     attachCartFunctions();
//   } catch (error) {
//     console.error("Failed to load navbar", error);
//   }    
// });
      // update the count in the top badge

      function updateCartCount() {
        const cart = JSON.parse(localStorage.getItem("cart")) || [];
        const count = cart.reduce((sum, item) => sum + (item.quantity || 1) , 0);
        const badge = document.getElementById("cartCount");
        if (badge) badge.textContent = count;
      }
     
      // Run on page load
      document.addEventListener("DOMContentLoaded", updateCartCount);

      // Display cart content in a modal

      let cartModal = null;

      function showCart() {
        const cart = JSON.parse(localStorage.getItem("cart")) || [];
        const container = document.getElementById("cartItemContainer");
        container.innerHTML = "";

        let total = 0;

        if (cart.length === 0) {
          container.innerHTML = "<p>Your cart is empty.</p>";
        } else {
          cart.forEach((item, index) => {
            total += item.price * item.quantity;

            const row = document.createElement("div");
            row.className =
              "d-flex justify-content-between align-items-center ";
            row.innerHTML = `
                <div class = "d-flex align-items-center">
                  <img src = "${item.imageUrl}" alt="${item.name}" style="width: 60px; height: 60px; object-fit: contain;" class = "me-3">
                  <div>
                    <h6 class="mb-0">${item.name}</h6>
                    <small>Price: $${item.price} * ${item.quantity}</small>
                  </div>
                </div>
                <div>
                  <button class="btn btn-sm btn-danger" onclick="removeFromCart(${index})">Remove</button>
                </div>
              `;
            container.appendChild(row);
          });
        }

        document.getElementById("cartTotal").textContent = total.toFixed(2);

        //create the modal only once
        if (!cartModal) {
          cartModal = new bootstrap.Modal(document.getElementById("cartModal"));
        }

        cartModal.show();
      }
      // Remove item from cart
      function removeFromCart(index) {
        let cart = JSON.parse(localStorage.getItem("cart")) || [];
        cart.splice(index, 1);
        localStorage.setItem("cart", JSON.stringify(cart));
        updateCartCount();
        showCart(); //Redisplay the cart with the updated list
      }

// Run on initial page load
      window.onload = updateCartCount;

      document
        .getElementById("cartModal")
        .addEventListener("hidden.bs.modal", function () {
          document
            .querySelectorAll(".modal-backdrop")
            .forEach((el) => el.remove());
          document.body.classList.remove("modal-open");
          document.body.style = "";
        });
// checkout button
 function attachCartFunctions () {
  const checkoutBtn = document
        .getElementById("checkoutBtn");
        if (checkoutBtn) {
          checkoutBtn.addEventListener("click", function () {
          const cart = JSON.parse(localStorage.getItem("cart")) || [];
          if (cart.length === 0) {
            alert("Your cart is empty.");
            return;
          }
          window.location.href = "checkout.html";
        });
      }};

    
   