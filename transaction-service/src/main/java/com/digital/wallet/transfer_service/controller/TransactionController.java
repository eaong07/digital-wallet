package com.digital.wallet.transfer_service.controller;

import com.digital.wallet.transfer_service.dto.DebitCreditDto;
import com.digital.wallet.transfer_service.dto.TransferDto;
import com.digital.wallet.transfer_service.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transferService;

    @PostMapping("/debit")
    public ResponseEntity debit(@RequestBody DebitCreditDto debitCreditDto,
                                @RequestHeader(value = "X-User-Id")
                                String userId) {
        transferService.debit(debitCreditDto, userId);
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

}
