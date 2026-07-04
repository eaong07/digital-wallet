package com.digital.wallet.transfer_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TRANSACTION")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String transactionId;
    @Column(name = "type")
    private String type;
    @Column(name = "sender")
    private String sender;
    @Column(name = "receiver")
    private String receiver;
    @Column(name = "accountId")
    private String accountId;
    @Column(name = "userId")
    private String userId;
    @Column(name = "status")
    private String status;
    @Column(name = "amount")
    private double amount;
}
