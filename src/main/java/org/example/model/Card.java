package org.example.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class Card {
    @NotBlank
    @NotNull
    private String cardNumber;

    @NotBlank
    @NotNull
    private String cardHolderName;

    @NotNull
    @NotBlank
    private String expirationDate;

    @NotNull
    @NotBlank
    private String cvv;
}