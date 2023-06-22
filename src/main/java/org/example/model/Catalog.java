package org.example.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Catalog {

    private Map<Integer, Integer> itemInc;
    private static List<Item> items;


    private Map<String, Item> itemsMap = new HashMap<>();
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
    public void decreaseItemQuantity(Integer item) {
        if (itemInc.containsKey(item)) {
            int currentQuantity = itemInc.get(item);
            if (currentQuantity > 1) {
                itemInc.put(item, currentQuantity - 1);
            }
            //if item is below 1 remove it from the cart
            else if (currentQuantity < 1) {
                itemInc.remove(item);
            }
        }
    }
    public int getItemQuantity(int item) {
        return itemInc.getOrDefault(item, 0);
    }
    public boolean containItems(Item item) {
        return items.contains(item);
    }

    public int getItemQuantityByName(String itemName) {
        int quantity = 0;

        // Iterate over the items in the catalog
        for (Item item : items) {
            // If the item name matches, add the quantity
            if (item.getName().equals(itemName)) {
                quantity += item.getQty();
            }
        }
        // Return the total quantity
        return quantity;
    }

    public double getItemTotal(String itemName) {
        double total = 0.0;

        // Iterate over the items in the cart
        for (Item item : items) {
            // If the item name matches, add the price
            if (item.getName().equals(itemName)) {
                total += item.getPrice() * item.getQty();
            }
        }

        // Return the total price
        return total;
    }
    public static List<Item> getItems() {
        return items;
    }

    public boolean processPayment(double amount) {
        // Simulate payment processing logic
        // For the sake of this example, assume all payments are successful
        // You can add your actual payment gateway integration code here

        // Return true to indicate a successful payment
        return true;
    }

}