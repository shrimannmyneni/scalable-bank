package com.shrimannmyneni.scalable_bank.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    @Schema(
            name = "Transaction ID (Auto-Generated)"
    )
    private String transactionId;
    @Schema(
            name = "Transaction Type"
    )
    private String transactionType;
    @Schema(
            name = "Transaction Amount"
    )
    private BigDecimal amount;
    @Schema(
            name = "Account Number"
    )
    private String accountNumber;
    @Schema(
            name = "Transaction Status"
    )
    private String status;
}
