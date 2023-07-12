package org.example.model;

import lombok.Data;

import java.math.BigDecimal;
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
    public void addItem(String name, BigDecimal price, int qty) {
        Item item = new Item(name, price, qty);
        items.add(item);
        itemInc.put(qty, 1);
    }

    public String selectItem(int index) {
        try {
            if (index >= 0 && index < items.size()) {
                return items.get(index).getName();
            }
        } catch (IndexOutOfBoundsException e) {
            // Handle the exception
        }

        return null;
    }

    public boolean increaseItemQuantity(Integer itemQty) {
        boolean itemFound = false;
        for (Item item : items) {
            if (item.getQty() == itemQty) {
                int currentQuantity = itemInc.get(itemQty);
                itemInc.put(itemQty, currentQuantity + 1);
                itemFound = true;
                break;
            }
        }
        return itemFound;
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
//    public boolean containItems(String item) {
//        return items.contains(item);
//    }

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

    public BigDecimal getItemTotal(String itemName) {
      BigDecimal total = BigDecimal.valueOf(0.0);

        // Iterate over the items in the cart
        for (Item item : items) {
            // If the item name matches, add the price
            if (item.getName().equals(itemName)) {
                total = item.getPrice().multiply(BigDecimal.valueOf(item.getQty()));
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

    public BigDecimal calculateExpectedCost(BigDecimal price, int quantity) {
        BigDecimal itemQuantity = BigDecimal.valueOf(quantity);
        return price.multiply(itemQuantity);
    }


    public BigDecimal calculateOverallCost(BigDecimal subtotalItem1, BigDecimal subtotalItem2) {
    return subtotalItem1.add(subtotalItem2);
  }

}
