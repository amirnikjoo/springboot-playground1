package ir.shaparak.eWallet.handlers;

import ir.shaparak.eWallet.api.dto.personal.WalletInquiryDto;
import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.CustomException;
import ir.shaparak.eWallet.core.IHandler;
import ir.shaparak.eWallet.model.User;
import ir.shaparak.eWallet.model.WalletRequest;
import ir.shaparak.eWallet.repository.WalletRequestRepository;
import lombok.Setter;

import java.util.Map;

public class GetOriginalRequest extends IHandler {
    @Setter
    WalletRequestRepository walletRequestRepository;

    @Override
    public void process(Map map) throws Exception {
        String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
        log.debug("-- refNo: {} ", refNo);
        User user = (User) map.get(WalletConstants.USER_OBJECT);
        WalletInquiryDto requestDto = (WalletInquiryDto) map.get(WalletConstants.REQUEST_OBJECT);
        WalletRequest walletRequest;

        if (requestDto.getOriginalReferenceNumber() != null && requestDto.getOriginalReferenceNumber().length() > 0)
            walletRequest = walletRequestRepository.findByUserIdAndRefNo(user.getId(), requestDto.getOriginalReferenceNumber());
        else
            walletRequest = walletRequestRepository.findByUserIdAndStan(user.getId(), requestDto.getOriginalStan());

        if (walletRequest == null)
            throw new CustomException(WalletConstants.EXCEPTION_SOURCE_ID_WALLET_CHANNEL, "RequestNotFoundException");

        map.put(WalletConstants.WALLET_REQUEST_OBJECT, walletRequest);
        map.put(WalletConstants.ORIGINAL_SERVICE_ID, walletRequest.getServiceId());

    }
}
