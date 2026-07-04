package com.digital.wallet.account_service.event;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponseEvent {
    private String senderId;
    private String receiverId;
    private String accountId;
    private String userId;
    private String status;
    private double amount;
}
