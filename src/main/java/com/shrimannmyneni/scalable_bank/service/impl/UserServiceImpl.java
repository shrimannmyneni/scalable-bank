package com.shrimannmyneni.scalable_bank.service.impl;

import com.shrimannmyneni.scalable_bank.dto.*;
import com.shrimannmyneni.scalable_bank.entity.Transaction;
import com.shrimannmyneni.scalable_bank.entity.User;
import com.shrimannmyneni.scalable_bank.repository.UserRepository;
import com.shrimannmyneni.scalable_bank.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    TransactionService transactionService;

    @Override
    public BankResponse createAccount(UserRequest userRequest) {

        if(userRepository.existsByEmail(userRequest.getEmail())) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User newUser = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .gender(userRequest.getGender())

                .email(userRequest.getEmail())
                .phoneNumber(userRequest.getPhoneNumber())
                .secondaryPhoneNumber(userRequest.getSecondaryPhoneNumber())

                .address(userRequest.getAddress())
                .city(userRequest.getCity())
                .USState(userRequest.getUSState())
                .zipCode(userRequest.getZipCode())

                .status("ACTIVE")

                .accountNumber(AccountUtils.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)

                .build();

        User savedUser = userRepository.save(newUser);

        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(savedUser.getEmail())
                .subject("ACCOUNT CREATION")
                .body("Your Shrimann Bank Account has been Successfully Created! \nAccount Details: \n" +
                        "Account Name: "
                + savedUser.getFirstName() + " " + savedUser.getLastName()
                + "\nAccount Number: " + savedUser.getAccountNumber()
                + "\nAccount Balance: $0.00")
                .build();
        emailService.sendEmailAlert(emailDetails);

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_CODE)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(savedUser.getAccountBalance())
                        .accountNumber(savedUser.getAccountNumber())
                        .accountName(savedUser.getFirstName() + " " + savedUser.getLastName())
                        .build())
                .build();

    }

    @Override
    public BankResponse balanceEnquiry(EnquiryRequest enquiryRequest) {
        //check if provided acct number is in the db
        boolean isValidAccountNumber = userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber());

        if (!isValidAccountNumber) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_DNE_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_DNE_MESSAGE)
                    .accountInfo(null)
                    .build();
        } else {
            // implement
            // userRepository.findByAccountNumber not working??
            User foundUser = userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());

            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_FOUND_MESSAGE)
                    .accountInfo(AccountInfo.builder()
                            .accountBalance(foundUser.getAccountBalance())
                            .accountNumber(foundUser.getAccountNumber())
                            .accountName(foundUser.getFirstName() + " " + foundUser.getLastName())
                            .build())
                    .build();
        }
    }

    @Override
    public String nameEnquiry(EnquiryRequest enquiryRequest) {
        Boolean isValidAccountNumber = userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber());
        if (!isValidAccountNumber) {
            return AccountUtils.ACCOUNT_DNE_MESSAGE;
        }
        User foundUser = userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());
        return foundUser.getFirstName() + " " + foundUser.getLastName();
    }

    @Override
    public BankResponse creditAccount(CreditDebitRequest creditDebitRequest){
        Boolean isValidAccountNumber = userRepository.existsByAccountNumber(creditDebitRequest.getAccountNumber());
        if (!isValidAccountNumber) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_DNE_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_DNE_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        User foundUser = userRepository.findByAccountNumber(creditDebitRequest.getAccountNumber());

        BigDecimal availableBalance = foundUser.getAccountBalance();
        BigDecimal creditAmount = creditDebitRequest.getAmount();
        if (availableBalance.compareTo(creditAmount) < 0) {

            TransactionDto t = TransactionDto.builder()
                    .accountNumber(foundUser.getAccountNumber())
                    .transactionType("CREDIT")
                    .status("FAILED (Insufficient Funds)")
                    .amount(creditDebitRequest.getAmount())
                    .build();

            transactionService.saveTransaction(t);


            return BankResponse.builder()
                    .responseMessage(AccountUtils.INSUFFICIENT_FUNDS_MESSAGE)
                    .responseCode(AccountUtils.INSUFFICIENT_FUNDS_CODE)
                    .accountInfo(AccountInfo.builder()
                            .accountBalance(foundUser.getAccountBalance())
                            .accountNumber(foundUser.getAccountNumber())
                            .accountName(foundUser.getFirstName() + " " + foundUser.getLastName())
                            .build())
                    .build();
        }

        foundUser.setAccountBalance(foundUser.getAccountBalance().subtract(creditDebitRequest.getAmount()));
        userRepository.save(foundUser);

        TransactionDto transactionDto = TransactionDto.builder()
                .accountNumber(foundUser.getAccountNumber())
                .transactionType("CREDIT")
                .status("SUCCESS")
                .amount(creditDebitRequest.getAmount())
                .build();

        transactionService.saveTransaction(transactionDto);

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREDITED_CODE)
                .responseMessage(AccountUtils.ACCOUNT_CREDITED_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(foundUser.getAccountBalance())
                        .accountNumber(foundUser.getAccountNumber())
                        .accountName(foundUser.getFirstName() + " " + foundUser.getLastName())
                        .build())
                .build();
    }

    @Override
    public BankResponse debitAccount(CreditDebitRequest creditDebitRequest) {
        Boolean isValidAccountNumber = userRepository.existsByAccountNumber(creditDebitRequest.getAccountNumber());
        if (!isValidAccountNumber) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_DNE_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_DNE_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        User foundUser = userRepository.findByAccountNumber(creditDebitRequest.getAccountNumber());
        foundUser.setAccountBalance(foundUser.getAccountBalance().add(creditDebitRequest.getAmount()));
        userRepository.save(foundUser);

        TransactionDto transactionDto = TransactionDto.builder()
                .accountNumber(foundUser.getAccountNumber())
                .transactionType("DEBIT")
                .status("SUCCESS")
                .amount(creditDebitRequest.getAmount())
                .build();

        transactionService.saveTransaction(transactionDto);

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_DEBITED_CODE)
                .responseMessage(AccountUtils.ACCOUNT_DEBITED_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(foundUser.getAccountBalance())
                        .accountNumber(foundUser.getAccountNumber())
                        .accountName(foundUser.getFirstName() + " " + foundUser.getLastName())
                        .build())
                .build();
    }

    @Override
    public BankResponse transferFunds(TransferRequest transferRequest) {
        //check if both accounts are valid and active
        Boolean validFromAccount = userRepository.existsByAccountNumber(transferRequest.getFromAccountNumber());
        if (!validFromAccount) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_DNE_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_DNE_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        Boolean validToAccount = userRepository.existsByAccountNumber(transferRequest.getToAccountNumber());
        if (!validToAccount) {
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_DNE_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_DNE_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        User fromUser = userRepository.findByAccountNumber(transferRequest.getFromAccountNumber());
        User toUser = userRepository.findByAccountNumber(transferRequest.getToAccountNumber());

        //check if the fromAccount has enough balance for the transaction
        BigDecimal availableFromBalance = fromUser.getAccountBalance();
        BigDecimal creditAmount = transferRequest.getAmount();
        if (availableFromBalance.compareTo(creditAmount) < 0) {

            TransactionDto q = TransactionDto.builder()
                    .accountNumber(fromUser.getAccountNumber())
                    .transactionType("CREDIT")
                    .status("FAILED TRANSFER (Insufficient funds)")
                    .amount(creditAmount)
                    .build();
            transactionService.saveTransaction(q);

            //        TransactionDto transactionDto = TransactionDto.builder()
//                .accountNumber(foundUser.getAccountNumber())
//                .transactionType("CREDIT")
//                .amount(creditDebitRequest.getAmount())
//                .build();
//
//        transactionService.saveTransaction(transactionDto);

            return BankResponse.builder()
                    .responseMessage(AccountUtils.INSUFFICIENT_FUNDS_MESSAGE)
                    .responseCode(AccountUtils.INSUFFICIENT_FUNDS_CODE)
                    .accountInfo(AccountInfo.builder()
                            .accountBalance(fromUser.getAccountBalance())
                            .accountNumber(fromUser.getAccountNumber())
                            .accountName(fromUser.getFirstName() + " " + fromUser.getLastName())
                            .build())
                    .build();
        }

        //now we can complete the transaction
        fromUser.setAccountBalance(fromUser.getAccountBalance().subtract(creditAmount));
        toUser.setAccountBalance(fromUser.getAccountBalance().add(creditAmount));
        userRepository.save(fromUser);
        userRepository.save(toUser);

        //log transactions in db
        TransactionDto s1 = TransactionDto.builder()
                .accountNumber(fromUser.getAccountNumber())
                .transactionType("CREDIT")
                .status("SUCCESS")
                .amount(creditAmount)
                .build();
        transactionService.saveTransaction(s1);

        TransactionDto s2 = TransactionDto.builder()
                .accountNumber(toUser.getAccountNumber())
                .transactionType("DEBIT")
                .status("SUCCESS")
                .amount(creditAmount)
                .build();
        transactionService.saveTransaction(s2);

        //send emails to both parties
        EmailDetails fromEmail = EmailDetails.builder()
                .recipient(fromUser.getEmail())
                .subject("Online Transfer: Account Credited")
                .body("Hello,\n"
                        + "Your Outgoing Transfer has successfully gone through!\n"
                        + "Account Details:\n"
                        + "\nAccount Number: " + fromUser.getAccountNumber()
                        + "\nTransfer Amount: " + creditAmount
                        + "\nCurrent Account Balance: " + fromUser.getAccountBalance()
                        + "\n\nTransferred To: " + toUser.getFirstName() + " " + toUser.getLastName()
                )
                .build();
        emailService.sendEmailAlert(fromEmail);

        EmailDetails toEmail = EmailDetails.builder()
                .recipient(toUser.getEmail())
                .subject("Online Transfer: Account Debited")
                .body("Hello,\n"
                        + "You have been debited via a transfer!\n"
                        + "Account Details:\n"
                        + "\nAccount Number: " + toUser.getAccountNumber()
                        + "\nTransfer Amount: " + creditAmount
                        + "\nCurrent Account Balance: " + toUser.getAccountBalance()
                        + "\n\nTransferred From: " + fromUser.getFirstName() + " " + fromUser.getLastName()
                )
                .build();
        emailService.sendEmailAlert(toEmail);



        return BankResponse.builder()
                .responseCode(AccountUtils.SUCCESSFUL_TRANSFER_CODE)
                .responseMessage(AccountUtils.SUCCESSFUL_TRANSFER_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(fromUser.getAccountBalance())
                        .accountNumber(fromUser.getAccountNumber())
                        .accountName(fromUser.getFirstName() + " " + fromUser.getLastName())
                        .build())
                .build();
    }
}

////basic userdata
//    private String firstName;
//    private String lastName;
//    private String gender;
//
//    //contact userdata
//    private String email;
//    private String phoneNumber;
//    private BigDecimal secondaryPhoneNumber;
//
//    //location userdata
//    private String address;
//    private String city;
//    private String USState;
//    private String zipCode;
//
//    //account userdata
//    private BigDecimal accountBalance;
//    private String status;