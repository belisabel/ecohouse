package com.EcoHouse.shoppingCart.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EcoHouse.user.model.Customer;
import com.EcoHouse.user.repository.CustomerRepository;
import com.EcoHouse.product.model.Product;
import com.EcoHouse.product.repository.ProductRepository;
import com.EcoHouse.shoppingCart.model.CartItem;
import com.EcoHouse.shoppingCart.model.ShoppingCart;
import com.EcoHouse.shoppingCart.repository.CartItemRepository;
import com.EcoHouse.shoppingCart.repository.ShoppingCartRepository;

@Service
public class ShoppingCartServiceImpl implements IShoppingCartService {

    @Autowired
    private ShoppingCartRepository cartRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public ShoppingCart getCartByCustomer(Long customerId) {

        return cartRepository.findByCustomerId(customerId)
                .orElseGet(() -> {
                    Customer customer = customerRepository.findById(customerId)
                            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

                    ShoppingCart sc = new ShoppingCart();
                    sc.setCustomer(customer);
                    sc.setTotalPrice(BigDecimal.ZERO);
                    sc.setEstimatedCarbonFootprint(BigDecimal.ZERO);

                    return cartRepository.save(sc);
                });
    }

    @Override
    public ShoppingCart addItem(Long customerId, Long productId, Integer quantity) {

        ShoppingCart cart = getCartByCustomer(customerId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (item == null) {
            item = new CartItem();
            item.setProduct(product);
            item.setShoppingCart(cart);
            item.setQuantity(0);
            cart.getItems().add(item);
        }

        item.setQuantity(item.getQuantity() + quantity);
        item.setUnitPrice(product.getPrice());
        item.setSubtotal(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())));

        cart.calculateTotal();
        cart.calculateEcoImpact();

        return cartRepository.save(cart);
    }

    @Override
    public ShoppingCart removeItem(Long customerId, Long productId) {

        ShoppingCart cart = getCartByCustomer(customerId);

        cart.getItems().removeIf(i -> i.getProduct().getId().equals(productId));

        cart.calculateTotal();
        cart.calculateEcoImpact();

        return cartRepository.save(cart);
    }

    @Override
    public ShoppingCart updateQuantity(Long customerId, Long productId, Integer quantity) {

        ShoppingCart cart = getCartByCustomer(customerId);

        cart.getItems().forEach(item -> {
            if (item.getProduct().getId().equals(productId)) {
                item.setQuantity(quantity);
                item.setSubtotal(
                        item.getUnitPrice().multiply(BigDecimal.valueOf(quantity))
                );
            }
        });

        cart.calculateTotal();
        cart.calculateEcoImpact();

        return cartRepository.save(cart);
    }

    @Override
    public ShoppingCart decreaseItem(Long customerId, Long productId) {

        ShoppingCart cart = getCartByCustomer(customerId);

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (item == null) return cart;

        int newQty = item.getQuantity() - 1;

        if (newQty <= 0) {
            cart.getItems().remove(item);
        } else {
            item.setQuantity(newQty);
            item.setSubtotal(item.getUnitPrice().multiply(BigDecimal.valueOf(newQty)));
        }

        cart.calculateTotal();
        cart.calculateEcoImpact();

        return cartRepository.save(cart);
    }

    @Override
    public ShoppingCart clearCart(Long customerId) {
        ShoppingCart cart = getCartByCustomer(customerId);
        cart.getItems().clear();
        cart.calculateTotal();
        cart.calculateEcoImpact();
        return cartRepository.save(cart);
    }

    @Override
    public BigDecimal getCartTotal(Long customerId) {
        ShoppingCart cart = getCartByCustomer(customerId);
        return cart.getTotalPrice();
    }

    @Override
    public Integer getItemCount(Long customerId) {
        return getCartByCustomer(customerId)
                .getItems()
                .stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    @Override
    public boolean existsItem(Long customerId, Long productId) {
        return getCartByCustomer(customerId)
                .getItems()
                .stream()
                .anyMatch(item -> item.getProduct().getId().equals(productId));
    }
}
