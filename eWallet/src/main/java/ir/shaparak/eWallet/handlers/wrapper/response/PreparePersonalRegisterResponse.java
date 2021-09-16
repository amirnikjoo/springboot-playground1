package ir.shaparak.eWallet.handlers.wrapper.response;

import ir.shaparak.eWallet.api.dto.ResponseCodeDto;
import ir.shaparak.eWallet.api.dto.personal.WalletRegisterRequestDto;
import ir.shaparak.eWallet.api.dto.personal.WalletResponseDto;
import ir.shaparak.eWallet.constants.ResponseConstants;
import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.IHandler;

import java.util.Map;

public class PreparePersonalRegisterResponse extends IHandler {
    @Override
    public void process(Map map) throws Exception {
        String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
        log.debug("-- refNo: {} ", refNo);

        String responseCode = ResponseConstants.APPROVED_RESPONSE_CODE;
        String responseCodeDesc = ResponseConstants.APPROVED_RESPONSE_DESC;
        String sourceException = WalletConstants.EXCEPTION_SOURCE;

        if (map.containsKey(WalletConstants.EXCEPTION_CLASS_OBJECT)) {
            Exception exception = (Exception) map.get(WalletConstants.EXCEPTION_CLASS_OBJECT);
            ResponseCodeDto responseCodeDto = (ResponseCodeDto) ResponseConstants.responseMap.get(exception.getMessage());
            responseCode = responseCodeDto.getResponseCode();
            responseCodeDesc = responseCodeDto.getDescription();
            sourceException = (String) WalletConstants.EXCEPTION_SOURCE_MAP.get(map.get(WalletConstants.EXCEPTION_SOURCE_ID));
        }

        WalletRegisterRequestDto requestDto = (WalletRegisterRequestDto) map.get(WalletConstants.REQUEST_OBJECT);

        WalletResponseDto responseDto = new WalletResponseDto(requestDto.getStan(), refNo, requestDto.getWalletSerial(),
                responseCode, responseCodeDesc, sourceException);

        map.put(WalletConstants.WALLET_RESPONSE_OBJECT, responseDto);
    }
}
