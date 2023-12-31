package org.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
@Data
@Entity
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "order_number", updatable = false, nullable = false)
    private String orderNumber;

    @CreationTimestamp
    private Date orderDate;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Column(name = "order_amount")
    private BigDecimal orderAmount;

    @NotNull
    @NotBlank
    @Column(name = "card_holder_name")
    private String cardHolderName;

    @ManyToOne // Many orders can belong to one user
    @JoinColumn(name = "user_id") // This is the foreign key to the User table
    private Customer customer;

    @Column(name = "email")
    private String email;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "billing_address")
    private String billingAddress;

    @Column(name = "phone_number")
    private String phoneNumber;
}