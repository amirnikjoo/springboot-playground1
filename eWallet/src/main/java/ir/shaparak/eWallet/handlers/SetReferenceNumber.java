package ir.shaparak.eWallet.handlers;

import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.IHandler;
import ir.shaparak.eWallet.util.MyUtility;

import java.util.Map;

public class SetReferenceNumber extends IHandler {
    @Override
    public void process(Map map) throws Exception {

        map.put(WalletConstants.REQUEST_TIME, System.currentTimeMillis());

        String refNo = MyUtility.generateRefNo();
        log.debug("---- inside -- {} ", refNo);

        map.put(WalletConstants.START_TIME, System.currentTimeMillis());
        map.put(WalletConstants.REFERENCE_NUMBER, refNo); // set UUID
    }
}
