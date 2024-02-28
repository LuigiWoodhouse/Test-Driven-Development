package org.example.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.OrderResponseDTO;
import org.example.model.Order;
import org.example.service.ViewOrderService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class ViewOrderServiceImpl implements ViewOrderService {

    @Override
    public List<Order> fetchAllCustomerOrdersOfOneCustomer(String userId) throws Exception {
        log.trace("Enter Method fetchAllCustomerOrdersOfOneCustomer:{}", userId);

        try{
            List<Order> customerOrders =  new ArrayList<>();

            List<OrderResponseDTO> orderResponseList = new ArrayList<>();
            for (Order order : customerOrders) {
                OrderResponseDTO orderResponseDTO = convertToOrderResponseDTO(order);
                orderResponseList.add(orderResponseDTO);
            }

            log.info("Exit Method fetchAllCustomerOrdersOfOneCustomer: orders fetched successfully {}", orderResponseList);
            return customerOrders;
        }
        catch (Exception e) {
            log.error("Exit Method fetchAllCustomerOrdersOfOneCustomer: An error occurred while trying to fetch customer orders", e);
            throw new RuntimeException("Failed to fetch orders");
        }
    }

    private OrderResponseDTO convertToOrderResponseDTO(Order order) {
        return new OrderResponseDTO(
                order.getOrderNumber(),
                order.getOrderDate(),
                order.getOrderAmount(),
                order.getCardHolderName(),
                new ArrayList<>()
        );
    }
}
