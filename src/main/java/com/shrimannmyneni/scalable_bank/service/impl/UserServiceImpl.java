package com.shrimannmyneni.scalable_bank.service.impl;

import com.shrimannmyneni.scalable_bank.dto.AccountInfo;
import com.shrimannmyneni.scalable_bank.dto.BankResponse;
import com.shrimannmyneni.scalable_bank.dto.EmailDetails;
import com.shrimannmyneni.scalable_bank.dto.UserRequest;
import com.shrimannmyneni.scalable_bank.entity.User;
import com.shrimannmyneni.scalable_bank.repository.UserRepository;
import com.shrimannmyneni.scalable_bank.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Override
    public BankResponse createAccount(UserRequest userRequest) {

        if(userRepository.existsByEmail(userRequest.getEmail())) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User newUser = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .gender(userRequest.getGender())

                .email(userRequest.getEmail())
                .phoneNumber(userRequest.getPhoneNumber())
                .secondaryPhoneNumber(userRequest.getSecondaryPhoneNumber())

                .address(userRequest.getAddress())
                .city(userRequest.getCity())
                .USState(userRequest.getUSState())
                .zipCode(userRequest.getZipCode())

                .status("ACTIVE")

                .accountNumber(AccountUtils.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)

                .build();

        User savedUser = userRepository.save(newUser);

        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(savedUser.getEmail())
                .subject("ACCOUNT CREATION")
                .body("Your Shrimann Bank Account has been Successfully Created! \nAccount Details: \n" +
                        "Account Name: "
                + savedUser.getFirstName() + " " + savedUser.getLastName()
                + "\nAccount Number: " + savedUser.getAccountNumber()
                + "\nAccount Balance: $0.00")
                .build();
        emailService.sendEmailAlert(emailDetails);

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_DNE_CODE)
                .responseMessage(AccountUtils.ACCOUNT_DNE_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(savedUser.getAccountBalance())
                        .accountNumber(savedUser.getAccountNumber())
                        .accountName(savedUser.getFirstName() + " " + savedUser.getLastName())
                        .build())
                .build();

    }
}

////basic userdata
//    private String firstName;
//    private String lastName;
//    private String gender;
//
//    //contact userdata
//    private String email;
//    private String phoneNumber;
//    private BigDecimal secondaryPhoneNumber;
//
//    //location userdata
//    private String address;
//    private String city;
//    private String USState;
//    private String zipCode;
//
//    //account userdata
//    private BigDecimal accountBalance;
//    private String status;