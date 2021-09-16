package ir.shaparak.eWallet.handlers;

import ir.shaparak.eWallet.api.dto.personal.WalletRegisterRequestDto;
import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.CustomException;
import ir.shaparak.eWallet.core.IHandler;
import ir.shaparak.eWallet.model.User;
import ir.shaparak.eWallet.repository.PersonalWalletRepository;
import lombok.Setter;

import java.util.Map;

public class CheckDuplicateWalletRequest extends IHandler {
    @Setter
    PersonalWalletRepository personalWalletRepository;

    @Override
    public void process(Map map) throws Exception {
        String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
        User user = (User) map.get(WalletConstants.USER_OBJECT);

        WalletRegisterRequestDto walletRegisterRequestDto = (WalletRegisterRequestDto) map.get(WalletConstants.REQUEST_OBJECT);
        log.debug("-- refNo: {}, {}", refNo, walletRegisterRequestDto.getWalletSerial());

        Integer c = personalWalletRepository.countByUserIdAndWalletSerial(
                user.getId(), Long.valueOf(walletRegisterRequestDto.getWalletSerial()));

        if (c != null && c > 0)
            throw new CustomException(WalletConstants.EXCEPTION_SOURCE_ID_WALLET_CHANNEL, "DuplicateTransmissionException");

    }
}
