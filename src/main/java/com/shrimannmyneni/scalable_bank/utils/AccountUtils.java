package com.shrimannmyneni.scalable_bank.utils;

import java.time.Year;

public class AccountUtils {

    //used for a successful customer inquiry
    public static final String ACCOUNT_FOUND_CODE = "001 (Found)";
    public static final String ACCOUNT_FOUND_MESSAGE = "Account found!";

    //used for a successful account creation
    public static final String ACCOUNT_CREATION_CODE = "002 (Creation)";
    public static final String ACCOUNT_CREATION_MESSAGE = "Account creation successful!";

    //used for an inactive account being used
    public static final String ACCOUNT_INACTIVE_CODE = "003 (Inactive)";
    public static final String ACCOUNT_INACTIVE_MESSAGE = "Account has been deactivated. Please reactivate for further transactions.";

    //used for a account that is already made
    public static final String ACCOUNT_EXISTS_CODE = "004 (Exists)";
    public static final String ACCOUNT_EXISTS_MESSAGE = "Account exists!";

    //used for an unsuccessful customer inquiry
    public static final String ACCOUNT_DNE_CODE = "005 (Does Not Exist)";
    public static final String ACCOUNT_DNE_MESSAGE = "Account does not exist!";

    //used for a account that has successfully been credited
    public static final String ACCOUNT_CREDITED_CODE = "011 (Credited)";
    public static final String ACCOUNT_CREDITED_MESSAGE = "Account credited!";

    //used for a account that has successfully been debited
    public static final String ACCOUNT_DEBITED_CODE = "012 (Debited)";
    public static final String ACCOUNT_DEBITED_MESSAGE = "Account debited!";

    //used for a account that was unsuccessful in being credited
    public static final String INSUFFICIENT_FUNDS_CODE = "014 (Insufficient Funds)";
    public static final String INSUFFICIENT_FUNDS_MESSAGE = "Insufficient funds for this transaction.";

    //used for a successful transfer
    public static final String SUCCESSFUL_TRANSFER_CODE = "022 (Successful Transfer)";
    public static final String SUCCESSFUL_TRANSFER_MESSAGE = "The requested amount has successfully been transferred.";


    // Generates an account number in the format YEAR + random 7 digits
    public static String generateAccountNumber() {
        String yearStr = String.valueOf(Year.now().getValue());

        int min = 1000000;
        int max = 9999999;
        int randomNum = (int) (Math.random() * (max - min + 1)) + min;

        return yearStr + randomNum;
    }
}
