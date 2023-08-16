package org.example.model;


import lombok.Data;
import org.example.impl.CatalogServiceImpl;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class Customer {
    private String name;
    private CatalogServiceImpl catalogServiceImpl;
    private Cart cart;

    private String email;

    public Customer(){

    }

    public Customer(String name) {
        this.name = name;
        this.catalogServiceImpl = new CatalogServiceImpl();
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

