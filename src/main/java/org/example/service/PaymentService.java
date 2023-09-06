package org.example.service;

public interface PaymentService {
    boolean makePayment(double amount);

    boolean processPayment(double amount);
}
