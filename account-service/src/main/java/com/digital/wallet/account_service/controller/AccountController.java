package com.digital.wallet.account_service.controller;

import com.digital.wallet.account_service.dto.AccountDto;
import com.digital.wallet.account_service.dto.FriendListDto;
import com.digital.wallet.account_service.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/user/account")
    public ResponseEntity<AccountDto> getAccount(@RequestHeader("X-User-Id")
                                                 String userId) {
        log.info("Request to receive user account details {}", userId);
        return ResponseEntity.ok(accountService.getUserAccount(userId));
    }

    @PostMapping("/friend/account")
    public ResponseEntity<List<AccountDto>> getFriendAccounts(@RequestHeader("X-User-Id")
                                                              String userId,
                                                              @RequestBody
                                                              FriendListDto friendListDto) {
        log.info("Request to find user's friends' accounts {}", userId);
        return ResponseEntity.ok(accountService.getFriendAccounts(friendListDto.getFriendList()));
    }
}

