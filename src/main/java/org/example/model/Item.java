package org.example.model;

import lombok.Data;
@Data
public class Item {
    private String name;
    private double price;
    private Integer qty;
    public Item(String name, double price, Integer qty) {
        this.name = name;
        this.price = price;
        this.qty = qty;
    }
}
