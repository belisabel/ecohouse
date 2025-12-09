package com.EcoHouse.order.services;

import java.math.BigDecimal;
import java.util.ArrayList;
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
        try {
            // Usar findByCustomerIdWithItems para cargar los items del carrito
            ShoppingCart cart = cartRepository.findByCustomerIdWithItems(customerId)
                    .orElseThrow(() -> new RuntimeException("Carrito no encontrado para el cliente: " + customerId));

            if (cart.getItems() == null || cart.getItems().isEmpty()) {
                throw new RuntimeException("El carrito está vacío");
            }

            // Crear la orden
            Order order = Order.builder()
                    .customer(cart.getCustomer())
                    .orderNumber(UUID.randomUUID().toString())
                    .orderDate(new Date())
                    .status(OrderStatus.PENDING)
                    .build();

            // Crear los OrderItem con referencia bidireccional a la Order
            List<OrderItem> orderItems = new ArrayList<>();

            for (var cartItem : cart.getItems()) {
                // Forzar carga del producto
                var product = cartItem.getProduct();
                if (product == null || product.getId() == null) {
                    throw new RuntimeException("Producto no válido en el carrito");
                }

                // Reducir el stock del producto ANTES de crear el OrderItem
                productService.reduceStock(product.getId(), cartItem.getQuantity());

                // Crear el OrderItem
                OrderItem orderItem = OrderItem.builder()
                        .order(order)
                        .product(product)
                        .quantity(cartItem.getQuantity())
                        .unitPrice(cartItem.getUnitPrice() != null ? cartItem.getUnitPrice() : BigDecimal.ZERO)
                        .subtotal(cartItem.getSubtotal() != null ? cartItem.getSubtotal() : BigDecimal.ZERO)
                        .itemCarbonFootprint(cartItem.getItemCarbonFootprint() != null ? cartItem.getItemCarbonFootprint() : BigDecimal.ZERO)
                        .cO2Saved(cartItem.getCo2Saved() != null ? cartItem.getCo2Saved() : BigDecimal.ZERO)
                        .build();

                orderItems.add(orderItem);
            }

            order.setItems(orderItems);

            // Calcular totales
            order.calculateImpact();

            // Guardar la orden con cascada en items
            Order savedOrder = orderRepository.save(order);

            // Limpiar carrito DESPUÉS de guardar la orden exitosamente
            cartItemRepository.deleteByShoppingCartId(cart.getId());

            return savedOrder;

        } catch (Exception e) {
            // Log del error para debugging
            System.err.println("Error al crear orden para cliente " + customerId + ": " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al crear la orden: " + e.getMessage(), e);
        }
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
