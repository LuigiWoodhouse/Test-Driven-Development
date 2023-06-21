package org.example.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Catalog {

    private Map<Integer, Integer> itemInc;
    private List<Item> items;
    public Catalog() {
        items = new ArrayList<>();
        itemInc = new HashMap<>();
    }
    public void addItem(String name, double price, int qty) {
        Item item = new Item(name, price, qty);
        items.add(item);
        itemInc.put(qty, 1);
    }

    public String selectItem(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index).getName();
        }
        else {
            return null;
        }
    }
    public void increaseItemQuantity(Integer item) {
        if (itemInc.containsKey(item)) {
            int currentQuantity = itemInc.get(item);
            itemInc.put(item, currentQuantity + 1);
        }
    }
    public int getItemQuantity(int item) {
        return itemInc.getOrDefault(item, 0);
    }
    public boolean containItems(Item item) {
        return items.contains(item);
    }
}