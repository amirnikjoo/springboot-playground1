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
public class InactivePersonalWalletAccount4Remove extends IHandler {
    @Setter
    private PersonalWalletAccountRepository personalWalletAccountRepository;

    @Override
    public void process(Map map) throws Exception {
        WalletRequest walletRequest = (WalletRequest) map.get(WalletConstants.REQUEST_OBJECT);
        Integer accType = (Integer) WalletConstants.ACCOUNT_TYPE_MAP.get(walletRequest.getAccountType());

        String accountId = "";
        if (walletRequest.getAccountType().equalsIgnoreCase(WalletConstants.ACCOUNT_TYPE_IBAN_STRING))
            accountId = walletRequest.getIbans();
        else
            accountId = walletRequest.getPans();

        personalWalletAccountRepository.updatePersonalWalletAccountByWalletIdAndAccountIdAndAccountType(
                WalletConstants.WALLET_ACCOUNT_STATUS_DISABLED, new Timestamp(System.currentTimeMillis()),
                walletRequest.getWalletId(), accountId, accType);
        String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
        log.debug("account successfully disabled -- refNo: {} ", refNo);
    }
}
