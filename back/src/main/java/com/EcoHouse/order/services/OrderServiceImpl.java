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
import com.EcoHouse.product.services.IProductService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShoppingCartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private IProductService productService;

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

                    // 🔥 Reducir el stock del producto
                    productService.reduceStock(
                        cartItem.getProduct().getId(),
                        cartItem.getQuantity()
                    );

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

        // Validar transiciones de estado
        validateStatusTransition(order.getStatus(), status);

        order.setStatus(status);
        return orderRepository.save(order);
    }

    /**
     * Valida que la transición de estado sea lógica
     */
    private void validateStatusTransition(OrderStatus currentStatus, OrderStatus newStatus) {
        // No se puede entregar si no está enviado
        if (newStatus == OrderStatus.DELIVERED && currentStatus != OrderStatus.SHIPPED) {
            throw new RuntimeException("No se puede marcar como DELIVERED sin estar SHIPPED primero");
        }

        // No se puede enviar si está cancelado
        if (currentStatus == OrderStatus.CANCELLED &&
            (newStatus == OrderStatus.SHIPPED || newStatus == OrderStatus.DELIVERED)) {
            throw new RuntimeException("No se puede enviar una orden cancelada");
        }

        // No se puede procesar si ya está entregado
        if (currentStatus == OrderStatus.DELIVERED && newStatus == OrderStatus.PROCESSING) {
            throw new RuntimeException("No se puede procesar una orden ya entregada");
        }
    }

    @Override
    public Order calculateImpact(Long orderId) {
        Order order = getOrderById(orderId);
        order.calculateImpact();
        return orderRepository.save(order);
    }

    @Override
    public Order confirmDelivery(Long orderId) {
        Order order = getOrderById(orderId);

        // Solo se puede confirmar si está enviada
        if (order.getStatus() != OrderStatus.SHIPPED) {
            throw new RuntimeException("Solo se puede confirmar recepción de órdenes enviadas (SHIPPED)");
        }

        order.setStatus(OrderStatus.DELIVERED);
        return orderRepository.save(order);
    }
}
