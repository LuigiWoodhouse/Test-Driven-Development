package org.example.impl;

import org.example.service.EmailService;

public class EmailServiceMock implements EmailService {
    private boolean emailSent;

    public void sendEmail(String recipient, String subject, String message) {
        // Simulate sending email
        emailSent = true;
    }

    public boolean isEmailSent() {
        return emailSent;
    }
}
