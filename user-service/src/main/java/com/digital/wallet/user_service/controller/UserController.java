package com.digital.wallet.user_service.controller;

import com.digital.wallet.user_service.dto.UserDto;
import com.digital.wallet.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spi")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") String id){
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PostMapping("/user")
    public ResponseEntity addNewUser(@RequestBody UserDto userDto){
        userService.addUser(userDto);
        return ResponseEntity.accepted().build();
    }

    @PatchMapping("/user")
    public ResponseEntity updateUserInfo(@RequestBody UserDto userDto){
        
        userService.updateUser(userDto);
        return ResponseEntity.accepted().build();
    }

}
