/*
package ir.shaparak.eWallet.core;

import java.util.HashMap;
import java.util.Map;

public class IRuntimeException extends RuntimeException {
    Integer sourceId = 0;
    Map additionalData = new HashMap();

    public IRuntimeException(Integer sourceId, Map additionalData) {
        this.sourceId = sourceId;
        this.additionalData = additionalData;
    }

    public IRuntimeException(Throwable cause, Integer sourceId) {
        super(cause);
        this.sourceId = sourceId;
    }

    public IRuntimeException(Throwable cause, Integer sourceId, Map additionalData) {
        super(cause);
        this.sourceId = sourceId;
        this.additionalData = additionalData;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Map getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(Map additionalData) {
        this.additionalData = additionalData;
    }

    public String toString() {
        return "sourceId = " + sourceId + ", additionalData = " + additionalData + ", " + super.toString();
    }

}*/
