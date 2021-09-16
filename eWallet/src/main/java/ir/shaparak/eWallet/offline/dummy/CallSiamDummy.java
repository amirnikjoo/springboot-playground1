package ir.shaparak.eWallet.offline.dummy;

import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.IHandler;

import java.util.Map;

public class CallSiamDummy extends IHandler {

    @Override
    public void process(Map map) throws Exception {
        String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
        String iban = (String) map.get(WalletConstants.WALLET_IBAN);

        //doCall siam and persist output
        //check output values with inputs > exception throws if needed
        log.debug("--{}, call siam successfully, iban =  ", refNo, iban);
    }
}
