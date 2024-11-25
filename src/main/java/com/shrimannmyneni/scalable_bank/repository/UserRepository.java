package com.shrimannmyneni.scalable_bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shrimannmyneni.scalable_bank.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);
    Boolean existsByAccountNumber(String username);
}
