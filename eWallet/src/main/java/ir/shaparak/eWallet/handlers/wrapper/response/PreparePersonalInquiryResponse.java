package ir.shaparak.eWallet.handlers.wrapper.response;

import ir.shaparak.eWallet.api.dto.ResponseCodeDto;
import ir.shaparak.eWallet.api.dto.personal.WalletResponseDto;
import ir.shaparak.eWallet.constants.ResponseConstants;
import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.IHandler;
import ir.shaparak.eWallet.model.WalletRequest;
import ir.shaparak.eWallet.repository.PersonalWalletRepository;
import ir.shaparak.eWallet.util.StringUtility;
import lombok.Setter;
import org.springframework.dao.DataAccessException;

import java.util.Map;

public class PreparePersonalInquiryResponse extends IHandler {

    @Setter
    PersonalWalletRepository personalWalletRepository;

    @Override
    public void process(Map map) throws Exception {
        String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
        log.debug("-- refNo: {} ", refNo);

        String responseCode = ResponseConstants.APPROVED_RESPONSE_CODE;
        String responseCodeDesc = ResponseConstants.APPROVED_RESPONSE_DESC;
        Integer exceptionSourceId = WalletConstants.EXCEPTION_SOURCE_ID_NO_EXCEPTION;
        String sourceExceptionDesc = WalletConstants.EXCEPTION_SOURCE;
        String walletId = "";

        if (map.containsKey(WalletConstants.EXCEPTION_CLASS_OBJECT)) {
            Exception exception = (Exception) map.get(WalletConstants.EXCEPTION_CLASS_OBJECT);
            String eMessage = exception.getMessage();
            ResponseCodeDto responseCodeDto;
            if (exception instanceof DataAccessException)
                eMessage = ResponseConstants.EXCEPTION_KEY_DATA_ACCESS;
            responseCodeDto = (ResponseCodeDto) ResponseConstants.responseMap.get(eMessage);
            responseCode = responseCodeDto.getResponseCode();
            responseCodeDesc = responseCodeDto.getDescription();
            exceptionSourceId = (Integer) map.get(WalletConstants.EXCEPTION_SOURCE_ID);
            sourceExceptionDesc = (String) WalletConstants.EXCEPTION_SOURCE_MAP.get(exceptionSourceId);
        } else {
            WalletRequest walletRequest = (WalletRequest) map.get(WalletConstants.WALLET_REQUEST_OBJECT);
            ResponseCodeDto responseCodeDto;
            switch (walletRequest.getHandled()) {
                case 0:
                    responseCodeDto = (ResponseCodeDto) ResponseConstants.responseMap.get(ResponseConstants.EXCEPTION_KEY_IN_PROGRESS);
                    responseCode = responseCodeDto.getResponseCode();
                    responseCodeDesc = responseCodeDto.getDescription();
//                    exceptionSourceId = WalletConstants.EXCEPTION_SOURCE_ID_NO_EXCEPTION;
//                    sourceExceptionDesc = WalletConstants.EXCEPTION_SOURCE;
                    break;

                case 1:
                    responseCodeDesc = ResponseConstants.APPROVED_RESPONSE_DESC_INQUIRY;
                    if (map.get(WalletConstants.ORIGINAL_SERVICE_ID).equals(WalletConstants.SERVICE_ID_REGISTER))
                        walletId = personalWalletRepository.findByUserIdAndWalletSerial(walletRequest.getUserId(), Long.valueOf(walletRequest.getWalletId())).getWalletId();
                    else
                        walletId = personalWalletRepository.findByUserIdAndWalletId(walletRequest.getUserId(), walletRequest.getWalletId()).getWalletId();

                    break;

                case 2:
                case 9:
                    String paddedResponseCode = StringUtility.pad(walletRequest.getLastActionCode() + "", 3, "0", StringUtility.PadPosition.Behind);
                    responseCodeDto = (ResponseCodeDto) ResponseConstants.responseMap.get(paddedResponseCode);
                    responseCode = paddedResponseCode;
                    responseCodeDesc = responseCodeDto.getDescription();
                    exceptionSourceId = walletRequest.getLastExceptionSource();
                    sourceExceptionDesc = (String) WalletConstants.EXCEPTION_SOURCE_MAP.get(walletRequest.getLastExceptionSource());
                    break;


                default:
                    //todo: what should i do
            }
        }

        WalletResponseDto responseDto = new WalletResponseDto("", refNo, walletId, responseCode, responseCodeDesc, sourceExceptionDesc);

        map.put(WalletConstants.WALLET_RESPONSE_OBJECT, responseDto);
        map.put(WalletConstants.EXCEPTION_SOURCE_ID, exceptionSourceId);

    }
}
