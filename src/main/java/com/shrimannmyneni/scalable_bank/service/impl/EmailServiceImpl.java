package com.shrimannmyneni.scalable_bank.service.impl;

import com.shrimannmyneni.scalable_bank.dto.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Override
    public void sendEmailAlert(EmailDetails emailDetails) {
        try{
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(senderEmail);
            msg.setTo(emailDetails.getRecipient());
            msg.setText(emailDetails.getBody());
            msg.setSubject(emailDetails.getSubject());

            javaMailSender.send(msg);
            System.out.println("Email sent successfully");
        } catch (MailException e) {
            throw new RuntimeException(e);
        }
    }
}
