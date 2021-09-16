package ir.shaparak.eWallet.handlers;

import ir.shaparak.eWallet.api.dto.personal.WalletChangeRiskLevelDto;
import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.CustomException;
import ir.shaparak.eWallet.core.IHandler;
import ir.shaparak.eWallet.model.PersonalWallet;

import java.util.Map;

public class CheckLevel3Validation extends IHandler {

    @Override
    public void process(Map map) throws Exception {
        String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);

        WalletChangeRiskLevelDto walletChangeRiskLevelDto = (WalletChangeRiskLevelDto) map.get(WalletConstants.REQUEST_OBJECT);
        log.debug("-- refNo: {}, riskLevel: {} ", refNo, walletChangeRiskLevelDto.getRiskLevel());
        if (walletChangeRiskLevelDto.getRiskLevel().equals(WalletConstants.WALLET_RISK_LEVEL_3)) {
            PersonalWallet personalWallet = (PersonalWallet) map.get(WalletConstants.PERSONAL_WALLET_OBJECT);
            if (personalWallet.getRiskLevel() != 2)
                throw new CustomException(WalletConstants.EXCEPTION_SOURCE_ID_WALLET_CHANNEL, "InvalidRequestException");

//            checkIfOneYearPastFromLevel2() todo:
        }
    }
}
