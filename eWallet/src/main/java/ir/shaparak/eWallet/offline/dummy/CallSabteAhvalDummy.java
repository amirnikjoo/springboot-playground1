package ir.shaparak.eWallet.offline.dummy;

import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.IHandler;
import ir.shaparak.eWallet.model.WalletRequest;

import java.util.Map;

public class CallSabteAhvalDummy extends IHandler {

    @Override
    public void process(Map map) throws Exception {
        String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
        WalletRequest walletRequest = (WalletRequest) map.get(WalletConstants.REQUEST_OBJECT);

        //doCall sabte ahval and persist output
        //check output values with inputs > exception throws if needed
        log.debug("--{}, call sabte ahval successfully, nationalId = {}, registerDate = {} ", refNo, walletRequest.getNationalId(), walletRequest.getRegisterDate());

    }
}
