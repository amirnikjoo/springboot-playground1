package ir.shaparak.eWallet.offline.handlers;

import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.IHandler;
import ir.shaparak.eWallet.model.User;
import ir.shaparak.eWallet.model.WalletRequest;
import ir.shaparak.eWallet.util.MyUtility;
import ir.shaparak.eWallet.util.StringUtility;

import java.util.Map;

public class GenerateWalletId extends IHandler {

    @Override
    public void process(Map map) throws Exception {
        String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
        log.debug("-- refNo: {} ", refNo);

        WalletRequest walletRequest = (WalletRequest) map.get(WalletConstants.REQUEST_OBJECT);

        User user = (User) map.get(WalletConstants.USER_OBJECT);

        // 6 digit instituteId + 3 digit providerId + 2 digit shaparak Random + 1 digit walletType + 12 digit walletSerial + 2 digit parity
        String shaparakRandom = StringUtility.pad(MyUtility.getRandomIntInRange(0, 99).toString(), 2, "0", StringUtility.PadPosition.Behind);
        log.debug("shaparakRandom = {} ", shaparakRandom);

        String generatedWallet = user.getBankIin() + user.getProvider()
                + shaparakRandom
                + WalletConstants.WALLET_TYPE_PERSONAL + walletRequest.getWalletId();
        log.debug(" generatedWalletId = {} , {}", generatedWallet, refNo);

        String generatedWalletWithCheckSum = MyUtility.LUHN.generate(MyUtility.LUHN.generate(generatedWallet));
        log.debug(" generatedWalletWithCheckSum = {} , len = {}", generatedWalletWithCheckSum, generatedWalletWithCheckSum.length());

        map.put(WalletConstants.GENERATED_WALLET_ID, generatedWalletWithCheckSum);
    }
}
