package ir.shaparak.eWallet.offline.handlers;

import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.IHandler;
import ir.shaparak.eWallet.model.WalletRequest;
import ir.shaparak.eWallet.repository.PersonalWalletRepository;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Map;

@Transactional
public class UpdatePersonalWalletStatus extends IHandler {
    @Setter
    PersonalWalletRepository personalWalletRepository;

    @Override
    public void process(Map map) throws Exception {
        String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
        log.debug("-- refNo: {} ", refNo);
        WalletRequest walletRequest = (WalletRequest) map.get(WalletConstants.REQUEST_OBJECT);

        Integer newStatus = (Integer) WalletConstants.WALLET_STATUS_TO_WEIGHT_MAP.get(walletRequest.getWalletStatus());
        Integer count = personalWalletRepository.updateStatusById(newStatus, new Timestamp(System.currentTimeMillis()), walletRequest.getWalletId());

        log.debug("-- refNo: {}, walletId: {}, newStatus: {}, rows update: {}", refNo, walletRequest.getWalletId(), newStatus, count);

    }
}
