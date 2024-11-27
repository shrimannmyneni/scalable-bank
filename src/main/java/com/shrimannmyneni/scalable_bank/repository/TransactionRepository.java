package com.shrimannmyneni.scalable_bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shrimannmyneni.scalable_bank.entity.Transaction;


public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
