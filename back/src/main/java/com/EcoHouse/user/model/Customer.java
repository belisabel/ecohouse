package com.EcoHouse.user.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "customers")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Customer extends User {

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "billing_address")
    private String billingAddress;

    @Column(length = 20)
    private String phone;

    @Column(name = "carbon_footprint")
    private Double carbonFootprint;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<com.EcoHouse.order.model.Order> orderHistory;
}
