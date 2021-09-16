package ir.shaparak.eWallet.offline.dummy;

import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.CustomException;
import ir.shaparak.eWallet.core.IHandler;

import java.util.Map;

public class CallShahkarErrorDummy extends IHandler {

    @Override
    public void process(Map map) throws Exception {
        throw new CustomException(WalletConstants.EXCEPTION_SOURCE_ID_SHAHKAR, "SystemInternalError");
    }
}
