package org.example.model;

import lombok.Data;

@Data
public class Card {
    private String cardNumber;
    private String cardHolderName;
    private String expirationDate;
    private String cvv;
}
