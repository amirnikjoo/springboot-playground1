package ir.shaparak.eWallet.handlers.wrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.shaparak.eWallet.api.dto.personal.WalletRegisterRequestDto;
import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.CustomException;
import ir.shaparak.eWallet.core.IHandler;

import java.io.IOException;
import java.util.Map;

public class WrapRegisterRequestObject extends IHandler {
    @Override
    public void process(Map map) throws Exception {
        String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
        log.debug("-- refNo: {} ", refNo);

        String jsonBodyString = (String) map.get(WalletConstants.JSON_BODY_STRING);

        ObjectMapper mapper = new ObjectMapper();
        WalletRegisterRequestDto inputDto;  //todo: add officer object to Dto
        try {
            inputDto = mapper.readValue(jsonBodyString, WalletRegisterRequestDto.class);
        } catch (IOException e) {
            log.error("JSON_MAPPING_EXCEPTION. body json = {}", jsonBodyString);
            throw new CustomException(WalletConstants.EXCEPTION_SOURCE_ID_WALLET_CHANNEL,
                    "FormatErrorException");
        }

        map.put(WalletConstants.REQUEST_OBJECT, inputDto);
//        map.put(WalletConstants.REQUEST_WALLET_ID, inputDto.getWalletSerial()); todo: if needed

    }
}
