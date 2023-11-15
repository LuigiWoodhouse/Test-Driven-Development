package org.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class GuestCustomer {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "order_number")
    private String orderNumber;

    @CreationTimestamp
    private Date orderDate;

    @Column(name = "order_amount")
    private BigDecimal orderAmount;

    @Column(name = "cardHolderName")
    private String cardHolderName;

    @Column(name = "email")
    private String email;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "billing_address")
    private String billingAddress;

    @Column(name = "phone_number")
    private String phoneNumber;
}
