package com.digital.wallet.transfer_service.event;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionEvent {
    private String senderId;
    private String receiverId;
    private String accountId;
    private String userId;
    private double amount;
}
