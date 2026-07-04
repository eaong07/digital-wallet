package com.digital.wallet.transfer_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferDto {
    private String senderId;
    private String receiverId;
    private double amount;
}
