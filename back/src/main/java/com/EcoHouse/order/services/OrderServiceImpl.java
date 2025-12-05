package com.EcoHouse.order.services;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EcoHouse.order.model.*;
import com.EcoHouse.order.repository.*;
import com.EcoHouse.shoppingCart.model.ShoppingCart;
import com.EcoHouse.shoppingCart.repository.*;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShoppingCartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    @Transactional
    public Order createOrder(Long customerId) {

        ShoppingCart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("El carrito está vacío");
        }

        Order order = new Order();
        order.setCustomer(cart.getCustomer());
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING);

        // Crear los OrderItem con referencia a la Order
        List<OrderItem> orderItems = cart.getItems().stream()
                .map(cartItem -> {

                    OrderItem oi = new OrderItem();
                    oi.setOrder(order);  // 🔥 IMPORTANTE

                    oi.setProduct(cartItem.getProduct());
                    oi.setQuantity(cartItem.getQuantity());
                    oi.setUnitPrice(cartItem.getUnitPrice());
                    oi.setSubtotal(cartItem.getSubtotal());

                    // Carbon footprint (si existe)
                    if (cartItem.getItemCarbonFootprint() != null) {
                        oi.setItemCarbonFootprint(cartItem.getItemCarbonFootprint());
                    }

                    // CO₂ ahorrado opcional
                    if (cartItem.getCo2Saved() != null) {
                        oi.setCO2Saved(cartItem.getCo2Saved());
                    }

                    return oi;
                })
                .toList();

        order.setItems(orderItems);

        // Calcular totales
        order.calculateImpact();

        // Guardar la orden con cascada en items
        Order savedOrder = orderRepository.save(order);

        // limpiar carrito
        cartItemRepository.deleteByShoppingCartId(cart.getId());

        return savedOrder;
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));
    }

    @Override
    public List<Order> getOrdersByCustomer(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = getOrderById(orderId);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    @Override
    public Order calculateImpact(Long orderId) {
        Order order = getOrderById(orderId);
        order.calculateImpact();
        return orderRepository.save(order);
    }
}
