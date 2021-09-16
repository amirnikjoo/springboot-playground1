package ir.shaparak.eWallet.handlers;

import ir.shaparak.eWallet.api.dto.personal.WalletChangeRiskLevelDto;
import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.CustomException;
import ir.shaparak.eWallet.core.IHandler;
import ir.shaparak.eWallet.repository.PersonalWalletRepository;
import ir.shaparak.eWallet.util.AccountUtility;
import lombok.Setter;

import java.util.Map;

public class CheckChangeRiskLevelAccounts extends IHandler {
    @Setter
    PersonalWalletRepository personalWalletRepository;

    @Override
    public void process(Map map) throws Exception {
        String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
        log.debug("-- refNo: {} ", refNo);

        WalletChangeRiskLevelDto walletChangeRiskLevelDto = (WalletChangeRiskLevelDto) map.get(WalletConstants.REQUEST_OBJECT);

        if (!AccountUtility.isValidAccountExist(walletChangeRiskLevelDto.getIban(), walletChangeRiskLevelDto.getPan()))
            throw new CustomException(WalletConstants.EXCEPTION_SOURCE_ID_WALLET_CHANNEL, "invalidAccountException");

    }


}
