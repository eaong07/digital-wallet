package com.digital.wallet.account_service.dto;

import com.digital.wallet.account_service.enums.AccountTypes;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {
    private String accountId;
    private String userId;
    private AccountTypes type;
    private double balance;
}
