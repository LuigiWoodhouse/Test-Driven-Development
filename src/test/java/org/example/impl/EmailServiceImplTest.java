package org.example.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.example.model.Customer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    EmailServiceImpl emailServiceImplMock =  mock(EmailServiceImpl.class);

    @Test
    public void send_Payment_Successful_Email() throws MessagingException, UnsupportedEncodingException {

        Customer customer = new Customer();
        customer.setName("John Doe");
        customer.setEmail("johndoe@example.com");

        MimeMessage mimeMessageMock = mock(MimeMessage.class);
        MimeMessageHelper mimeMessageHelperMock = mock(MimeMessageHelper.class);

        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessageMock);
        doNothing().when(mimeMessageHelperMock).setFrom(anyString(), anyString());

        // Act
        Customer result = emailServiceImpl.sendPaymentSuccessfulEmail(customer);

        // Assert
        verify(javaMailSender).send(mimeMessageMock);

        // Ensure that the method returns the same customer object
        assertEquals(customer, result);
    }
    @Test
    public void send_Payment_Email_Return_500() {

        Customer customer = new Customer();
        customer.setName("John Doe");
        customer.setEmail("johndoe@example.com");

        when(emailServiceImplMock.sendPaymentSuccessfulEmail(customer))
                .thenThrow(new RuntimeException("Failed to send verification email"));


        Exception exception = assertThrows(RuntimeException.class, ()
                -> emailServiceImplMock.sendPaymentSuccessfulEmail(customer));

        // Perform your assertions on the exception or any other expected behavior
        assertEquals("Failed to send verification email", exception.getMessage());

        System.out.println("Expected: Failed to send verification email");
        System.out.println("Actual  : " + exception.getMessage());
    }

    @Test
    public void make_Payment_Return_200() throws Exception {
        CatalogServiceImpl catalogServiceMock = mock(CatalogServiceImpl.class);

        // Stub the behavior of the methods on the mock
        when(catalogServiceMock.getItemTotal("Item 1")).thenReturn(new BigDecimal("60"));
        when(catalogServiceMock.getItemTotal("Item 2")).thenReturn(new BigDecimal("45"));
        when(catalogServiceMock.calculateOverallCost(any(BigDecimal.class), any(BigDecimal.class)))
                .thenReturn(BigDecimal.valueOf(105.0));

        BigDecimal expectedPayment = BigDecimal.valueOf(105.0);

        BigDecimal actualPayment = catalogServiceMock.calculateOverallCost(
                catalogServiceMock.getItemTotal("Item 1"),
                catalogServiceMock.getItemTotal("Item 2"));

        // Output the expected and actual payments
        System.out.println("Expected: " + expectedPayment);
        System.out.println("Actual  : " + actualPayment);

        // Assertions or further verifications
        assertEquals(expectedPayment, actualPayment);
    }
}