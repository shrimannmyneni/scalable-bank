package com.shrimannmyneni.scalable_bank.service.impl;

import com.shrimannmyneni.scalable_bank.dto.EmailDetails;

public interface EmailService {
    void sendEmailAlert(EmailDetails emailDetails);
}
