package org.example.impl;

import org.example.model.Card;
import org.example.service.CardValidationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;

public class CardValidationServiceImplTests {

    @Mock
    CardValidationService cardValidationService;

    Card cardDetails = new Card();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void cardDetails_Card_Number_Is_Mandatory() {

        cardDetails.setCardNumber("2023-1234-1234-1234");

        when(cardValidationService.isCardValid(cardDetails)).thenReturn(true);

        Assertions.assertNotNull(cardDetails.getCardNumber());
        Assertions.assertFalse(cardDetails.getCardNumber().trim().isEmpty());
        Assertions.assertTrue(cardValidationService.isCardValid(cardDetails));
    }

    @Test
    public void cardDetails_Source_Card_Holder_Name_Is_Mandatory() {

        cardDetails.setCardHolderName("BronePeppa");

        when(cardValidationService.isCardValid(cardDetails)).thenReturn(true);

        Assertions.assertNotNull(cardDetails.getCardHolderName());
        Assertions.assertFalse(cardDetails.getCardHolderName().isEmpty());
        Assertions.assertTrue(cardValidationService.isCardValid(cardDetails));
    }

    @Test
    public void cardDetails_Expiration_Date_Is_Mandatory() {

        cardDetails.setExpirationDate("09/20/2023");

        when(cardValidationService.isCardValid(cardDetails)).thenReturn(true);

        Assertions.assertNotNull(cardDetails.getExpirationDate());
        Assertions.assertFalse(cardDetails.getExpirationDate().isEmpty());
        Assertions.assertTrue(cardValidationService.isCardValid(cardDetails));
    }

    @Test
    public void cardDetails_CVV_Is_Mandatory() {

        cardDetails.setCvv("123");

        when(cardValidationService.isCardValid(cardDetails)).thenReturn(true);

        Assertions.assertNotNull(cardDetails.getCvv());
        Assertions.assertFalse(cardDetails.getCvv().isEmpty());
        Assertions.assertTrue(cardValidationService.isCardValid(cardDetails));
    }


    @Test
    public void cardDetails_CVV_Is_Null_Return_Bad_cardDetails() {

        cardDetails.setCvv(null);

        when(cardValidationService.isCardValid(cardDetails)).thenReturn(false);

        Assertions.assertNull(cardDetails.getCvv());
    }

    @Test
    public void  cardDetails_Card_Holder_Name_Is_Null_Return_Bad_cardDetails() {

        cardDetails.setCardHolderName(null);

        when(cardValidationService.isCardValid(cardDetails)).thenReturn(false);

        Assertions.assertNull(cardDetails.getCardHolderName());
    }

    @Test
    public void cardDetails_Card_Expiration_Date_Null_Return_Bad_cardDetails() {

        cardDetails.setExpirationDate(null);

        when(cardValidationService.isCardValid(cardDetails)).thenReturn(false);

        Assertions.assertNull(cardDetails.getExpirationDate());
    }

    @Test
    public void cardDetails_Card_Number_Is_Null_Return_Bad_cardDetails() {

        cardDetails.setCardNumber(null);

        when(cardValidationService.isCardValid(cardDetails)).thenReturn(false);

        Assertions.assertNull(cardDetails.getCardNumber());
    }
}