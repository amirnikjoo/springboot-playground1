package ir.shaparak.eWallet.handlers;

import ir.shaparak.eWallet.api.dto.personal.WalletChangeMobileNoDto;
import ir.shaparak.eWallet.api.dto.personal.WalletResponseDto;
import ir.shaparak.eWallet.constants.ResponseConstants;
import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.IHandler;
import ir.shaparak.eWallet.model.User;
import ir.shaparak.eWallet.model.WalletRequest;
import ir.shaparak.eWallet.repository.WalletRequestRepository;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

public class InsertChangeMobileNo2WalletRequest extends IHandler {
    @Setter
    WalletRequestRepository walletRequestRepository;

    @Override
    public void process(Map map) throws Exception {
        String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
        log.debug("-- refNo: {} ", refNo);

        User user = (User) map.get(WalletConstants.USER_OBJECT);

//        log.info("logged in, userId: {} ", user.getId());

        WalletChangeMobileNoDto requestDto = (WalletChangeMobileNoDto) map.get(WalletConstants.REQUEST_OBJECT);
        WalletResponseDto responseDto = (WalletResponseDto) map.get(WalletConstants.WALLET_RESPONSE_OBJECT);
        Integer handle = WalletConstants.HANDLE;
        Integer exceptionSourceId = WalletConstants.EXCEPTION_SOURCE_ID_NO_EXCEPTION;
        Integer lastActionCode = ResponseConstants.APPROVED_RESPONSE_CODE_INT;
        if (map.containsKey(WalletConstants.EXCEPTION_CLASS_OBJECT)) {
            handle = WalletConstants.HANDLE_NOT;
            exceptionSourceId = (Integer) map.get(WalletConstants.EXCEPTION_SOURCE_ID);
            lastActionCode = Integer.valueOf(responseDto.getResponseCode());
        }

        WalletRequest walletRequest = WalletRequest.builder()
                .userId(user.getId())
                .serviceId(WalletConstants.SERVICE_ID_CHANGE_MOBILE_NO)
                .walletId(requestDto.getWalletId())
//                .walletStatus()
                .stan(requestDto.getStan())
                .mobileNumber(requestDto.getMobileNumber())
                .referenceNumber(refNo)
//                .riskLevel((short) 0)
//                .nationalIdType((short) 0)
//                .nationalId("")
//                .registerDate()
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
