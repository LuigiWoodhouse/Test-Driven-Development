package org.example.service;

import org.example.exception.OrderNotFoundException;
import org.example.model.Customer;
public interface EmailService {
    String sendPaymentSuccessfulEmail(String orderNumber) throws OrderNotFoundException;
}