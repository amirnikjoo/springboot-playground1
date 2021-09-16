package ir.shaparak.eWallet.util;

import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.CustomException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class MyUtility {

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    private static Map map = new HashMap<>();
    private static Random random = new Random();

    public static void main(String[] args) {
        for (int i = 0; i < 1500; i++) {
            Integer d = 1;
            String a = getRandomNumber(d);
            if (Long.valueOf(a).toString().length() != d)
                throw new RuntimeException();

            System.out.println(a);
        }
    }

    public static void main2(String[] args) {
        for (int i = 0; i < 10_000; i++) {
            int stan = Integer.parseInt(MyUtility.getRandomNumber(6));
            System.out.println("stan = " + stan);
        }

    }

    public static void main1(String[] args) throws Exception {
        for (int i = 0; i < 10_000_000; i++) {
            String refNo = MyUtility.generateRefNo();
            if (map.containsKey(refNo))
                throw new RuntimeException("DuplicateTransaction");

            map.put(refNo, true);
            if (i % 100000 == 0)
                System.out.println(i + "," + refNo);
        }
    }

    public static void main3(String[] args) {
        for (int i = 0; i < 100; i++) {
            String paddedNo = StringUtility.pad(getRandomIntInRange(0, 99).toString(), 2, "0", StringUtility.PadPosition.Behind);
            if (paddedNo.length() != 2)
                throw new RuntimeException();

            System.out.print(paddedNo + ",");
        }
    }

    public static boolean isMobileNoValid(String mobileNo) {
        if (mobileNo.length() != 12)
            return false;

        if (!mobileNo.startsWith("98"))
            return false;

        return true;
    }

    public static Integer getRandomIntInRange(int low, int high) {
        return new Random().nextInt(high - low) + low;
    }

    public static String getRandomNumber(int digCount) {
        StringBuilder sb = new StringBuilder(digCount);
        for (int i = 0; i < digCount; i++)
            if (i == 0)
                sb.append(random.nextInt(9) + 1);
            else
                sb.append(random.nextInt(10));
        return sb.toString();
    }

    public static String generateRefNo() throws Exception {
        String formattedRefNo = String.valueOf(UUID.randomUUID());
        return formattedRefNo.replaceAll("-", "");
    }

    public static String generateRefNo1() throws Exception {
        MessageDigest salt = MessageDigest.getInstance("SHA-256");
        salt.update(UUID.randomUUID().toString().getBytes("UTF-8"));
        return bytesToHex(salt.digest());
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static void main5(String[] args) {
        String iban = "IBAN1|IBAN2";
        String[] ibans = iban.split("\\|");
        System.out.println("ibans.length = " + ibans.length);
    }

    public static String getFlatStr(String[] input, String separator) {
        String out = "";
        if (input == null || input.length == 0)
            return out;

        for (String in : input) {
            out = out + in + separator;
        }

        return out.substring(0, out.length() - 1);
    }

    public static void checkMaxValidAccounts(String[] ibans, String[] pans) throws CustomException {
        Integer panCount = (pans != null ? pans.length : 0);
        Integer ibanCount = (ibans != null ? ibans.length : 0);

        if (panCount + ibanCount > WalletConstants.MAX_VALID_ACCOUNTS)
            throw new CustomException(WalletConstants.EXCEPTION_SOURCE_ID_WALLET_CHANNEL, "MaxAccountCountExceededException");

    }

    public static class LUHN {
        public static String generate(String code) {
            if (code == null)
                throw new IllegalArgumentException("Invalid code");

            code = code.trim();

            for (char c : code.toCharArray())
                if (!Character.isDigit(c))
                    throw new IllegalArgumentException("Code characters must be digit");

            int digit = 0;
            int counter = 0;

            for (int index = code.length(); index > 0; index--) {
                digit = digit + getSum(code.charAt(index - 1), counter);
                counter++;
            }
            String checkSum = new BigDecimal(digit)
                    .setScale(1, BigDecimal.ROUND_UNNECESSARY)
                    .divide(BigDecimal.TEN, MathContext.DECIMAL128)
                    .setScale(0, RoundingMode.UP)
                    .multiply(BigDecimal.TEN)
                    .subtract(new BigDecimal(digit))
                    .toString();

            return code.concat(checkSum);
        }

        private static int getSum(char c, int index) {
            int algNum;
            if (isOdd(index))
                algNum = Integer.parseInt(String.format("%s", c));
            else
                algNum = Integer.parseInt(String.format("%s", c)) * 2;

            String value = Integer.toString(algNum);
            if (value.length() > 1)
                return Integer.parseInt(String.format("%s", value.charAt(0))) +
                        Integer.parseInt(String.format("%s", value.charAt(1)));

            return algNum;
        }

        private static boolean isOdd(int number) {
            return (number % 2) != 0;
        }
    }
}

