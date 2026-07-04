package com.digital.wallet.account_service.service;

import com.digital.wallet.account_service.event.TransactionEvent;

public interface AccountManagementService {

    void updateAccount(TransactionEvent transactionEvent);
}
