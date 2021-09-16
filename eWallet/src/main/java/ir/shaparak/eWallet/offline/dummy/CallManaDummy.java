package ir.shaparak.eWallet.offline.dummy;

import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.IHandler;

import java.util.Map;

public class CallManaDummy extends IHandler {

    @Override
    public void process(Map map) throws Exception {
        String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
        String pan = (String) map.get(WalletConstants.WALLET_PAN);

        //doCall mana and persist output
        //check output values with inputs > exception throws if needed
        log.debug("--{}, call mana successfully, iban =  ", refNo, pan);
    }
}
