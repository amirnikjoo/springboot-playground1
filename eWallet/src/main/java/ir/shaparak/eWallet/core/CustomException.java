package ir.shaparak.eWallet.core;

import java.util.Map;

public class CustomException extends IException {

    public CustomException(Integer sourceId) {
        super(sourceId);
    }

    public CustomException(Integer sourceId, String message) {
        super(sourceId, message);
    }

    public CustomException(Integer sourceId, String message, Map additionalData) {
        super(sourceId, message, additionalData);
    }

    @Override
    public String toString() {
        return "CustomException{" +
                "sourceId=" + sourceId +
                ", myMessage='" + myMessage + '\'' +
                '}';
    }
}