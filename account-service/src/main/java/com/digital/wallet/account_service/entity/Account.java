package com.digital.wallet.account_service.entity;

import com.digital.wallet.account_service.enums.AccountTypes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "ACCOUNT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String accountId;
    @Column(name = "userId")
    private String userId;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private AccountTypes type;
    @Column(name = "balance")
    private double balance;
}
