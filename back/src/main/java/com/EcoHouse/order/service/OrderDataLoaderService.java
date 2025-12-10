package com.EcoHouse.order.service;

import com.EcoHouse.order.model.Order;
import com.EcoHouse.order.model.OrderItem;
import com.EcoHouse.order.model.OrderStatus;
import com.EcoHouse.order.model.Payment;
import com.EcoHouse.order.model.ShippingAddress;
import com.EcoHouse.order.repository.OrderRepository;
import com.EcoHouse.product.model.Product;
import com.EcoHouse.product.repository.ProductRepository;
import com.EcoHouse.user.model.Customer;
import com.EcoHouse.user.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderDataLoaderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    /**
     * Carga 10 órdenes de ejemplo completas
     */
    @Transactional
    public List<Order> loadSampleOrders() {
        log.info("📦 Cargando órdenes de ejemplo...");

        List<Customer> customers = customerRepository.findAll();
        List<Product> products = productRepository.findAllWithRelations();

        if (customers.isEmpty() || products.isEmpty()) {
            throw new RuntimeException("No hay clientes o productos para crear órdenes");
        }

        List<Order> orders = new ArrayList<>();

        // Orden 1: Ana García - 3 productos
        Order order1 = createCompletedOrder(
                customers.get(0),
                "ORD-SAMPLE-001",
                45,
                new ShippingAddress("Calle Mayor", "123", "Madrid", "Madrid", "España", "28001")
        );
        addOrderItem(order1, products.get(0), 2);
        addOrderItem(order1, products.get(1), 1);
        addOrderItem(order1, products.get(2), 1);
        orders.add(order1);

        // Orden 2: Carlos López - 2 productos
        Order order2 = createCompletedOrder(
                customers.get(Math.min(1, customers.size() - 1)),
                "ORD-SAMPLE-002",
                38,
                new ShippingAddress("Avenida Libertad", "45", "Barcelona", "Barcelona", "España", "08001")
        );
        addOrderItem(order2, products.get(Math.min(3, products.size() - 1)), 1);
        addOrderItem(order2, products.get(Math.min(4, products.size() - 1)), 2);
        orders.add(order2);

        // Orden 3: María Rodríguez - 4 productos
        Order order3 = createCompletedOrder(
                customers.get(Math.min(2, customers.size() - 1)),
                "ORD-SAMPLE-003",
                32,
                new ShippingAddress("Plaza España", "78", "Valencia", "Valencia", "España", "46001")
        );
        addOrderItem(order3, products.get(Math.min(5, products.size() - 1)), 1);
        addOrderItem(order3, products.get(Math.min(6, products.size() - 1)), 3);
        addOrderItem(order3, products.get(Math.min(7, products.size() - 1)), 1);
        addOrderItem(order3, products.get(Math.min(8, products.size() - 1)), 2);
        orders.add(order3);

        // Orden 4: Pedro Martínez - 2 productos
        Order order4 = createCompletedOrder(
                customers.get(Math.min(3, customers.size() - 1)),
                "ORD-SAMPLE-004",
                28,
                new ShippingAddress("Calle Sol", "12", "Sevilla", "Sevilla", "España", "41001")
        );
        addOrderItem(order4, products.get(Math.min(9, products.size() - 1)), 2);
        addOrderItem(order4, products.get(0), 1);
        orders.add(order4);

        // Orden 5: Laura Sánchez - 3 productos
        Order order5 = createCompletedOrder(
                customers.get(Math.min(4, customers.size() - 1)),
                "ORD-SAMPLE-005",
                25,
                new ShippingAddress("Avenida Constitución", "34", "Málaga", "Málaga", "España", "29001")
        );
        addOrderItem(order5, products.get(1), 1);
        addOrderItem(order5, products.get(Math.min(3, products.size() - 1)), 1);
        addOrderItem(order5, products.get(Math.min(6, products.size() - 1)), 2);
        orders.add(order5);

        // Orden 6: Javier Fernández - 2 productos
        Order order6 = createCompletedOrder(
                customers.get(Math.min(5, customers.size() - 1)),
                "ORD-SAMPLE-006",
                22,
                new ShippingAddress("Calle Comercio", "56", "Bilbao", "Vizcaya", "España", "48001")
        );
        addOrderItem(order6, products.get(2), 2);
        addOrderItem(order6, products.get(Math.min(7, products.size() - 1)), 1);
        orders.add(order6);

        // Orden 7: Sofía Gómez - 5 productos
        Order order7 = createCompletedOrder(
                customers.get(Math.min(6, customers.size() - 1)),
                "ORD-SAMPLE-007",
                18,
                new ShippingAddress("Plaza Mayor", "89", "Zaragoza", "Zaragoza", "España", "50001")
        );
        addOrderItem(order7, products.get(Math.min(4, products.size() - 1)), 1);
        addOrderItem(order7, products.get(Math.min(5, products.size() - 1)), 1);
        addOrderItem(order7, products.get(Math.min(8, products.size() - 1)), 2);
        addOrderItem(order7, products.get(Math.min(9, products.size() - 1)), 1);
        addOrderItem(order7, products.get(0), 1);
        orders.add(order7);

        // Orden 8: Diego Ruiz - 3 productos
        Order order8 = createCompletedOrder(
                customers.get(Math.min(7, customers.size() - 1)),
                "ORD-SAMPLE-008",
                15,
                new ShippingAddress("Calle Victoria", "23", "Granada", "Granada", "España", "18001")
        );
        addOrderItem(order8, products.get(1), 3);
        addOrderItem(order8, products.get(Math.min(3, products.size() - 1)), 2);
        addOrderItem(order8, products.get(Math.min(6, products.size() - 1)), 1);
        orders.add(order8);

        // Orden 9: Elena Torres - 3 productos
        Order order9 = createCompletedOrder(
                customers.get(Math.min(8, customers.size() - 1)),
                "ORD-SAMPLE-009",
                12,
                new ShippingAddress("Avenida Principal", "67", "Murcia", "Murcia", "España", "30001")
        );
        addOrderItem(order9, products.get(2), 1);
        addOrderItem(order9, products.get(Math.min(5, products.size() - 1)), 2);
        addOrderItem(order9, products.get(Math.min(7, products.size() - 1)), 1);
        orders.add(order9);

        // Orden 10: Jorge Vázquez - 4 productos
        Order order10 = createCompletedOrder(
                customers.get(Math.min(9, customers.size() - 1)),
                "ORD-SAMPLE-010",
                8,
                new ShippingAddress("Calle Real", "90", "Alicante", "Alicante", "España", "03001")
        );
        addOrderItem(order10, products.get(Math.min(4, products.size() - 1)), 2);
        addOrderItem(order10, products.get(Math.min(6, products.size() - 1)), 1);
        addOrderItem(order10, products.get(Math.min(8, products.size() - 1)), 2);
        addOrderItem(order10, products.get(Math.min(9, products.size() - 1)), 1);
        orders.add(order10);

        // Calcular totales y actualizar montos de pago
        orders.forEach(order -> {
            order.calculateImpact();
            if (order.getPayment() != null) {
                order.getPayment().setAmount(order.getTotalAmount());
            }
        });

        // Guardar todas las órdenes
        List<Order> savedOrders = orderRepository.saveAll(orders);

        log.info("✅ {} órdenes de ejemplo cargadas", savedOrders.size());
        return savedOrders;
    }

    /**
     * Helper: Crea una orden completada
     */
    private Order createCompletedOrder(Customer customer, String orderNumber, int daysAgo, ShippingAddress address) {
        LocalDateTime orderDateTime = LocalDateTime.now().minusDays(daysAgo);

        Payment payment = Payment.builder()
                .amount(BigDecimal.ZERO)
                .paymentDate(orderDateTime)
                .build();

        Order order = Order.builder()
                .customer(customer)
                .orderNumber(orderNumber)
                .status(OrderStatus.DELIVERED)
                .shippingAddress(address)
                .payment(payment)
                .ecoPointsEarned(30)
                .items(new ArrayList<>())
                .build();

        order.setOrderDate(java.util.Date.from(orderDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant()));
        order.setShippingDate(java.util.Date.from(orderDateTime.plusDays(2).atZone(java.time.ZoneId.systemDefault()).toInstant()));
        order.setDeliveryDate(java.util.Date.from(orderDateTime.plusDays(5).atZone(java.time.ZoneId.systemDefault()).toInstant()));

        return order;
    }

    /**
     * Helper: Agrega un item a una orden
     */
    private void addOrderItem(Order order, Product product, int quantity) {
        OrderItem item = OrderItem.builder()
                .order(order)
                .product(product)
                .quantity(quantity)
                .unitPrice(product.getPrice())
                .build();

        // Establecer datos ambientales si existen
        try {
            if (product.getEnvironmentalData() != null) {
                BigDecimal carbonFootprint = product.getEnvironmentalData().getCarbonFootprint();
                if (carbonFootprint != null) {
                    item.setItemCarbonFootprint(carbonFootprint);
                    item.setCO2Saved(carbonFootprint.multiply(new BigDecimal("0.30")));
                    log.debug("✅ Datos ambientales agregados para producto {} - Huella: {}",
                             product.getId(), carbonFootprint);
                } else {
                    log.warn("⚠️ Producto {} tiene EnvironmentalData pero carbonFootprint es null", product.getId());
                    item.setItemCarbonFootprint(BigDecimal.ZERO);
                    item.setCO2Saved(BigDecimal.ZERO);
                }
            } else {
                log.warn("⚠️ Producto {} no tiene EnvironmentalData", product.getId());
                item.setItemCarbonFootprint(BigDecimal.ZERO);
                item.setCO2Saved(BigDecimal.ZERO);
            }
        } catch (Exception e) {
            log.error("❌ Error al cargar datos ambientales para producto {}: {}",
                     product.getId(), e.getMessage(), e);
            item.setItemCarbonFootprint(BigDecimal.ZERO);
            item.setCO2Saved(BigDecimal.ZERO);
        }

        item.calculateSubtotal();
        order.getItems().add(item);
    }

    /**
     * Eliminar todas las órdenes de ejemplo (que empiezan con ORD-SAMPLE-)
     */
    @Transactional
    public int deleteSampleOrders() {
        List<Order> sampleOrders = orderRepository.findAll().stream()
                .filter(order -> order.getOrderNumber().startsWith("ORD-SAMPLE-"))
                .toList();

        orderRepository.deleteAll(sampleOrders);
        log.info("🗑️ {} órdenes de ejemplo eliminadas", sampleOrders.size());

        return sampleOrders.size();
    }
}

