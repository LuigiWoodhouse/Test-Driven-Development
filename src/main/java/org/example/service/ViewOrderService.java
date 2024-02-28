package org.example.service;

import org.example.model.Order;

import java.util.List;

public interface ViewOrderService {
    List<Order> fetchAllCustomerOrdersOfOneCustomer(String userId) throws Exception;
}
