package ir.shaparak.eWallet.core;

import ir.shaparak.eWallet.constants.WalletConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


public abstract class IHandler {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected IHandler[] handlers;

    protected IHandler[] exceptionHandler;

    protected IHandler() {
        super();
    }


    public abstract void process(Map map) throws Exception;

    public void setHandlers(List handlerList) {
        Iterator iterator = handlerList.iterator();
        handlers = new IHandler[handlerList.size()];
        for (int i = 0; iterator.hasNext(); i++) {
            IHandler iHandler = (IHandler) iterator.next();
            handlers[i] = iHandler;
        }
    }

    public void setExceptionHandler(List newExceptionHandler) {
        Iterator iterator = newExceptionHandler.iterator();
        exceptionHandler = new IHandler[newExceptionHandler.size()];

        for (int i = 0; iterator.hasNext(); i++) {
            IHandler cmHandlerBase = (IHandler) iterator.next();
            exceptionHandler[i] = cmHandlerBase;
        }
    }

    public void processChainHandler(Map map) throws Exception {
        int i = 0;
        try {
            for (; i < handlers.length; i++)
                handlers[i].process(map);
        } catch (Exception e) {
            String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
            log.error("refNo = {}, {}, in {}", refNo, e, handlers[i].getClass());
            log.info("refNo = {}, map = {}", refNo, map);

            map.put(WalletConstants.EXCEPTION_CLASS_OBJECT, e);
            log.error("class: {}, {}", e.getClass(), e.getMessage());
            if (e instanceof CustomException) {
                Integer sourceId = ((CustomException) e).getSourceId();
                map.put(WalletConstants.EXCEPTION_SOURCE_ID, sourceId);
                log.error("sourceId: {}", sourceId);
            } else {
                map.put(WalletConstants.EXCEPTION_SOURCE_ID, -1);
            }
            map.put(WalletConstants.EXCEPTION_CLASS, e.getClass());
            if (exceptionHandler != null && exceptionHandler.length > 0) {
                try {
                    for (int k = 0; k < exceptionHandler.length; k++)
                        exceptionHandler[k].process(map);
                } catch (Exception e1) {
                    log.error("refNo = {} , exception occurred in exception handler, e = {}", refNo, e1);
                    throw e1;
                }
                throw (Exception) map.get(WalletConstants.EXCEPTION_CLASS_OBJECT);
            } else
                throw (Exception) map.get(WalletConstants.EXCEPTION_CLASS_OBJECT);
        }
    }
}
