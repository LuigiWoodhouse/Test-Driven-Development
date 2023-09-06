package org.example.service;

import org.example.model.Customer;
public interface EmailService {
    Customer sendPaymentSuccessfulEmail(Customer customer);
}