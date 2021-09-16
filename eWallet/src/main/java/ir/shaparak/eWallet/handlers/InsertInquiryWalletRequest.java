package ir.shaparak.eWallet.handlers;

import ir.shaparak.eWallet.api.dto.personal.WalletInquiryDto;
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

public class InsertInquiryWalletRequest extends IHandler {
    @Setter
    WalletRequestRepository walletRequestRepository;

    @Override
    public void process(Map map) throws Exception {
        String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
        log.debug("-- refNo: {} ", refNo);

        User user = (User) map.get(WalletConstants.USER_OBJECT);

//        log.info("logged in, userId: {} ", user.getId());

        WalletInquiryDto requestDto = (WalletInquiryDto) map.get(WalletConstants.REQUEST_OBJECT);
        WalletResponseDto responseDto = (WalletResponseDto) map.get(WalletConstants.WALLET_RESPONSE_OBJECT);

        WalletRequest walletRequest = WalletRequest.builder()
                .userId(user.getId())
                .serviceId(WalletConstants.SERVICE_ID_INQUIRY)
//                .walletId(requestDto.getWalletId())  //todo: fill if needed!
                .stan(requestDto.getStan())
                .referenceNumber(refNo)
                .originalReferenceNumber(requestDto.getOriginalReferenceNumber())
                .originalStan(requestDto.getOriginalStan())
//                .ibans(iban)
//                .pans(pan)
//                .accountType(requestDto.getAccountType())
//                .walletStatus()
//                .riskLevel((short) 0)
//                .mobileNumber(requestDto.getMobileNumber())
//                .nationalIdType((short) 0)
//                .nationalId("")
//                .registerDate()
                .handled(WalletConstants.HANDLE_NOT)
                .responseCode(Integer.valueOf(responseDto.getResponseCode()))
                .lastActionCode(Integer.valueOf(responseDto.getResponseCode()))
                .lastExceptionSource((Integer) map.get(WalletConstants.EXCEPTION_SOURCE_ID))
                .handled(WalletConstants.HANDLE_NOT)
                .responseCode(Integer.valueOf(responseDto.getResponseCode()))
                .step(WalletConstants.INIT_STEP)
                .actionCodes(WalletConstants.EMPTY_STRING)
                .tryCount(WalletConstants.ZERO)
                .maxTryCount(WalletConstants.ZERO)
                .duration((int) (System.currentTimeMillis() - (long) map.get(WalletConstants.START_TIME)))
                .createDate(new Date())
                .lastEditDate(new Timestamp(System.currentTimeMillis()))
                .build();

        walletRequestRepository.save(walletRequest);

    }
}
