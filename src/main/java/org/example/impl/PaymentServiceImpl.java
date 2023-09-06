package org.example.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.service.PaymentService;
import org.springframework.stereotype.Service;
@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Override
    public boolean makePayment(double amount) {
        log.trace("Enter Method makePayment");

        if (amount > 0) {
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    public boolean processPayment(double amount) {
        log.trace("Enter Method processPayment: {}", amount);

        boolean paymentSuccessful = makePayment(amount);

        if (paymentSuccessful) {
            String receiptMessage = "Payment Successful. Thank you!";
            log.info("Return Method processPayment: Payment is successful: amount={}, receiptMessage={}", amount,receiptMessage);
            return true;
        }
        else{
            log.error("Return Method processPayment: Payment is unsuccessful");
            return false;
        }
    }
}