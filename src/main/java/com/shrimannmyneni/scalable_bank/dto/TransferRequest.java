package com.shrimannmyneni.scalable_bank.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequest {
    @Schema(
           name = "Account to Credit"
    )
    private String fromAccount;
    @Schema(
            name = "Account to Debit"
    )
    private String toAccount;
    @Schema(
            name = "Amount of debit/credit"
    )
    private BigDecimal amount;

    public String getFromAccountNumber() {
        return fromAccount;
    }

    public String getToAccountNumber() {
        return toAccount;
    }
}
