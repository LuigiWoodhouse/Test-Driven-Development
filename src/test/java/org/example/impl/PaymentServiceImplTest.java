package org.example.impl;

import org.example.service.PaymentService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class PaymentServiceImplTest {

    @InjectMocks
    PaymentServiceImpl paymentServiceImpl;
    @Mock
    PaymentService paymentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void process_Payment_Return_200() {
        // Arrange
        double amount = 100.0;

        when(paymentService.makePayment(amount)).thenReturn(true);

        boolean paymentResult = paymentServiceImpl.processPayment(amount);

        assertTrue(paymentResult);
    }
}