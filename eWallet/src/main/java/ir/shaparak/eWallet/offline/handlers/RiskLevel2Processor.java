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
import java.util.*;

public class RiskLevel2Processor extends IHandler {

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

        String walletId = "";
        Integer userId = user.getId();
        String nationalId = walletRequest.getNationalId();
        Integer nationalType = walletRequest.getNationalType();
        if (!walletRequest.getServiceId().equals(WalletConstants.SERVICE_ID_REGISTER)) {
            walletId = walletRequest.getWalletId();
            PersonalWallet personalWallet = personalWalletRepository.findByUserIdAndWalletId(userId, walletRequest.getWalletId());
            nationalId = personalWallet.getNationalId();
            nationalType = personalWallet.getNationalType();
        }

        List<PersonalWalletAccount> accounts = new ArrayList<>();
        if (walletRequest.getIbans() != null && walletRequest.getIbans().trim().length() > 0) {
            String ibans[] = walletRequest.getIbans().split(WalletConstants.SPLIT_PIPE);
            for (int i = 0; i < ibans.length; i++) {
                log.debug("-- refNo: {}, iban[{}] = {}", refNo, i, ibans[i]);
                Map siamMap = new HashMap();
                siamMap.put(WalletConstants.WALLET_IBAN, ibans[i]);
                //todo put more fields to call siam
                callSiam.process(siamMap);
                accounts.add(getNewPersonalWalletAccountObject(walletId, ibans[i], WalletConstants.ACCOUNT_TYPE_IBAN, nationalId, nationalType, userId));
            }
        }

        if (walletRequest.getPans() != null && walletRequest.getPans().trim().length() > 0) {
            String pans[] = walletRequest.getPans().split(WalletConstants.SPLIT_PIPE);
            for (int i = 0; i < pans.length; i++) {
                log.debug("-- refNo: {}, pan[{}] = {}", refNo, i, pans[i]);
                Map manaMap = new HashMap();
                manaMap.put(WalletConstants.WALLET_PAN, pans[i]);
                //todo put more fields to call mana
                callMana.process(manaMap);
                accounts.add(getNewPersonalWalletAccountObject(walletId, pans[i], WalletConstants.ACCOUNT_TYPE_PAN, nationalId, nationalType, userId));
            }
        }

        if (!accounts.isEmpty())
            map.put(WalletConstants.PERSONAL_WALLET_ACCOUNT_OBJECT_LIST, accounts);
    }

    private PersonalWalletAccount getNewPersonalWalletAccountObjectWithoutWalletId(String account, Integer accountType, String nationalId, Integer nationalType, Integer userId) {
        return PersonalWalletAccount.builder()
//                .walletId(walletId) set after generation
                .accountId(account)
                .accountType(accountType)
                .nationalId(nationalId)
                .nationalType(nationalType)
                .status(WalletConstants.ZERO)
                .userId(userId)
                .createDate(new Date())
                .lastEditDate(new Timestamp(System.currentTimeMillis()))
                .build();
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
