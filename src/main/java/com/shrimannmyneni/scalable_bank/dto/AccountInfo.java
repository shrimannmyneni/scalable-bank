package com.shrimannmyneni.scalable_bank.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class AccountInfo {
    private String accountName;
    private BigDecimal accountBalance;
    private String accountNumber;
}
