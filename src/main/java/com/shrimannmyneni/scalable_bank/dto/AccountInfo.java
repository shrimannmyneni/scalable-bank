package com.shrimannmyneni.scalable_bank.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class AccountInfo {
    @Schema(
            name = "User Account Name"
    )
    private String accountName;
    @Schema(
            name = "User Account Balance"
    )
    private BigDecimal accountBalance;
    @Schema(
            name = "User Account Number"
    )
    private String accountNumber;
}
