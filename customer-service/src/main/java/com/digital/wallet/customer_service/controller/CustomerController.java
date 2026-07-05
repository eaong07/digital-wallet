package com.digital.wallet.customer_service.controller;

import com.digital.wallet.customer_service.dto.CustomerDto;
import com.digital.wallet.customer_service.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/user")
    public ResponseEntity<CustomerDto> getUserById(@RequestHeader("X-User-Id") String id) {
        return ResponseEntity.ok(customerService.getUser(id));
    }

    @PostMapping("/user")
    public ResponseEntity addNewUser(@RequestBody CustomerDto customerDto) {
        customerService.addUser(customerDto);
        return ResponseEntity.accepted().build();
    }

    @PatchMapping("/user")
    public ResponseEntity updateUserInfo(@RequestBody CustomerDto customerDto) {
        customerService.updateUser(customerDto);
        return ResponseEntity.accepted().build();
    }

    @PatchMapping("/friends")
    public ResponseEntity addFriends(@RequestParam("friendId") String friendId,
                                     @RequestHeader("X-User-Id") String userId) {
        customerService.addFriends(userId, friendId);
        return ResponseEntity.accepted().build();
    }

}
