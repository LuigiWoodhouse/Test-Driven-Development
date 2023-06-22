package org.example.model;


import lombok.Data;

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
}

