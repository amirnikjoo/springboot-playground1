package ir.shaparak.eWallet.handlers.chain;

import ir.shaparak.eWallet.core.IHandler;

import java.util.Map;

public class ChangeStatusFlow extends IHandler {
    @Override
    public void process(Map map) throws Exception {
        processChainHandler(map);
    }
}
