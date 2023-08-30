package org.example.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Item {
    private String name;
    private BigDecimal price;
    private Integer qty;
    public Item(String name, BigDecimal price, Integer qty) {
        this.name = name;
        this.price = price;
        this.qty = qty;
    }

    public Item(String name, double v, int qty) {
        this.name = name;
        this.price = BigDecimal.valueOf(v);
        this.qty = qty;
    }

    public Item(String s, double v) {
    }
}
