package com.EcoHouse.shoppingCart.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.EcoHouse.user.model.Customer;
import com.EcoHouse.user.repository.CustomerRepository;
import com.EcoHouse.product.model.Product;
import com.EcoHouse.product.repository.ProductRepository;
import com.EcoHouse.shoppingCart.model.CartItem;
import com.EcoHouse.shoppingCart.model.ShoppingCart;
import com.EcoHouse.shoppingCart.repository.CartItemRepository;
import com.EcoHouse.shoppingCart.repository.ShoppingCartRepository;

@Service
@Transactional
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
    @Transactional
    public ShoppingCart getCartByCustomer(Long customerId) {
        ShoppingCart cart = cartRepository.findByCustomerIdWithItems(customerId)
                .orElseGet(() -> {
                    Customer customer = customerRepository.findById(customerId)
                            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

                    ShoppingCart sc = new ShoppingCart();
                    sc.setCustomer(customer);
                    sc.setTotalPrice(BigDecimal.ZERO);
                    sc.setEstimatedCarbonFootprint(BigDecimal.ZERO);

                    return cartRepository.save(sc);
                });

        // Filtrar items corruptos o con datos null
        cart.getItems().removeIf(item ->
            item == null ||
            item.getProduct() == null ||
            item.getQuantity() == null ||
            item.getQuantity() <= 0
        );

        boolean needsUpdate = false;

        // Recalcular items con valores null (fix para items existentes)
        for (CartItem item : cart.getItems()) {
            if (item.getUnitPrice() == null || item.getSubtotal() == null) {
                // Cargar producto completo si es necesario
                if (item.getProduct().getPrice() == null) {
                    Product fullProduct = productRepository.findByIdWithRelations(item.getProduct().getId())
                            .orElse(item.getProduct());
                    item.setProduct(fullProduct);
                }

                // Establecer unitPrice
                if (item.getUnitPrice() == null) {
                    item.setUnitPrice(item.getProduct().getPrice());
                    needsUpdate = true;
                }

                // Calcular subtotal
                if (item.getSubtotal() == null) {
                    item.calculateSubtotal();
                    needsUpdate = true;
                }
            }

            // Inicializar certificaciones para evitar LazyInitializationException
            if (item.getProduct() != null && item.getProduct().getCertifications() != null) {
                item.getProduct().getCertifications().size();
            }
        }

        // Si hubo cambios, guardar el carrito
        if (needsUpdate) {
            cart.calculateTotal();
            cart.calculateEcoImpact();
            cart = cartRepository.save(cart);
        }

        return cart;
    }

    @Override
    @Transactional
    public ShoppingCart addItem(Long customerId, Long productId, Integer quantity) {

        ShoppingCart cart = getCartByCustomer(customerId);

        // Usar JOIN FETCH para cargar el producto con todas sus relaciones
        Product product = productRepository.findByIdWithRelations(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (item == null) {
            item = new CartItem();
            item.setProduct(product);
            item.setShoppingCart(cart);
            item.setQuantity(quantity);
            item.setUnitPrice(product.getPrice());
            cart.getItems().add(item);
        } else {
            item.setQuantity(item.getQuantity() + quantity);
        }

        // Asegurar que unitPrice siempre esté actualizado
        if (item.getUnitPrice() == null) {
            item.setUnitPrice(product.getPrice());
        }

        // Calcular subtotal del item
        item.calculateSubtotal();

        // Calcular datos ambientales si están disponibles
        if (product.getEnvironmentalData() != null && product.getEnvironmentalData().getCarbonFootprint() != null) {
            BigDecimal carbonPerUnit = product.getEnvironmentalData().getCarbonFootprint();
            item.setItemCarbonFootprint(carbonPerUnit);
            item.setCo2Saved(carbonPerUnit.multiply(new BigDecimal("0.30"))); // 30% de ahorro vs convencional
        }

        // Calcular totales del carrito
        cart.calculateTotal();
        cart.calculateEcoImpact();

        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public ShoppingCart removeItem(Long customerId, Long productId) {

        ShoppingCart cart = getCartByCustomer(customerId);

        cart.getItems().removeIf(i -> i.getProduct().getId().equals(productId));

        cart.calculateTotal();
        cart.calculateEcoImpact();

        return cartRepository.save(cart);
    }

    @Override
    @Transactional
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
    @Transactional
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
