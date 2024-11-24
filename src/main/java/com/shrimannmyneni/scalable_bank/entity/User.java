package com.shrimannmyneni.scalable_bank.entity;

import jakarta.persistence.*;
import lombok.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //basic userdata
    private String firstName;
    private String lastName;
    private String gender;

    //contact userdata
    private String email;
    private String phoneNumber;
    private BigDecimal secondaryPhoneNumber;

    //location userdata
    private String address;
    private String city;
    private String USState;
    private String zipCode;

    //account userdata
    private BigDecimal accountBalance;
    private String status;
    private String accountNumber;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
