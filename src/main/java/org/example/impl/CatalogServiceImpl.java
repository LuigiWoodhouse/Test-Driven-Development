package org.example.impl;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import lombok.Data;
import org.example.exception.ItemNotFoundException;
import org.example.model.Customer;
import org.example.model.Item;
import org.example.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class CatalogServiceImpl implements CatalogService {

    private Map<Integer, Integer> itemInc;
    private static List<Item> items;

    @Value("${azure.blob.container.name.shop}")
    String blobContainerName;

    @Autowired
    BlobServiceClient blobServiceClient;

    private Map<String, Item> itemsMap = new HashMap<>();
    public CatalogServiceImpl() {
        items = new ArrayList<>();
        itemInc = new HashMap<>();
    }

    @Override
    public void addItem(String name, BigDecimal price, int qty, MultipartFile image) throws IOException {
        Item item = new Item(name, price, qty);
        items.add(item);
        itemInc.put(qty, 1);

        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(blobContainerName);
        String imageName = item.getName() + item.getItemId();
        BlobClient blobClient = containerClient.getBlobClient(imageName);
        blobClient.upload(image.getInputStream(), image.getSize(), true);
    }

    @Override
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

    @Override
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

    @Override
    public boolean decreaseItemQuantity(Integer itemQty) {
        boolean itemFound = false;
        for (Item item : items) {
            if (item.getQty() == itemQty) {
                int currentQuantity = itemInc.get(itemQty);
                itemInc.put(itemQty, currentQuantity - 1);
                itemFound = true;
                break;
            }
        }
        return itemFound;
    }


//    public void decreaseItemQuantity(Integer item) {
//        if (itemInc.containsKey(item)) {
//            int currentQuantity = itemInc.get(item);
//            if (currentQuantity > 1) {
//                itemInc.put(item, currentQuantity - 1);
//            }
//            //if item is below 1 remove it from the cart
//            else if (currentQuantity < 1) {
//                itemInc.remove(item);
//            }
//        }
//    }

    @Override
    public int getItemQuantity(int item) {
        return itemInc.getOrDefault(item, 0);
    }
//    public boolean containItems(String item) {
//        return items.contains(item);
//    }


    @Override
    public boolean containItems(Item item) {
        return items.contains(item);
    }


    @Override
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


    @Override
    public BigDecimal getItemTotal(String itemName) throws ItemNotFoundException {
        BigDecimal total = BigDecimal.valueOf(0.0);  // Initialize total to zero

        for (Item item : items) {
            if (item.getName().equals(itemName)) {
                total = item.getPrice().multiply(BigDecimal.valueOf(item.getQty()));
                return total;  // Return total immediately if item is found
            }
        }

        // If item is not found, throw an ItemNotFoundException
        throw new ItemNotFoundException(HttpStatus.NOT_FOUND.value(), "Item not found");
    }

    public List<Item> getItems() {
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

    public static boolean checkUniqueCarts(List<Customer> customers) {
        Set<Customer> uniqueCustomers = new HashSet<>();
        for (Customer customer : customers) {
            System.out.println("Customer: " + customer.getId() + ", Cart: " + customer.getCart());

            if (uniqueCustomers.contains(customer)) {
                return false;
            }
            uniqueCustomers.add(customer);
        }
        return true;
    }

}
