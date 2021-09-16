package ir.shaparak.eWallet.handlers;

import ir.shaparak.eWallet.api.dto.personal.WalletChangeAccountDto;
import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.CustomException;
import ir.shaparak.eWallet.core.IHandler;
import ir.shaparak.eWallet.model.PersonalWalletAccount;
import ir.shaparak.eWallet.repository.PersonalWalletAccountRepository;
import lombok.Setter;

import java.util.Map;

public class CheckPersonalWalletAccountExist extends IHandler {

    @Setter
    PersonalWalletAccountRepository personalWalletAccountRepository;

    @Override
    public void process(Map map) throws Exception {
        String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
        log.debug("-- refNo: {} ", refNo);
//        PersonalWallet personalWallet = (PersonalWallet) map.get(WalletConstants.PERSONAL_WALLET_OBJECT);

        WalletChangeAccountDto requestDto = (WalletChangeAccountDto) map.get(WalletConstants.REQUEST_OBJECT);

        //todo: maybe get count of account is a better idea to check WalletConstant.MAX_VALID_ACCOUNTS
        PersonalWalletAccount personalWalletAccount = personalWalletAccountRepository.findPersonalWalletAccountByWalletIdAndAccountIdAndStatus(
                requestDto.getWalletId(), requestDto.getAccountId(), WalletConstants.WALLET_ACCOUNT_STATUS_ACTIVE);

        if (map.get(WalletConstants.METHOD_ACCOUNT_TYPE) == WalletConstants.SERVICE_ID_ADD_ACCOUNT) {
            if (personalWalletAccount != null)
                throw new CustomException(WalletConstants.EXCEPTION_SOURCE_ID_WALLET_CHANNEL, "AccountAlreadyExistsException");
        } else {
            if (personalWalletAccount == null)
                throw new CustomException(WalletConstants.EXCEPTION_SOURCE_ID_WALLET_CHANNEL, "AccountNotFoundException");

            //check if only account could not be removed
            Integer count = personalWalletAccountRepository.findPersonalWalletAccountByWalletIdAndStatus(
                    requestDto.getWalletId(), WalletConstants.WALLET_ACCOUNT_STATUS_ACTIVE);
            if (count == 1)
                throw new CustomException(WalletConstants.EXCEPTION_SOURCE_ID_WALLET_CHANNEL, "MinAccountCountExceededException");
        }

    }

}
