package ir.shaparak.eWallet.offline.handlers;

import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.IHandler;
import ir.shaparak.eWallet.model.PersonalWalletAccount;
import ir.shaparak.eWallet.repository.PersonalWalletAccountRepository;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
public class PrepareAndInsertPersonalWalletAccount extends IHandler {
    @Setter
    private PersonalWalletAccountRepository personalWalletAccountRepository;

    @Override
    public void process(Map map) throws Exception {
        List<PersonalWalletAccount> accounts = (List<PersonalWalletAccount>) map.get(WalletConstants.PERSONAL_WALLET_ACCOUNT_OBJECT_LIST);
        if (accounts != null && !accounts.isEmpty()) {
            String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
            String walletId = (String) map.get(WalletConstants.GENERATED_WALLET_ID);
            log.debug(" generatedWalletId = {} , {}", walletId, refNo);

            for (PersonalWalletAccount account : accounts)
                account.setWalletId(walletId);

            personalWalletAccountRepository.saveAll(accounts);

            log.debug("personalWalletAccount insert {} rows successfully -- refNo: {} ", accounts.size(), refNo);
        }
    }

}
