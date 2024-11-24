package com.shrimannmyneni.scalable_bank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    //basic userdata
    private String firstName;
    private String lastName;
    private String gender;

    //contact userdata
    private String email;
    private String phoneNumber;
    private BigDecimal secondaryPhoneNumber;

    //location userdata
    private String address;
    private String city;
    private String USState;
    private String zipCode;
}
