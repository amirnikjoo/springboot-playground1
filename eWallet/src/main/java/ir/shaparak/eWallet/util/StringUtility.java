package ir.shaparak.eWallet.util;

public class StringUtility {
    public static String pad(String value, int length, String padValue, PadPosition position) {
        if (value == null)
            value = "";

        value = value.trim();
        if (value.length() < length) {
            String pad = "";
            for (int index = 0; index < length - value.length(); index++)
                pad += padValue;

            if (position == PadPosition.Front) {
                value = value + pad;
            } else {
                value = pad + value;
            }
        }
        return value.substring(0, length);
    }

    public static String unPad(String value, Integer length, String padValue) {
        value = value.replaceAll(padValue, "");
        if (length != null)
            return value.substring(0, (length > value.length() ? value.length() : length));

        return value;
    }

    public enum PadPosition {
        Behind, //00
        Front   //01
    }
}
