package ir.shaparak.eWallet.offline.handlers;

import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.CustomException;
import ir.shaparak.eWallet.core.IHandler;
import ir.shaparak.eWallet.model.WalletRequest;
import lombok.Setter;

import java.util.Map;

public class ProcessBasedOnPersonType extends IHandler {
    @Setter
    Map personTypeMap;

    @Override
    public void process(Map map) throws Exception {
        String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
        WalletRequest walletRequest = (WalletRequest) map.get(WalletConstants.REQUEST_OBJECT);
        IHandler imHandler = (IHandler) personTypeMap.get("" + walletRequest.getNationalType());
        if (imHandler == null)
            throw new CustomException(WalletConstants.EXCEPTION_SOURCE_ID_WALLET_CHANNEL, "UnsupportedRequestException");

        imHandler.process(map);
        log.debug("-- refNo: {} ", refNo);
    }
}
