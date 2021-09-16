package ir.shaparak.eWallet.handlers;

import ir.shaparak.eWallet.api.dto.personal.WalletChangeRiskLevelDto;
import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.CustomException;
import ir.shaparak.eWallet.core.IHandler;
import ir.shaparak.eWallet.model.PersonalWallet;
import lombok.Setter;

import java.util.Map;

public class CheckAccountBasedOnOldNewRiskLevel extends IHandler {

    @Setter
    Map oldNewRiskLevelMap;

    @Override
    public void process(Map map) throws Exception {
        String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);

        WalletChangeRiskLevelDto walletChangeRiskLevelDto = (WalletChangeRiskLevelDto) map.get(WalletConstants.REQUEST_OBJECT);
        log.debug("-- refNo: {}, riskLevel: {} ", refNo, walletChangeRiskLevelDto.getRiskLevel());
        if (walletChangeRiskLevelDto.getRiskLevel() != 1) {  // new risk level != 1
            PersonalWallet personalWallet = (PersonalWallet) map.get(WalletConstants.PERSONAL_WALLET_OBJECT);

            //current + "-" + new risk level
            String oldNewRiskLevel = personalWallet.getRiskLevel() + WalletConstants.SEPARATOR_DASH + walletChangeRiskLevelDto.getRiskLevel();

            IHandler imHandler = (IHandler) oldNewRiskLevelMap.get(oldNewRiskLevel);
            if (imHandler == null)
                throw new CustomException(WalletConstants.EXCEPTION_SOURCE_ID_WALLET_CHANNEL, "UnsupportedRequestException");

            imHandler.process(map);
        }
    }
}
