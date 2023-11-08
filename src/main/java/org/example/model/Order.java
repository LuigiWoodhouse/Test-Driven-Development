package org.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
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

    @Column(name = "order_amount")
    private BigDecimal orderAmount;

    @Column(name = "cardHolderName")
    private String cardHolderName;

    @Column(name = "email")
    private String email;
}