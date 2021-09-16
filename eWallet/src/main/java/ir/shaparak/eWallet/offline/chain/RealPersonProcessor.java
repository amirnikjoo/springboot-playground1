package ir.shaparak.eWallet.offline.chain;

import ir.shaparak.eWallet.core.IHandler;

import java.util.Map;

public class RealPersonProcessor extends IHandler {
    @Override
    public void process(Map map) throws Exception {
        processChainHandler(map);
    }
}
