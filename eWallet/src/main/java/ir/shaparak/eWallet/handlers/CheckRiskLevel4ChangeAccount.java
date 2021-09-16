package ir.shaparak.eWallet.handlers;

import ir.shaparak.eWallet.api.dto.personal.WalletChangeAccountDto;
import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.CustomException;
import ir.shaparak.eWallet.core.IHandler;
import ir.shaparak.eWallet.model.PersonalWallet;
import ir.shaparak.eWallet.util.AccountUtility;

import java.util.Map;

public class CheckRiskLevel4ChangeAccount extends IHandler {

    @Override
    public void process(Map map) throws Exception {
        String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
        log.debug("-- refNo: {} ", refNo);
        PersonalWallet personalWallet = (PersonalWallet) map.get(WalletConstants.PERSONAL_WALLET_OBJECT);

        if (personalWallet.getRiskLevel().equals(WalletConstants.WALLET_RISK_LEVEL_1) ||
                personalWallet.getRiskLevel() > WalletConstants.WALLET_RISK_LEVEL_3)
            throw new CustomException(WalletConstants.EXCEPTION_SOURCE_ID_WALLET_CHANNEL, "InvalidRequestException");

        WalletChangeAccountDto requestDto = (WalletChangeAccountDto) map.get(WalletConstants.REQUEST_OBJECT);

        if (!AccountUtility.isValidAccountByType(requestDto.getAccountType(), requestDto.getAccountId()))
            throw new CustomException(WalletConstants.EXCEPTION_SOURCE_ID_WALLET_CHANNEL, "InvalidAccountException");

    }

}
