package ir.shaparak.eWallet.util;

import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.CustomException;

public class AccountUtility {
    public static boolean isValidPans(String[] pans) {
        for (String pan : pans){
            if (!isValidPan(pan))
                return false;
        }
        return true;
    }

    public static boolean isValidIban(String[] ibans) {
        for (String iban : ibans)
            if (!isValidIban(iban))
                return false;

        return true;
    }

    public static boolean isValidPan(String pan) {
        return (pan.length() == 16 || pan.length() == 19);
    }

    public static boolean isValidIban(String iban) {
        return iban.startsWith("IR");
    }

    public static boolean isValidAccountByType(String accountType, String account) {
        if (accountType.equals(WalletConstants.ACCOUNT_TYPE_IBAN_STRING))
            return isValidIban(account);
        else
            return isValidPan(account);

    }

    public static boolean isValidAccountExist(String[] ibans, String[] pans) {

        boolean hasItems = false;
        boolean isIbansValid = true, isPansValid = true;

        if (ibans.length != 0) {
            isIbansValid = AccountUtility.isValidIban(ibans);
            hasItems = true;
        }

        if (pans.length != 0) {
            isPansValid = AccountUtility.isValidPans(pans);
            hasItems = true;
        }

        return hasItems && isIbansValid && isPansValid;

    }

    public static void main(String[] args) throws CustomException {
/*
        //should be false
        System.out.println(isValidAccountExist(new String[]{}, new String[]{}) + ", false");
        System.out.println(isValidAccountExist(new String[]{"123"}, new String[]{}) + ", false");
        System.out.println(isValidAccountExist(new String[]{"IR", "123"}, new String[]{}) + ", false");
        System.out.println(isValidAccountExist(new String[]{}, new String[]{"123456789012345"}) + ", false");
        System.out.println(isValidAccountExist(new String[]{}, new String[]{"1234567890123456", "123456789012345678"}) + ", false");
        System.out.println(isValidAccountExist(new String[]{}, new String[]{"1234567890123456", "123456789012345678"}) + ", false");
        System.out.println(isValidAccountExist(new String[]{"IR", "123"}, new String[]{"1234567890123456"}) + ", false");

        //should be true
        System.out.println(isValidAccountExist(new String[]{"IR"}, new String[]{}) + ", true");
        System.out.println(isValidAccountExist(new String[]{"IR", "IR"}, new String[]{}) + ", true");
        System.out.println(isValidAccountExist(new String[]{}, new String[]{"1234567890123456"}) + ", true");
        System.out.println(isValidAccountExist(new String[]{}, new String[]{"1234567890123456", "1234567890123456789"}) + ", true");
        System.out.println(isValidAccountExist(new String[]{"IR"}, new String[]{"1234567890123456", "1234567890123456789"}) + ", true");
        System.out.println(isValidAccountExist(new String[]{"IR", "IR"}, new String[]{"1234567890123456789"}) + ", true");
        System.out.println(isValidAccountExist(new String[]{"IR", "IR"}, new String[]{"1234567890123456", "1234567890123456789"}) + ", true");
*/

        System.out.println(isValidAccountByType(WalletConstants.ACCOUNT_TYPE_IBAN_STRING, "IR123412341") + ", true");
        System.out.println(isValidAccountByType(WalletConstants.ACCOUNT_TYPE_IBAN_STRING, "1111") + ", false");


    }
}
