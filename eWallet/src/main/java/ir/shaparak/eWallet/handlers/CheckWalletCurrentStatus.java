package ir.shaparak.eWallet.handlers;

import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.CustomException;
import ir.shaparak.eWallet.core.IHandler;
import ir.shaparak.eWallet.model.PersonalWallet;

import java.util.Map;

public class CheckWalletCurrentStatus extends IHandler {

    @Override
    public void process(Map map) throws Exception {
        String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
        log.debug("-- refNo: {} ", refNo);
        PersonalWallet personalWallet = (PersonalWallet) map.get(WalletConstants.PERSONAL_WALLET_OBJECT);

        //todo: if status = WALLET_STATUS_ACTIVE_WARNING then exception should not throw . in which cases should reject the request
        if (!personalWallet.getShaparakStatus().equals(WalletConstants.WALLET_STATUS_ACTIVE) ||
                !personalWallet.getStatus().equals(WalletConstants.WALLET_STATUS_ACTIVE))
            throw new CustomException(WalletConstants.EXCEPTION_SOURCE_ID_WALLET_CHANNEL, "InactiveWalletException");

    }
}
