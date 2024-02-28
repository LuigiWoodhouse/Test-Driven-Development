package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.model.Item;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderResponseDTO {

    private String orderNumber;
    private Date orderDate;
    private BigDecimal orderAmount;
    private String cardHolderName;
    private List<Item> items;
}