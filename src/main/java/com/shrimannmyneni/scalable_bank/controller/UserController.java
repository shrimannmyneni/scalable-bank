package com.shrimannmyneni.scalable_bank.controller;

import com.shrimannmyneni.scalable_bank.dto.*;
import com.shrimannmyneni.scalable_bank.service.impl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User Account Management APIs")
public class UserController {
    @Autowired
    UserService userService;

    @Operation(
            summary = "Create New User Account",
            description = "Creating a new user. They need to use an unique email and are assigned a account ID upon successful sign up."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @PostMapping("/create")
    public BankResponse createAccount(@RequestBody UserRequest userRequest) {
        return userService.createAccount(userRequest);
    }

    @Operation(
            summary = "Inquire User Account Balance",
            description = "Inquire a User's Account Balance. Returns Account Name, Account Balance, and Account Number"
    )
    @ApiResponse(
            responseCode = "202",
            description = "Http Status 202 INQUIRED"
    )
    @GetMapping("/balanceEnquiry")
    public BankResponse balanceEnquiry(@RequestBody EnquiryRequest enquiryRequest) {
        return userService.balanceEnquiry(enquiryRequest);
    }

    @Operation(
            summary = "Inquire User Account Name",
            description = "Given an Account #, returns the user's first and last name on the account."
    )
    @ApiResponse(
            responseCode = "202",
            description = "Http Status 202 INQUIRED"
    )
    @GetMapping("/nameEnquiry")
    public String nameEnquiry(@RequestBody EnquiryRequest enquiryRequest) {
        return userService.nameEnquiry(enquiryRequest);
    }

    @Operation(
            summary = "Credit User Account",
            description = "Given an Account # and an amount to credit, credits the user's account if there are sufficient funds."
    )
    @ApiResponse(
            responseCode = "203",
            description = "Http Status 203 CREDITED"
    )
    @PostMapping("/credit")
    public BankResponse credit(@RequestBody CreditDebitRequest creditDebitRequest) {
        return userService.creditAccount(creditDebitRequest);
    }

    @Operation(
            summary = "Debit User Account",
            description = "Given an Account # and an amount to debit, debits the user's account."
    )
    @ApiResponse(
            responseCode = "204",
            description = "Http Status 204 DEBITED"
    )
    @PostMapping("/debit")
    public BankResponse debit(@RequestBody CreditDebitRequest creditDebitRequest) {
        return userService.debitAccount(creditDebitRequest);
    }

    @Operation(
            summary = "Transfer Funds",
            description = "Given an Account # and an amount to transfer, credits the from user's account if there are sufficient funds, then debits in the to user's account."
    )
    @ApiResponse(
            responseCode = "205",
            description = "Http Status 205 TRANSFERRED"
    )
    @PostMapping("/transfer")
    public BankResponse transfer(@RequestBody TransferRequest transferRequest) {
        return userService.transferFunds(transferRequest);
    }
}
