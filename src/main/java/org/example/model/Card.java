package org.example.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Card {
    @NotBlank
    private String cardNumber;
    @NotBlank
    private String cardHolderName;
    @NotBlank
    private String expirationDate;
    @NotBlank
    private String cvv;
}