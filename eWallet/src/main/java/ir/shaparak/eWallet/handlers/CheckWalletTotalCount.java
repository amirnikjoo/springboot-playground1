package ir.shaparak.eWallet.handlers;

import ir.shaparak.eWallet.api.dto.personal.WalletRegisterRequestDto;
import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.CustomException;
import ir.shaparak.eWallet.core.IHandler;
import ir.shaparak.eWallet.repository.PersonalWalletRepository;
import lombok.Setter;

import java.util.Map;

public class CheckWalletTotalCount extends IHandler {
    @Setter
    PersonalWalletRepository personalWalletRepository;

    @Override
    public void process(Map map) throws Exception {
        String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
        log.debug("-- refNo: {} ", refNo);
        WalletRegisterRequestDto walletRegisterRequestDto = (WalletRegisterRequestDto) map.get(WalletConstants.REQUEST_OBJECT);

        //todo: add walletStatus criteria to
        Integer nationalType = (Integer) WalletConstants.IDENTIFIER_TYPE_MAP.get(walletRegisterRequestDto.getPersonDto().getIdentifierType());
        Integer count = personalWalletRepository.countByNationalIdAndNationalType(walletRegisterRequestDto.getPersonDto().getIdentifier(), nationalType);
        if (count != null && count >= WalletConstants.MAX_WALLET_COUNT)
            throw new CustomException(WalletConstants.EXCEPTION_SOURCE_ID_WALLET_CHANNEL, "MaxWalletCountExceededException");

        log.debug("-- refNo: {} , count: {}", refNo, count);

    }
}
