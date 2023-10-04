package org.example.service;

import org.example.exception.InvalidCardException;
import org.example.model.Card;

public interface PaymentService {

    Card submitCardDetails(Card cardDetails) throws InvalidCardException;
    boolean makePayment(double amount);

    boolean processPayment(double amount);
}
