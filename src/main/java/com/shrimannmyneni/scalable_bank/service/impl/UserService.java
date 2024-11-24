package com.shrimannmyneni.scalable_bank.service.impl;

import com.shrimannmyneni.scalable_bank.dto.BankResponse;
import com.shrimannmyneni.scalable_bank.dto.UserRequest;

public interface UserService {
    BankResponse createAccount(UserRequest userRequest);
}
