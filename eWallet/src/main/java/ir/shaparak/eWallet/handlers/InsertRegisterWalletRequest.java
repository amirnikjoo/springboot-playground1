package ir.shaparak.eWallet.handlers;

import ir.shaparak.eWallet.api.dto.personal.WalletRegisterRequestDto;
import ir.shaparak.eWallet.api.dto.personal.WalletResponseDto;
import ir.shaparak.eWallet.constants.ResponseConstants;
import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.IHandler;
import ir.shaparak.eWallet.model.User;
import ir.shaparak.eWallet.model.WalletRequest;
import ir.shaparak.eWallet.repository.WalletRequestRepository;
import ir.shaparak.eWallet.util.MyUtility;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

public class InsertRegisterWalletRequest extends IHandler {
    @Setter
    WalletRequestRepository walletRequestRepository;

    @Override
    public void process(Map map) throws Exception {
        String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
        log.debug("-- refNo: {} ", refNo);

        User user = (User) map.get(WalletConstants.USER_OBJECT);

        WalletRegisterRequestDto walletRegisterRequestDto = (WalletRegisterRequestDto) map.get(WalletConstants.REQUEST_OBJECT);

        MyUtility.checkMaxValidAccounts(walletRegisterRequestDto.getIban(), walletRegisterRequestDto.getPan());

        Integer nationalIdType = (Integer) WalletConstants.IDENTIFIER_TYPE_MAP.get(walletRegisterRequestDto.getPersonDto().getIdentifierType());

        WalletResponseDto responseDto = (WalletResponseDto) map.get(WalletConstants.WALLET_RESPONSE_OBJECT);
        Integer handle = WalletConstants.HANDLE;
        Integer exceptionSourceId = WalletConstants.EXCEPTION_SOURCE_ID_NO_EXCEPTION;
        Integer lastActionCode = ResponseConstants.APPROVED_RESPONSE_CODE_INT;
        if (map.containsKey(WalletConstants.EXCEPTION_CLASS_OBJECT)) {
            handle = WalletConstants.HANDLE_NOT;
            exceptionSourceId = (Integer) map.get(WalletConstants.EXCEPTION_SOURCE_ID);
            lastActionCode = Integer.valueOf(responseDto.getResponseCode());
        }

        String flatIban = MyUtility.getFlatStr(walletRegisterRequestDto.getIban(), WalletConstants.SEPARATOR_PIPE);
        String flatPan = MyUtility.getFlatStr(walletRegisterRequestDto.getPan(), WalletConstants.SEPARATOR_PIPE);
        WalletRequest walletRequest = WalletRequest.builder()
                .userId(user.getId())
                .serviceId(WalletConstants.SERVICE_ID_REGISTER) //register request
                .walletId(walletRegisterRequestDto.getWalletSerial())
//                .walletStatus()
                .riskLevel(walletRegisterRequestDto.getRiskLevel())
                .stan(walletRegisterRequestDto.getStan())
                .mobileNumber(walletRegisterRequestDto.getMobileNumber())
                .postalCode(walletRegisterRequestDto.getPostalCode())
                .referenceNumber(refNo)
                .nationalType(nationalIdType)
                .nationalId(walletRegisterRequestDto.getPersonDto().getIdentifier())
                .registerDate(walletRegisterRequestDto.getPersonDto().getRegisterDate())
                .ibans(flatIban)
                .pans(flatPan)
                .step(WalletConstants.INIT_STEP)
                .handled(handle)
                .responseCode(Integer.valueOf(responseDto.getResponseCode()))
                .lastActionCode(lastActionCode)
                .lastExceptionSource(exceptionSourceId)
                .actionCodes(WalletConstants.EMPTY_STRING)
                .tryCount(WalletConstants.TRY_COUNT)
                .maxTryCount(WalletConstants.MAX_TRY_COUNT)
                .duration((int) (System.currentTimeMillis() - (long) map.get(WalletConstants.START_TIME)))
                .createDate(new Date())
                .lastEditDate(new Timestamp(System.currentTimeMillis()))
                .build();

        walletRequestRepository.save(walletRequest);

    }

}
