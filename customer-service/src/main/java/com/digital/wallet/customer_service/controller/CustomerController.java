package com.digital.wallet.customer_service.controller;

import com.digital.wallet.customer_service.dto.CustomerDto;
import com.digital.wallet.customer_service.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/user")
    public ResponseEntity<CustomerDto> getUserById(@RequestHeader("X-User-Id") String id) {
        log.info("Requesting details for {}", id);
        return ResponseEntity.ok(customerService.getUser(id));
    }

    @PostMapping("/user")
    @Transactional
    public ResponseEntity addNewUser(@RequestBody CustomerDto customerDto) {
        log.info("Received request to add new user {}", customerDto.getName());
        customerService.addUser(customerDto);
        return ResponseEntity.accepted().build();
    }

    @PatchMapping("/user")
    @Transactional
    public ResponseEntity updateUserInfo(@RequestHeader("X-User-Id") String id,
                                         @RequestBody CustomerDto customerDto) {
        log.info("Received request to update user details {}", id);
        customerDto.setUserId(id);
        customerService.updateUser(customerDto);
        return ResponseEntity.accepted().build();
    }

    @PatchMapping("/friends")
    @Transactional
    public ResponseEntity addFriends(@RequestParam("friendId") String friendId,
                                     @RequestHeader("X-User-Id") String userId) {
        log.info("Received request to add new friends to user {}", userId);
        customerService.addFriends(userId, friendId);
        return ResponseEntity.accepted().build();
    }

}
