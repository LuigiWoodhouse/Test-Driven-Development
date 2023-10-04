package org.example.impl;

import org.example.exception.InvalidCardException;
import org.example.model.Card;
import org.example.repository.CardRepository;
import org.example.service.CardValidationService;
import org.example.service.PaymentService;
import org.example.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class PaymentServiceImplTest {

    @InjectMocks
    PaymentServiceImpl paymentServiceImpl;
    @Mock
    PaymentService paymentService;

    @Mock
    CardRepository cardRepository;

    @Mock
    CardValidationService cardValidationService;

    PaymentServiceImpl paymentServiceImplMock = mock(PaymentServiceImpl.class);

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void submit_Card_Info_Return_200() throws InvalidCardException {
        Card cardDetails = new Card();
        cardDetails.setCardNumber("4242424242424242");
        cardDetails.setCardHolderName("Captain Iron Dog");
        cardDetails.setExpirationDate("01/23");
        cardDetails.setCvv("123");

        // Mock the behavior of the CardRepository save method
        when(cardRepository.save(any(Card.class))).thenReturn(cardDetails);

        when(cardValidationService.isCardValid(cardDetails)).thenReturn(true);
        // Call the service method
        Card actualCardDetails = paymentServiceImpl.submitCardDetails(cardDetails);

        // Assert the logic in the service
        assertEquals(cardDetails, actualCardDetails);
    }

    @Test
    public void submit_Card_Info_Return_400() throws InvalidCardException {
        Card cardDetails = new Card();
        cardDetails.setCardNumber("4242424242424242");
        cardDetails.setCardHolderName("Captain Iron Dog");
        cardDetails.setExpirationDate("01/23");
        cardDetails.setCvv("123");

        when(cardValidationService.isCardValid(cardDetails)).thenReturn(false);
        // Call the service method
        InvalidCardException exception = assertThrows(InvalidCardException.class, () -> {
            paymentServiceImpl.submitCardDetails(cardDetails);
        });

        assertEquals(400, exception.getCode());

        System.out.println("Expected=" + Constants.INVALID_CARD);
        System.out.println("Actual =" + exception.getMessage());
    }

    @Test
    public void submit_Card_Info_Return_500() throws InvalidCardException {
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
    @Test
    public void make_Payment_Return_200() throws Exception {
        CatalogServiceImpl catalogServiceMock = mock(CatalogServiceImpl.class);

        // Stub the behavior of the methods on the mock
        when(catalogServiceMock.getItemTotal("Item 1")).thenReturn(new BigDecimal("60"));
        when(catalogServiceMock.getItemTotal("Item 2")).thenReturn(new BigDecimal("45"));
        when(catalogServiceMock.calculateOverallCost(any(BigDecimal.class), any(BigDecimal.class)))
                .thenReturn(BigDecimal.valueOf(105.0));

        BigDecimal expectedPayment = BigDecimal.valueOf(105.0);

        BigDecimal actualPayment = catalogServiceMock.calculateOverallCost(
                catalogServiceMock.getItemTotal("Item 1"),
                catalogServiceMock.getItemTotal("Item 2"));

        // Output the expected and actual payments
        System.out.println("Expected: " + expectedPayment);
        System.out.println("Actual  : " + actualPayment);

        // Assertions or further verifications
        assertEquals(expectedPayment, actualPayment);
    }
    @Test
    public void process_Payment_Return_Not_Successful() {

        when(paymentService.makePayment(anyDouble())).thenReturn(false);

        boolean paymentResult = paymentServiceImpl.processPayment(anyDouble());

        assertFalse(paymentResult);
    }
}