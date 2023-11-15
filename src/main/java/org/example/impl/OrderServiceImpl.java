package org.example.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.model.Customer;
import org.example.model.Order;
import org.example.repository.CustomerRepository;
import org.example.repository.OrderRepository;
import org.example.service.EmailService;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
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

            log.info("Return Method customerOrder: Order Was Successful: {}", newOrder);
            emailService.sendPaymentSuccessfulEmail( newOrder.getOrderNumber());
            return orderRepository.save(newOrder);
        }
        catch (Exception e){
            log.error("Return Method customerOrder: an error occurred when making customer order", e);
            throw new RuntimeException("Failed to make customer order");
        }
    }
}
