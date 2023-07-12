package org.example.model;


import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class Customer {
    private String name;
    private Catalog catalog;
    private Cart cart;

    public Catalog getCatalog() {
        return catalog;
    }


    public Customer(String name) {
        this.name = name;
        this.catalog = new Catalog();
        this.cart =  new Cart();
    }

    public static boolean checkUniqueCarts(List<Customer> customers) {
        Set<Customer> uniqueCustomers = new HashSet<>();
        for (Customer customer : customers) {
            if (uniqueCustomers.contains(customer)) {
                return false;
            }
            uniqueCustomers.add(customer);
        }
        return true;
    }
}

