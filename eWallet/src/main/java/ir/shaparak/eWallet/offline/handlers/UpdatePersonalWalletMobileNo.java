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
public class UpdatePersonalWalletMobileNo extends IHandler {
    @Setter
    PersonalWalletRepository personalWalletRepository;

    @Override
    public void process(Map map) throws Exception {
        String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
        log.debug("-- refNo: {} ", refNo);
        WalletRequest walletRequest = (WalletRequest) map.get(WalletConstants.REQUEST_OBJECT);

        String newMobileNo = walletRequest.getMobileNumber();
        Integer count = personalWalletRepository.updateMobileNoById(newMobileNo, new Timestamp(System.currentTimeMillis()), walletRequest.getWalletId());

        log.debug("-- refNo: {}, walletId: {}, newMobileNo: {}, rows update: {}", refNo, walletRequest.getWalletId(), newMobileNo, count);

    }
}
