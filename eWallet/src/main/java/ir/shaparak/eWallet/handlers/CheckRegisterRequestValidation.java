package ir.shaparak.eWallet.handlers;

import ir.shaparak.eWallet.api.dto.personal.WalletRegisterRequestDto;
import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.CustomException;
import ir.shaparak.eWallet.core.IHandler;
import ir.shaparak.eWallet.util.MyUtility;

import java.util.Map;

public class CheckRegisterRequestValidation extends IHandler {

    @Override
    public void process(Map map) throws Exception {
        String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
        log.debug("-- refNo: {} ", refNo);
        WalletRegisterRequestDto walletRegisterRequestDto = (WalletRegisterRequestDto) map.get(WalletConstants.REQUEST_OBJECT);

        if (!MyUtility.isMobileNoValid(walletRegisterRequestDto.getMobileNumber()))
            throw new CustomException(WalletConstants.EXCEPTION_SOURCE_ID_WALLET_CHANNEL, "InputValidationException");

        if (!WalletConstants.IDENTIFIER_TYPE_MAP.containsKey(walletRegisterRequestDto.getPersonDto().getIdentifierType()))
            throw new CustomException(WalletConstants.EXCEPTION_SOURCE_ID_WALLET_CHANNEL, "InvalidIdentifierTypeException");
    }
}
