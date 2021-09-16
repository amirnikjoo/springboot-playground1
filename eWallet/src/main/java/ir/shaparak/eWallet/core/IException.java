package ir.shaparak.eWallet.core;

import java.util.HashMap;
import java.util.Map;

public abstract class IException extends Exception {
    static final long serialVersionUID = -334147822944743062L;
    Integer sourceId;
    String myMessage = "";
    Map additionalData = new HashMap();

    public IException(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public IException(Integer sourceId, String myMessage) {
        super(myMessage);
        this.sourceId = sourceId;
    }

    public IException(Integer sourceId, String myMessage, Map additionalData) {
        super(myMessage);
        this.sourceId = sourceId;
        this.additionalData = additionalData;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public String getMyMessage() {
        return myMessage;
    }

    public void setMessage(String myMessage) {
        this.myMessage = myMessage;
    }

    public Map getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(Map additionalData) {
        this.additionalData = additionalData;
    }
}
