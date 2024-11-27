package com.shrimannmyneni.scalable_bank.service.impl;

import com.shrimannmyneni.scalable_bank.dto.TransactionDto;
import com.shrimannmyneni.scalable_bank.entity.Transaction;

public interface TransactionService {
    void saveTransaction(TransactionDto transactionDto);
}
