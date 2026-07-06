package com.digital.wallet.transfer_service.controller;

import com.digital.wallet.transfer_service.dto.DebitCreditDto;
import com.digital.wallet.transfer_service.dto.TransactionDto;
import com.digital.wallet.transfer_service.dto.TransferDto;
import com.digital.wallet.transfer_service.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class TransactionController {

    private final TransactionService transferService;

    @PostMapping("/debit")
    public ResponseEntity debit(@RequestBody DebitCreditDto debitCreditDto,
                                @RequestHeader(value = "X-User-Id")
                                String userId) {
        log.info("Request to debit {}", userId);
        transferService.debit(debitCreditDto, userId);
        log.info("Amount debited {}", userId);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/credit")
    public ResponseEntity credit(@RequestBody DebitCreditDto debitCreditDto,
                                 @RequestHeader(value = "X-User-Id")
                                 String userId) {
        transferService.credit(debitCreditDto, userId);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/transfer")
    public ResponseEntity transfer(@RequestBody TransferDto transferDto,
                                   @RequestHeader(value = "X-User-Id")
                                   String userId) {
        transferService.transfer(transferDto, userId);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/history")
    public ResponseEntity getHistory(@RequestHeader(value = "X-User-Id")
                                     String userId) {
        return ResponseEntity.ok(transferService.getHistory(userId));
    }

    @GetMapping("/transaction")
    public ResponseEntity getTransaction(@RequestHeader(value = "X-User-Id")
                                         String userId,
                                         @RequestHeader(value = "X-Transaction-Id") String transactionID) {
        return ResponseEntity.ok(transferService.getTransaction(transactionID, userId));
    }

}
