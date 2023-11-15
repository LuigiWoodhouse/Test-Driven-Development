package org.example.service;

import org.example.model.Order;

public interface OrderService {

    Order customerOrder(Order order, String id);
}
