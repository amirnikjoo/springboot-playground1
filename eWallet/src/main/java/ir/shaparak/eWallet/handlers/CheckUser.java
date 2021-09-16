package ir.shaparak.eWallet.handlers;

import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.CustomException;
import ir.shaparak.eWallet.core.IHandler;
import ir.shaparak.eWallet.model.User;
import ir.shaparak.eWallet.repository.UserRepository;
import lombok.Setter;

import java.util.Base64;
import java.util.Map;

public class CheckUser extends IHandler {
    @Setter
    UserRepository userRepository;

    @Override
    public void process(Map map) throws Exception {
        String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
        log.debug("-- refNo: {} ", refNo);
        Map headersMap = (Map) map.get(WalletConstants.HEADERS);

        if (!headersMap.containsKey(WalletConstants.AUTHORIZATION_HEADER))
            throw new CustomException(WalletConstants.EXCEPTION_SOURCE_ID_WALLET_CHANNEL,
                    "SecurityViolationException");

        String auth = (String) headersMap.get(WalletConstants.AUTHORIZATION_HEADER);
        log.debug("auth : {}", auth);

        String[] authorization = auth.split(" ");
        log.debug("authorization[0] : {}, authorization[1] : {}", authorization[0], authorization[1]);
        String decAuthorization = new String(Base64.getDecoder().decode(authorization[1]));
        log.debug("decAuthorization : {}", decAuthorization);

        String[] extractUserPass = decAuthorization.split(":");
        log.debug("user[0] : {}, pass[1] : {}", extractUserPass[0], extractUserPass[1]);

        String tempuser = extractUserPass[0];
        String temppassword = extractUserPass[1];

        User user = userRepository.findUserByUsernameAndPassword(tempuser, temppassword);
        if (user == null)
            throw new CustomException(WalletConstants.EXCEPTION_SOURCE_ID_WALLET_CHANNEL,
                    "InvalidUsernameOrPasswordException");

        if (!user.getUserType().equals(WalletConstants.USER_TYPE_BANK_OPERATOR))
            throw new CustomException(WalletConstants.EXCEPTION_SOURCE_ID_WALLET_CHANNEL, "SecurityViolationException");

        map.put(WalletConstants.USER_OBJECT, user);

        log.info("logged in, {} ", user.toString());
    }
}
