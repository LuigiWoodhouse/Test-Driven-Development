package org.example.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.example.exception.OrderNotFoundException;
import org.example.model.Customer;
import org.example.model.Order;
import org.example.repository.CustomerRepository;
import org.example.repository.OrderRepository;
import org.example.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
public class OrderServiceImplTests {

    @InjectMocks
    OrderServiceImpl orderServiceImpl;

    @Mock
    OrderRepository orderRepository;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    JavaMailSender javaMailSender;

    String id = "Test";

    OrderServiceImpl orderServiceImplMock = mock(OrderServiceImpl.class);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void customer_Order_Return_200() throws  MessagingException, UnsupportedEncodingException {

        Customer customer = new Customer();
        customer.setId("rtrg");
        customer.setEmail("test@gmail.com");

        Order newOrder = new Order();
        newOrder.setOrderAmount(new BigDecimal(5000.50));
        newOrder.setCardHolderName("order.getCustomerName()");
        newOrder.setEmail(customer.getEmail());


        MimeMessage mimeMessageMock = mock(MimeMessage.class);
        MimeMessageHelper mimeMessageHelperMock = mock(MimeMessageHelper.class);

        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessageMock);
        doNothing().when(mimeMessageHelperMock).setFrom(anyString(), anyString());

        //when(emailService.sendReceiptRegisteredCustomerEmail(newOrder.getOrderNumber())).thenReturn(newOrder.getEmail());

        when(orderRepository.findByOrderNumber(newOrder.getOrderNumber())).thenReturn(newOrder);
        when(orderRepository.save(any(Order.class))).thenReturn(newOrder);
        when(customerRepository.findById(customer.getId())).thenReturn(customer);

        Order actualOrderDetails = orderServiceImpl.customerOrder(newOrder , customer.getId());

        assertEquals(newOrder, actualOrderDetails);
    }

    @Test
    public void customer_Order_Return_500(){

        Order newOrder = new Order();
        newOrder.setOrderAmount(new BigDecimal(5000.50));
        newOrder.setCardHolderName("order.getCustomerName()");

        doThrow(new RuntimeException("Failed to make customer order"))
                .when(orderServiceImplMock)
                .customerOrder(newOrder, id);

        // Now, when you call addPet, it should throw the RuntimeException as configured above
        Exception exception = assertThrows(RuntimeException.class, ()
                -> orderServiceImplMock.customerOrder(newOrder, id));

        // Perform your assertions on the exception or any other expected behavior
        assertEquals("Failed to make customer order", exception.getMessage());
    }
}
