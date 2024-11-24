package com.shrimannmyneni.scalable_bank.utils;

import java.time.Year;

public class AccountUtils {

    public static final String ACCOUNT_EXISTS_CODE = "004";
    public static final String ACCOUNT_EXISTS_MESSAGE = "Account already exists!";

    public static final String ACCOUNT_DNE_CODE = "002";
    public static final String ACCOUNT_DNE_MESSAGE = "Account creation successful!";


    // Generates an account number in the format YEAR + random 7 digits
    public static String generateAccountNumber() {
        String yearStr = String.valueOf(Year.now().getValue());

        int min = 1000000;
        int max = 9999999;
        int randomNum = (int) (Math.random() * (max - min + 1)) + min;

        return yearStr + randomNum;
    }
}
