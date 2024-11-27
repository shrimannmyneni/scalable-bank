package com.shrimannmyneni.scalable_bank.service.impl;

import com.shrimannmyneni.scalable_bank.dto.TransactionDto;
import com.shrimannmyneni.scalable_bank.entity.Transaction;
import com.shrimannmyneni.scalable_bank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public void saveTransaction(TransactionDto transactionDto) {
        Transaction transaction = Transaction.builder()
                .transactionType(transactionDto.getTransactionType())
                .amount(transactionDto.getAmount())
                .accountNumber(transactionDto.getAccountNumber())
                .status(transactionDto.getStatus())
                .build();
        transactionRepository.save(transaction);
        System.out.println("Saved transaction successfully");
    }
}
