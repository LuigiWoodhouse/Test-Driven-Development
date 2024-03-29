package org.example.service;

import org.example.exception.ItemNotFoundException;
import org.example.model.Item;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

public interface CatalogService {
    void addItem(String name, BigDecimal price, int qty, MultipartFile image) throws IOException;

    String selectItem(int index);

    boolean increaseItemQuantity(Integer itemQty);

    boolean decreaseItemQuantity(Integer itemQty);

    int getItemQuantity(int item);

    boolean containItems(Item item);

    int getItemQuantityByName(String itemName);

    BigDecimal getItemTotal(String itemName) throws ItemNotFoundException;
}
