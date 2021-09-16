package ir.shaparak.eWallet.handlers.wrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.shaparak.eWallet.api.dto.personal.WalletInquiryDto;
import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.CustomException;
import ir.shaparak.eWallet.core.IHandler;

import java.io.IOException;
import java.util.Map;

public class WrapInquiryObject extends IHandler {
    @Override
    public void process(Map map) throws Exception {
        String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
        log.debug("-- refNo: {} ", refNo);

        String jsonBodyString = (String) map.get(WalletConstants.JSON_BODY_STRING);

        ObjectMapper mapper = new ObjectMapper();
        WalletInquiryDto inputDto;
        try {
            inputDto = mapper.readValue(jsonBodyString, WalletInquiryDto.class);
        } catch (IOException e) {
            log.error("JSON_MAPPING_EXCEPTION. body json = {}", jsonBodyString);
            throw new CustomException(WalletConstants.EXCEPTION_SOURCE_ID_WALLET_CHANNEL,
                    "FormatErrorException");
        }

        map.put(WalletConstants.REQUEST_OBJECT, inputDto);
    }
}
