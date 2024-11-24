package com.shrimannmyneni.scalable_bank.controller;

import com.shrimannmyneni.scalable_bank.dto.BankResponse;
import com.shrimannmyneni.scalable_bank.dto.UserRequest;
import com.shrimannmyneni.scalable_bank.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")

public class UserController {
    @Autowired
    UserService userService;

    public BankResponse createAccount(@RequestParam UserRequest userRequest) {
        return userService.createAccount(userRequest);
    }
}
