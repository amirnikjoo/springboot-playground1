package ir.shaparak.eWallet.handlers;

import ir.shaparak.eWallet.api.dto.personal.WalletChangeMobileNoDto;
import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.CustomException;
import ir.shaparak.eWallet.core.IHandler;
import ir.shaparak.eWallet.util.MyUtility;

import java.util.Map;

public class CheckChangeMobileNoRequestValidation extends IHandler {

    @Override
    public void process(Map map) throws Exception {
        String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
        log.debug("-- refNo: {} ", refNo);
        WalletChangeMobileNoDto requestDto = (WalletChangeMobileNoDto) map.get(WalletConstants.REQUEST_OBJECT);

        if (!MyUtility.isMobileNoValid(requestDto.getMobileNumber()))
            throw new CustomException(WalletConstants.EXCEPTION_SOURCE_ID_WALLET_CHANNEL, "InputValidationException");

    }
}
