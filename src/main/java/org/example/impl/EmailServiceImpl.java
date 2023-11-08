package org.example.impl;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.OrderNotFoundException;
import org.example.model.Customer;
import org.example.model.Order;
import org.example.repository.OrderRepository;
import org.example.service.EmailService;
import org.example.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    OrderRepository orderRepository;

    @Override
    public String sendPaymentSuccessfulEmail( String orderNumber) throws OrderNotFoundException {
        log.trace("Enter Method sendVerificationEmail");

        //Declaring & initializing the contents of the verification email

        String fromAddress = "luigiwoodhouse@gmail.com";
        String senderName = "TDD Support Team";
        String subject = "Payment Successful";
        String content = "Dear [[name]],<br>" + "Your Payment has been successful:<br>"
                + "Thank you,<br>"
                + "Test";

        //creates a new instance of the MimeMessage class using the javaMailSender object.
        MimeMessage message = javaMailSender.createMimeMessage();

        Order existingOrder = orderRepository.findByOrderNumber(orderNumber);

        String toAddress = existingOrder.getEmail();
        if (existingOrder == null) {
            log.error(("Return Method sendReceiptGuestCustomerEmail: an existing order was not found with the order number provided"));
            throw new OrderNotFoundException(HttpStatus.NOT_FOUND.value(), Constants.CUSTOMER_ORDER_NOT_FOUND);
        }

        try {
            //creates a new instance of the MimeMessageHelper class.
            //It takes the MimeMessage instance as a parameter.
            //The MimeMessageHelper class provides various methods to set email properties
            //such as the sender, recipient, subject, content, attachments, etc.
            MimeMessageHelper helper = new MimeMessageHelper(message);

            //Setting the contents of the email in the object "helper"
            helper.setFrom(fromAddress, senderName);
            helper.setTo(toAddress);
            helper.setSubject(subject);

            //Display the first and last name of the user and store it in "name"
            content = String.format("\nOrder Amount: %s USD\nOrder Number: %s\nOrder Date & Time: %s\nName: %s\nEmail: %s",
                    existingOrder.getOrderAmount(),
                    existingOrder.getOrderNumber(),
                    existingOrder.getOrderDate(),
                    existingOrder.getCardHolderName(),
                    existingOrder.getEmail());
            helper.setText(content, false); // Set to false if you want plain text


            helper.setText(content, true);

            //Send the verification email
            javaMailSender.send(message);
            log.info("Return Method sendPaymentSuccessfulEmail: Payment Successful Email sent {}", existingOrder.getEmail());

            return content;
        }
        catch (Exception e){
            log.error("Return Method sendPaymentSuccessfulEmail: " +
                    "An error occurred while sending payment successful email to customer with email " + existingOrder.getEmail() + ": " ,e);
            throw new RuntimeException("Failed to send verification email");
        }
    }
}
