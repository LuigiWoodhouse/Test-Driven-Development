package org.example.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.model.Card;
import org.example.service.CardValidationService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CardValidationServiceImpl implements CardValidationService {

    @Override
    public boolean isCardValid(Card cardDetails) {
        log.trace("Enter Method isValid: {}", cardDetails);

        if (cardDetails == null ||
                cardDetails.getCardNumber().trim().isEmpty() ||
                cardDetails.getCardHolderName().trim().isEmpty() ||
                cardDetails.getExpirationDate().trim().isEmpty() ||
                cardDetails.getCvv().isEmpty()) {

            log.error("Return Method isValid: convert rate request is not valid {}", cardDetails);
            return false;
        }
        else {
            log.info("Return Method isValid: convert rate request was validated successfully {}", cardDetails);
            return true;
        }
    }
}