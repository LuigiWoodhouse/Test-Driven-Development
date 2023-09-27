package org.example.impl;

import org.example.model.Card;
import org.example.repository.CardRepository;
import org.example.service.PaymentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PaymentServiceImplTest {

    @InjectMocks
    PaymentServiceImpl paymentServiceImpl;
    @Mock
    PaymentService paymentService;

    @Mock
    CardRepository cardRepository;

    PaymentServiceImpl paymentServiceImplMock = mock(PaymentServiceImpl.class);

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void submit_Card_Info_Return_200() {
        Card cardDetails = new Card();
        cardDetails.setCardNumber("4242424242424242");
        cardDetails.setCardHolderName("Captain Iron Dog");
        cardDetails.setExpirationDate("01/23");
        cardDetails.setCvv("123");

        // Mock the behavior of the CardRepository save method
        when(cardRepository.save(any(Card.class))).thenReturn(cardDetails);

        // Call the service method
        Card actualCardDetails = paymentServiceImpl.submitCardDetails(cardDetails);

        // Assert the logic in the service
        assertEquals(cardDetails, actualCardDetails);
    }

    @Test
    public void submit_Card_Info_Return_500() {
        Card cardDetails = new Card();
        cardDetails.setCardNumber("4242424242424242");
        cardDetails.setCardHolderName("Captain Iron Dog");
        cardDetails.setExpirationDate("01/23");
        cardDetails.setCvv("123");


        Mockito.when(paymentServiceImplMock.submitCardDetails(Mockito.any(Card.class)))
                .thenThrow(new RuntimeException("Failed to submit card"));


        Exception exception = assertThrows(RuntimeException.class, ()
                -> paymentServiceImplMock.submitCardDetails(cardDetails));

        assertEquals("Failed to submit card", exception.getMessage());

        System.out.println("Expected=Failed to submit card");
        System.out.println("Actual=" + exception.getMessage());
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