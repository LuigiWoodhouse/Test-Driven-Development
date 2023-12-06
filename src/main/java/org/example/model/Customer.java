package org.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class Customer {
    private String id;
    private String name;
    private String cart;
    private String email;
    private String address;
    private String shippingAddress;
    private String billingAddress;


    public Customer(String cart) {
        this.cart = cart;
    }
}