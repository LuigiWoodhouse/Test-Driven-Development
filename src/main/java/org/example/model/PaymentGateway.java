package org.example.model;

import lombok.Data;
import org.example.service.EmailService;

@Data
public class PaymentGateway {

    private EmailService emailService;
    public String getPaymentPromptMessage() {
        return "Please proceed with the payment.";
    }

    public boolean processPayment() {
        boolean paymentSuccess = true;

        if (paymentSuccess && emailService != null) {
            emailService.sendEmail("payment@123.com", "Payment successful", "Thank you for your payment!");
        }

        return paymentSuccess;
    }

    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }
}

