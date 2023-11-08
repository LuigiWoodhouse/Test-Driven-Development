package org.example.impl;

import org.example.exception.OrderNotFoundException;
import org.example.model.Order;
import org.example.repository.OrderRepository;
import org.example.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
public class OrderServiceImplTests {

    @InjectMocks
    OrderServiceImpl orderServiceImpl;

    @Mock
    OrderRepository orderRepository;

    @Mock
    EmailService emailService;

    OrderServiceImpl orderServiceImplMock = mock(OrderServiceImpl.class);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void customer_Order_Return_200() throws OrderNotFoundException {

        Order newOrder = new Order();
        newOrder.setOrderAmount(new BigDecimal(5000.50));
        newOrder.setCardHolderName("order.getCustomerName()");

        when(emailService.sendPaymentSuccessfulEmail(newOrder.getOrderNumber())).thenReturn(newOrder.getEmail());

        when(orderRepository.save(any(Order.class))).thenReturn(newOrder);

        Order actualOrderDetails = orderServiceImpl.customerOrder(newOrder);

        assertEquals(newOrder, actualOrderDetails);
    }

    @Test
    public void customer_Order_Return_500(){

        Order newOrder = new Order();
        newOrder.setOrderAmount(new BigDecimal(5000.50));
        newOrder.setCardHolderName("order.getCustomerName()");

        doThrow(new RuntimeException("Failed to make customer order"))
                .when(orderServiceImplMock)
                .customerOrder(newOrder);

        // Now, when you call addPet, it should throw the RuntimeException as configured above
        Exception exception = assertThrows(RuntimeException.class, ()
                -> orderServiceImplMock.customerOrder(newOrder));

        // Perform your assertions on the exception or any other expected behavior
        assertEquals("Failed to make customer order", exception.getMessage());
    }
}
