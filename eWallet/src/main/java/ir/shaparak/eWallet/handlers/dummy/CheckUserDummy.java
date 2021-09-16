package ir.shaparak.eWallet.handlers.dummy;

import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.IHandler;
import ir.shaparak.eWallet.model.User;

import java.util.Map;

public class CheckUserDummy extends IHandler {
    @Override
    public void process(Map map) throws Exception {
        String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
        log.debug("-- refNo: {} ", refNo);

        //todo: remove
        //User user = userRepository.findUserByUsernameAndPassword(extractUserPass[0], extractUserPass[1]);
        User user = new User(1, "user", "user123", "610433", "000", "بانک ملت", "به پرداخت", WalletConstants.USER_TYPE_BANK_OPERATOR);

        map.put(WalletConstants.USER_OBJECT, user);
    }
}
