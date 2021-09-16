package ir.shaparak.eWallet.offline.handlers;

import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.IHandler;
import ir.shaparak.eWallet.model.PersonalWalletAccount;
import ir.shaparak.eWallet.repository.PersonalWalletAccountRepository;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Transactional
public class InsertPersonalWalletAccount extends IHandler {
    @Setter
    private PersonalWalletAccountRepository personalWalletAccountRepository;

    @Override
    public void process(Map map) throws Exception {
        PersonalWalletAccount account = (PersonalWalletAccount) map.get(WalletConstants.PERSONAL_WALLET_ACCOUNT_OBJECT);
        personalWalletAccountRepository.save(account);
        String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
        log.debug("personalWalletAccount insert successfully -- refNo: {} ", refNo);
    }
}
