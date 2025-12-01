package com.EcoHouse.customer.model;

import com.EcoHouse.order.model.Order;
import com.EcoHouse.user.model.User;
//import com.EcoHouse.wallet.model.EcoWallet;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "customers")
public class Customer extends User {

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "billing_address")
    private String billingAddress;

    @Column(length = 20)
    private String phone;

   // @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
   // private EcoWallet ecoWallet;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orderHistory;
}

