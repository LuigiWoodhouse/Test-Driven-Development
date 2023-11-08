package org.example.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.example.exception.OrderNotFoundException;
import org.example.model.Customer;
import org.example.model.Order;
import org.example.repository.OrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import java.io.UnsupportedEncodingException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
public class EmailServiceImplTest {

    @InjectMocks
    EmailServiceImpl emailServiceImpl;

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    OrderRepository orderRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    EmailServiceImpl emailServiceImplMock =  mock(EmailServiceImpl.class);

    @Test
    public void send_Payment_Successful_Email() throws MessagingException, UnsupportedEncodingException, OrderNotFoundException {

        Customer customer = new Customer();
        customer.setName("John Doe");
        customer.setEmail("johndoe@example.com");
        Order newOrder = new Order();

        newOrder.setEmail("test@gmail.com");

        MimeMessage mimeMessageMock = mock(MimeMessage.class);
        MimeMessageHelper mimeMessageHelperMock = mock(MimeMessageHelper.class);

        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessageMock);
        doNothing().when(mimeMessageHelperMock).setFrom(anyString(), anyString());
        when(orderRepository.findByOrderNumber(newOrder.getOrderNumber())).thenReturn(newOrder);
        // Act
        String result = emailServiceImpl.sendPaymentSuccessfulEmail(newOrder.getOrderNumber());

        // Assert
        verify(javaMailSender).send(mimeMessageMock);

        assertNotNull(result);
    }
    @Test
    public void send_Payment_Email_Return_500() throws OrderNotFoundException {

        Customer customer = new Customer();
        customer.setName("John Doe");
        customer.setEmail("johndoe@example.com");

        Order newOrder = new Order();

        when(emailServiceImplMock.sendPaymentSuccessfulEmail(newOrder.getOrderNumber()))
                .thenThrow(new RuntimeException("Failed to send verification email"));


        Exception exception = assertThrows(RuntimeException.class, ()
                -> emailServiceImplMock.sendPaymentSuccessfulEmail(newOrder.getOrderNumber()));

        // Perform your assertions on the exception or any other expected behavior
        assertEquals("Failed to send verification email", exception.getMessage());

        System.out.println("Expected: Failed to send verification email");
        System.out.println("Actual  : " + exception.getMessage());
    }
}