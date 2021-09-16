package ir.shaparak.eWallet.offline.handlers;

import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.CustomException;
import ir.shaparak.eWallet.core.IHandler;
import ir.shaparak.eWallet.model.WalletRequest;
import ir.shaparak.eWallet.repository.PersonalWalletRepository;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

@Transactional
public class UpdateRiskLevelInPersonalWallet extends IHandler {
    @Setter
    PersonalWalletRepository personalWalletRepository;

    @Override
    public void process(Map map) throws Exception {
        String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
        log.debug("-- refNo: {} ", refNo);
        WalletRequest walletRequest = (WalletRequest) map.get(WalletConstants.REQUEST_OBJECT);

        Date level2Date = null;
        if (walletRequest.getRiskLevel().equals(WalletConstants.WALLET_RISK_LEVEL_2))
            level2Date = new Date();

        Integer count = personalWalletRepository.updateRiskLevelById(walletRequest.getRiskLevel(), level2Date, new Timestamp(System.currentTimeMillis()), walletRequest.getWalletId());
        if (count != null && count >= WalletConstants.MAX_WALLET_COUNT)
            throw new CustomException(WalletConstants.EXCEPTION_SOURCE_ID_WALLET_CHANNEL, "MaxWalletCountExceededException");

        log.debug("-- refNo: {} , count: {}", refNo, count);

    }
}
