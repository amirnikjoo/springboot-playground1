package ir.shaparak.eWallet.offline.handlers;

import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.IHandler;
import ir.shaparak.eWallet.model.PersonalWallet;
import ir.shaparak.eWallet.model.User;
import ir.shaparak.eWallet.model.WalletRequest;
import ir.shaparak.eWallet.repository.PersonalWalletRepository;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

@Transactional
public class InsertPersonalWallet extends IHandler {
    @Setter
    private PersonalWalletRepository personalWalletRepository;

    @Override
    public void process(Map map) throws Exception {
        String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
        log.debug("-- refNo: {} ", refNo);

        WalletRequest walletRequest = (WalletRequest) map.get(WalletConstants.REQUEST_OBJECT);

        User user = (User) map.get(WalletConstants.USER_OBJECT);
        String walletId = (String) map.get(WalletConstants.GENERATED_WALLET_ID);
        log.debug(" generatedWalletId = {} , {}", walletId, refNo);
        Date level2Date = null;
        if (walletRequest.getRiskLevel().equals(WalletConstants.WALLET_RISK_LEVEL_2))
            level2Date = new Date();

        PersonalWallet personalWallet = PersonalWallet.builder()
                .userId(user.getId())
                .walletId(walletId)
                .walletSerial(Long.valueOf(walletRequest.getWalletId()))
                .nationalId(walletRequest.getNationalId())
                .nationalType(walletRequest.getNationalType())
                .riskLevel(walletRequest.getRiskLevel())
                .mobileNumber(walletRequest.getMobileNumber())
                .riskLevel2_date(level2Date)
                .status(WalletConstants.WALLET_STATUS_ACTIVE)
                .shaparakStatus(WalletConstants.WALLET_STATUS_ACTIVE)
                .createDate(new Date())
                .lastEditDate(new Timestamp(System.currentTimeMillis()))
                .build();

        personalWalletRepository.save(personalWallet);
        log.debug("personalWallet insert successfully -- refNo: {} ", refNo);

    }

}
