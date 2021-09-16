package ir.shaparak.eWallet.offline.handlers;

import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.IHandler;
import ir.shaparak.eWallet.model.WalletRequest;
import ir.shaparak.eWallet.repository.WalletRequestRepository;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Map;

@Transactional
public class SetWalletRequestStep extends IHandler {
    @Setter
    private Integer step;

    @Setter
    private WalletRequestRepository walletRequestRepository;

    @Override
    public void process(Map map) throws Exception {
        String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
        log.debug("-- refNo: {}, step = {} ", refNo, step);

        WalletRequest walletRequest = (WalletRequest) map.get(WalletConstants.REQUEST_OBJECT);

        walletRequest.setStep(step);
        walletRequestRepository.incrementWalletRequestStepById(walletRequest.getStep(), new Timestamp(System.currentTimeMillis()), walletRequest.getId());
    }
}
