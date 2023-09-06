package org.example.impl;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Customer;
import org.example.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public Customer sendPaymentSuccessfulEmail(Customer customer) {
        log.trace("Enter Method sendVerificationEmail");

        //Declaring & initializing the contents of the verification email
        String toAddress = customer.getEmail();
        String fromAddress = "luigiwoodhouse@gmail.com";
        String senderName = "TDD Support Team";
        String subject = "Payment Successful";
        String content = "Dear [[name]],<br>" + "Your Payment has been successful:<br>"
                + "Thank you,<br>"
                + "Test";

        //creates a new instance of the MimeMessage class using the javaMailSender object.
        MimeMessage message = javaMailSender.createMimeMessage();

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
            content = content.replace("[[name]]", customer.getName());


            helper.setText(content, true);

            //Send the verification email
            javaMailSender.send(message);
            log.info("Return Method sendPaymentSuccessfulEmail: Payment Successful Email sent {}", customer.getEmail());

            return customer;
        }
        catch (Exception e){
            log.error("Return Method sendPaymentSuccessfulEmail: " +
                    "An error occurred while sending payment successful email to customer with email " + customer.getEmail() + ": " ,e);
            throw new RuntimeException("Failed to send verification email");
        }
    }
}
