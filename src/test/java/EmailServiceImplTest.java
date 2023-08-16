import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.example.impl.EmailServiceImpl;
import org.example.model.Customer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class EmailServiceImplTest {

    @InjectMocks
    EmailServiceImpl emailServiceImpl;

    @Mock
    private JavaMailSender javaMailSender;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

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
}
