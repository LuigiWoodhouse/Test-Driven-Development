package org.example.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.InvalidCardException;
import org.example.model.Card;
import org.example.repository.CardRepository;
import org.example.service.CardValidationService;
import org.example.service.PaymentService;
import org.example.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {


    @Autowired
    CardRepository cardRepository;

    @Autowired
    CardValidationService cardValidationService;

    @Override
    public Card submitCardDetails(Card cardDetails) throws InvalidCardException {
        log.trace("Enter Method submitCardDetails: Card Details:{}", cardDetails);

        // Validate the card details
        if (!cardValidationService.isCardValid(cardDetails)) {
            log.error("Card details are not valid. Cannot submit.");
            throw new InvalidCardException(HttpStatus.BAD_REQUEST.value(), Constants.INVALID_CARD);
        }
        try{
            Card card = new Card();

            card.setCardNumber(card.getCardNumber());
            card.setCardHolderName(card.getCardHolderName());
            card.setExpirationDate(card.getExpirationDate());
            card.setCvv(card.getCvv());

            log.info("Return Method submitCardDetails: Card details submitted successfully");
            return cardRepository.save(card);
        }

        catch (Exception e){
            log.error("Return Method submitCardDetails: an error occurred when submitting card details", e);
            throw new RuntimeException("Failed to submit card");
        }
    }

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