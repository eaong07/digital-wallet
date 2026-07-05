package com.digital.wallet.account_service.service;

import com.digital.wallet.account_service.dto.AccountDto;
import com.digital.wallet.account_service.entity.Account;
import com.digital.wallet.account_service.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountDto getUserAccount(String userId){
        return accountRepository.findByUserId(userId).map(a->{
            return AccountDto
                    .builder()
                    .accountId(a.getAccountId())
                    .userId(a.getUserId())
                    .type(a.getType())
                    .balance(a.getBalance())
                    .build();
        }).orElseThrow();
    }

    public List<AccountDto> getFriendAccounts(List<String> friendList){
        List<Account> accounts = accountRepository.findByUserIdIn(friendList.stream().toArray(String[]::new));
        log.info("Friend accounts found {}", !accounts.isEmpty());
        return accounts.stream().map(a -> {
            return AccountDto
                    .builder()
                    .accountId(a.getAccountId())
                    .userId(a.getUserId())
                    .type(a.getType())
                    .build();
        }).toList();
    }
}