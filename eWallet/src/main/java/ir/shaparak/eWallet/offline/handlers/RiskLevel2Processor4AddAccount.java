package ir.shaparak.eWallet.offline.handlers;

import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.IHandler;
import ir.shaparak.eWallet.model.PersonalWallet;
import ir.shaparak.eWallet.model.PersonalWalletAccount;
import ir.shaparak.eWallet.model.User;
import ir.shaparak.eWallet.model.WalletRequest;
import ir.shaparak.eWallet.repository.PersonalWalletRepository;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RiskLevel2Processor4AddAccount extends IHandler {

    @Setter
    PersonalWalletRepository personalWalletRepository;

    @Setter
    private IHandler callSiam;

    @Setter
    private IHandler callMana;

    @Override
    public void process(Map map) throws Exception {
        String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
        log.debug("-- refNo: {} ", refNo);

        WalletRequest walletRequest = (WalletRequest) map.get(WalletConstants.REQUEST_OBJECT);
        User user = (User) map.get(WalletConstants.USER_OBJECT);
        PersonalWallet personalWallet = (PersonalWallet) map.get(WalletConstants.PERSONAL_WALLET_OBJECT);
        String walletId = (String) map.get(WalletConstants.REQUEST_WALLET_ID);

        Integer userId = user.getId();
        String nationalId = personalWallet.getNationalId();
        Integer nationalType = personalWallet.getNationalType();

        PersonalWalletAccount account;
        if (walletRequest.getAccountType().equalsIgnoreCase(WalletConstants.ACCOUNT_TYPE_IBAN_STRING)) {
            String iban = walletRequest.getIbans();
            Map siamMap = new HashMap();
            siamMap.put(WalletConstants.WALLET_IBAN, walletRequest.getIbans());
            //todo put more fields to call siam
            callSiam.process(siamMap);
            account = getNewPersonalWalletAccountObject(walletId, iban, WalletConstants.ACCOUNT_TYPE_IBAN, nationalId, nationalType, userId);
        } else {
            String pan = walletRequest.getPans();

            Map manaMap = new HashMap();
            manaMap.put(WalletConstants.WALLET_PAN, pan);
            //todo put more fields to call mana
            callMana.process(manaMap);
            account = getNewPersonalWalletAccountObject(walletId, pan, WalletConstants.ACCOUNT_TYPE_PAN, nationalId, nationalType, userId);
        }

        map.put(WalletConstants.PERSONAL_WALLET_ACCOUNT_OBJECT, account);
    }

    private PersonalWalletAccount getNewPersonalWalletAccountObject(String walletId, String account, Integer accountType, String nationalId, Integer nationalType, Integer userId) {
        return PersonalWalletAccount.builder()
                .walletId(walletId)
                .accountId(account)
                .accountType(accountType)
                .nationalId(nationalId)
                .nationalType(nationalType)
                .status(WalletConstants.WALLET_ACCOUNT_STATUS_ACTIVE)
                .userId(userId)
                .createDate(new Date())
                .lastEditDate(new Timestamp(System.currentTimeMillis()))
                .build();

    }
}

