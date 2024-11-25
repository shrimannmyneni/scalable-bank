package com.shrimannmyneni.scalable_bank.controller;

import com.shrimannmyneni.scalable_bank.dto.BankResponse;
import com.shrimannmyneni.scalable_bank.dto.UserRequest;
import com.shrimannmyneni.scalable_bank.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")

public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/create")
    public BankResponse createAccount(@RequestBody UserRequest userRequest) {
        return userService.createAccount(userRequest);
    }
}
