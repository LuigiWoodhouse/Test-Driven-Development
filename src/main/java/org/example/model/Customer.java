package org.example.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.impl.CatalogServiceImpl;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class Customer {
    private String id;
    private String name;
    private String cart;
    private String email;
    private String address;


    public Customer(String cart) {
        this.cart = cart;
    }
}