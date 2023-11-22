package org.example.impl;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.OrderNotFoundException;
import org.example.model.Customer;
import org.example.model.Order;
import org.example.repository.CustomerRepository;
import org.example.repository.OrderRepository;
import org.example.service.EmailService;
import org.example.service.OrderService;
import org.example.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {


    @Autowired
    OrderRepository orderRepository;

    @Autowired
    EmailService emailService;


    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public Order customerOrder(Order order, String id){
        log.trace("Enter Method customerOrder: {}", order);


        Customer checkIfCustomerLoggedIn = customerRepository.findById(id);

        try{
            Order newOrder = new Order();

            newOrder.setOrderAmount(order.getOrderAmount());
            newOrder.setCardHolderName(order.getCardHolderName());
            newOrder.setEmail(order.getEmail());
            newOrder.setShippingAddress(order.getShippingAddress());
            newOrder.setBillingAddress(order.getBillingAddress());
            newOrder.setPhoneNumber(order.getPhoneNumber());

            String customerReceipt = sendReceiptRegisteredCustomerEmail(newOrder.getCustomer(), newOrder.getOrderNumber());

            log.info("Return Method customerOrder: Order Was Successful: {}", newOrder);
            emailService.sendPaymentSuccessfulEmail( newOrder.getOrderNumber());
            return orderRepository.save(newOrder);
        }
        catch (Exception e){
            log.error("Return Method customerOrder: an error occurred when making customer order", e);
            throw new RuntimeException("Failed to make customer order");
        }
    }

    private String sendReceiptRegisteredCustomerEmail(Customer customer, String orderNumber) throws OrderNotFoundException {
        log.trace("Enter Method sendReceiptRegisteredCustomerEmail: user={} ,orderNumber={}", customer, orderNumber);

        String toAddress = customer.getEmail();

        Order existingOrder = orderRepository.findByOrderNumber(orderNumber);

        if (existingOrder == null) {
            log.error(("Return Method sendReceiptRegisteredCustomerEmail: an existing order was not found with the order number provided"));
            throw new OrderNotFoundException(HttpStatus.NOT_FOUND.value(), Constants.CUSTOMER_ORDER_NOT_FOUND);
        }

        try {

            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom("Constants.CUSTOMER_RECEIPT_FROM_ADDRESS", "Constants.CUSTOMER_RECEIPT_SENDER_NAME");
            helper.setTo(toAddress);
            helper.setSubject("Constants.CUSTOMER_RECEIPT_SUBJECT");

            String content = String.format("\nOrder Amount: %s USD\nOrder Number: %s\nOrder Date & Time: %s\nName: %s\nEmail: %s",
                    existingOrder.getOrderAmount(), existingOrder.getOrderNumber(),
                    existingOrder.getOrderDate(), existingOrder.getCardHolderName(), existingOrder.getEmail());
            helper.setText(content, false); // Set to false if you want plain text

            javaMailSender.send(message);
            log.info("Return Method sendReceiptRegisteredCustomerEmail: Order Receipt Was Successfully Sent: {}", content);
            return content;
        }
        catch (Exception e) {
            log.error("Return Method sendReceiptRegisteredCustomerEmail: An error occurred while sending receipt " + customer.getEmail() + ": " + e.getMessage(), e);
            throw new RuntimeException("Failed to send order receipt");
        }
    }
}
