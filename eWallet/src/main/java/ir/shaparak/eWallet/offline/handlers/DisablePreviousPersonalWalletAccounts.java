package ir.shaparak.eWallet.offline.handlers;

import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.IHandler;
import ir.shaparak.eWallet.model.WalletRequest;
import ir.shaparak.eWallet.repository.PersonalWalletAccountRepository;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Map;

@Transactional
public class DisablePreviousPersonalWalletAccounts extends IHandler {
    @Setter
    private PersonalWalletAccountRepository personalWalletAccountRepository;

    @Override
    public void process(Map map) throws Exception {
        String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
        WalletRequest walletRequest = (WalletRequest) map.get(WalletConstants.REQUEST_OBJECT);

        Integer rows = personalWalletAccountRepository.updatePersonalWalletAccountByWalletId(WalletConstants.WALLET_ACCOUNT_STATUS_DISABLED, new Timestamp(System.currentTimeMillis()), walletRequest.getWalletId());

        log.debug("personalWalletAccount update {} rows successfully -- refNo: {} ", rows, refNo);
    }
}
