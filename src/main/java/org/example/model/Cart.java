package org.example.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class Cart {
    private List<Item> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public List<Item> getItems() {
        return items;
    }
}

