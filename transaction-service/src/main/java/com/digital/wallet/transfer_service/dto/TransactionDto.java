package com.digital.wallet.transfer_service.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    private String transactionId;
    private String type;
    private String sender;
    private String receiver;
    private String accountId;
    private String userId;
    private String status;
    private double amount;
    private Date transactionDate;
}
