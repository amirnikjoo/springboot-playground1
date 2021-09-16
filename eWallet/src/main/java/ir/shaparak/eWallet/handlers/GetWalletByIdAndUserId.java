package ir.shaparak.eWallet.handlers;

import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.CustomException;
import ir.shaparak.eWallet.core.IHandler;
import ir.shaparak.eWallet.model.PersonalWallet;
import ir.shaparak.eWallet.model.User;
import ir.shaparak.eWallet.repository.PersonalWalletRepository;
import lombok.Setter;

import java.util.Map;

public class GetWalletByIdAndUserId extends IHandler {
    @Setter
    PersonalWalletRepository personalWalletRepository;

    @Override
    public void process(Map map) throws Exception {
        String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
        log.debug("-- refNo: {} ", refNo);
        User user = (User) map.get(WalletConstants.USER_OBJECT);

        PersonalWallet personalWallet = personalWalletRepository.findByUserIdAndWalletId(user.getId(), (String) map.get(WalletConstants.REQUEST_WALLET_ID));
        if (personalWallet == null)
            throw new CustomException(WalletConstants.EXCEPTION_SOURCE_ID_WALLET_CHANNEL, "WalletNotFoundException");

        map.put(WalletConstants.PERSONAL_WALLET_OBJECT, personalWallet);
//        log.debug("-- refNo: {} , count: {}", refNo, count);

    }
}
