package ir.shaparak.eWallet.handlers;

import ir.shaparak.eWallet.api.dto.personal.WalletRegisterRequestDto;
import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.CustomException;
import ir.shaparak.eWallet.core.IHandler;
import ir.shaparak.eWallet.util.AccountUtility;

import java.util.Map;

public class CheckAccountBasedOnRiskLevel extends IHandler {

    @Override
    public void process(Map map) throws Exception {
        String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
        log.debug("-- refNo: {} ", refNo);
        WalletRegisterRequestDto walletRegisterRequestDto = (WalletRegisterRequestDto) map.get(WalletConstants.REQUEST_OBJECT);

        if (walletRegisterRequestDto.getRiskLevel().equals(WalletConstants.WALLET_RISK_LEVEL_1)) {
            walletRegisterRequestDto.setIban(new String[]{});
            walletRegisterRequestDto.setPan(new String[]{});
        } else if (walletRegisterRequestDto.getRiskLevel().equals(WalletConstants.WALLET_RISK_LEVEL_2)) {
            if (!AccountUtility.isValidAccountExist(walletRegisterRequestDto.getIban(), walletRegisterRequestDto.getPan()))
                throw new CustomException(WalletConstants.EXCEPTION_SOURCE_ID_WALLET_CHANNEL, "InvalidAccountException");
        } else if (walletRegisterRequestDto.getRiskLevel() >= WalletConstants.WALLET_RISK_LEVEL_3)
            throw new CustomException(WalletConstants.EXCEPTION_SOURCE_ID_WALLET_CHANNEL, "InvalidRequestException");

    }

}
