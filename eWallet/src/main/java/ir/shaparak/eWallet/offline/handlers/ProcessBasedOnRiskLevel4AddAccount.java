package ir.shaparak.eWallet.offline.handlers;

import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.CustomException;
import ir.shaparak.eWallet.core.IHandler;
import ir.shaparak.eWallet.model.PersonalWallet;
import lombok.Setter;

import java.util.Map;

public class ProcessBasedOnRiskLevel4AddAccount extends IHandler {
    @Setter
    Map riskLevelMap;

    @Override
    public void process(Map map) throws Exception {
        String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
        log.debug("-- refNo: {} ", refNo);
        PersonalWallet personalWallet = (PersonalWallet) map.get(WalletConstants.PERSONAL_WALLET_OBJECT);
        Integer riskLevel = personalWallet.getRiskLevel();

        IHandler imHandler = (IHandler) riskLevelMap.get("" + riskLevel);
        if (imHandler == null)
            throw new CustomException(WalletConstants.EXCEPTION_SOURCE_ID_WALLET_CHANNEL, "UnsupportedRequestException");

        imHandler.process(map);
    }
}
