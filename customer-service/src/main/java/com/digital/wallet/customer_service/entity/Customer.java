package com.digital.wallet.customer_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "CUSTOMER")
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;
    @Column(name = "name")
    private String name;
    @JdbcTypeCode(value = SqlTypes.DATE)
    @Column(name = "dob")
    private Date dob;
    @Column(name = "address")
    private String address;
    @JdbcTypeCode(value = SqlTypes.ARRAY)
    @Column(name = "contactNumbers", columnDefinition = "text[]")
    private List<String> contactNumbers;
    @JdbcTypeCode(value = SqlTypes.ARRAY)
    @Column(name = "friends", columnDefinition = "text[]")
    private List<String> friends;
}
