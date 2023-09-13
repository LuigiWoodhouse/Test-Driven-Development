package org.example.service;

import org.example.model.Card;

public interface PaymentService {

    Card submitCardDetails(Card cardDetails);
    boolean makePayment(double amount);

    boolean processPayment(double amount);
}
