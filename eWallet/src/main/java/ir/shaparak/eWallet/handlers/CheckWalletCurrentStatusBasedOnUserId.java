package ir.shaparak.eWallet.handlers;

import ir.shaparak.eWallet.api.dto.personal.WalletChangeStatusDto;
import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.CustomException;
import ir.shaparak.eWallet.core.IHandler;
import ir.shaparak.eWallet.model.PersonalWallet;
import ir.shaparak.eWallet.model.User;

import java.util.Map;

public class CheckWalletCurrentStatusBasedOnUserId extends IHandler {

    @Override
    public void process(Map map) throws Exception {
        String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
        log.debug("-- refNo: {} ", refNo);

        WalletChangeStatusDto walletChangeStatusDto = (WalletChangeStatusDto) map.get(WalletConstants.REQUEST_OBJECT);
        Integer statusValue = (Integer) WalletConstants.WALLET_STATUS_TO_WEIGHT_MAP.get(walletChangeStatusDto.getStatus().trim().toUpperCase());
        if (statusValue == null)
            throw new CustomException(WalletConstants.EXCEPTION_SOURCE_ID_WALLET_CHANNEL, "InvalidStatusRequestException");

        User user = (User) map.get(WalletConstants.USER_OBJECT);
        PersonalWallet personalWallet = (PersonalWallet) map.get(WalletConstants.PERSONAL_WALLET_OBJECT);

        Integer currentStatus = personalWallet.getStatus();
        Integer currentShaparakStatus = personalWallet.getShaparakStatus();

        if (user.getUserType().equals(WalletConstants.USER_TYPE_BANK_OPERATOR)) {
            if (currentStatus.equals(statusValue))
                throw new CustomException(WalletConstants.EXCEPTION_SOURCE_ID_WALLET_CHANNEL, "InvalidStatusRequestException");

            // minimum CLOSE weight
            if (currentStatus >= WalletConstants.WALLET_STATUS_CLOSE_TEMP || currentShaparakStatus >= WalletConstants.WALLET_STATUS_CLOSE_TEMP)
                throw new CustomException(WalletConstants.EXCEPTION_SOURCE_ID_WALLET_CHANNEL, "BlockedWalletException");

        } else if (user.getUserType().equals(WalletConstants.USER_TYPE_SHAPARAK_OPERATION)) {
            if (currentShaparakStatus.equals(statusValue))
                throw new CustomException(WalletConstants.EXCEPTION_SOURCE_ID_WALLET_CHANNEL, "InvalidStatusRequestException");
        }


    }
}
