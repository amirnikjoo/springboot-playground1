package ir.shaparak.eWallet.offline.runner;

import ir.shaparak.eWallet.api.dto.ResponseCodeDto;
import ir.shaparak.eWallet.constants.ResponseConstants;
import ir.shaparak.eWallet.constants.WalletConstants;
import ir.shaparak.eWallet.core.CustomException;
import ir.shaparak.eWallet.core.IHandler;
import ir.shaparak.eWallet.model.WalletRequest;
import ir.shaparak.eWallet.repository.WalletRequestRepository;
import ir.shaparak.eWallet.util.MyUtility;
import lombok.Setter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FetchAddAccountRequests extends IHandler {
    @Setter
    private WalletRequestRepository walletRequestRepository;

    @Setter
    private ThreadPoolTaskExecutor taskExecutor;

    @Setter
    private IHandler offlineAddAccountFlow;

    @Override
    public void process(Map map) throws Exception {

        if (this.taskExecutor.getActiveCount() > 0) {
            StringBuffer buffer = new StringBuffer();
            buffer.append(" Active: ").append(this.taskExecutor.getActiveCount()).
                    append(", Queue: ").append(this.taskExecutor.getThreadPoolExecutor().getQueue().size()).
                    append(", Current: ").append(this.taskExecutor.getPoolSize()).
                    append(", Completed: ").append(this.taskExecutor.getThreadPoolExecutor().getCompletedTaskCount());
            log.warn(" job skipped... " + buffer);
            return;
        }

        List<WalletRequest> requests = walletRequestRepository.getWalletRequestByHandledAndServiceId(
                WalletConstants.HANDLE, WalletConstants.SERVICE_ID_ADD_ACCOUNT);
        if (requests.size() > 0)
            log.info("record count: {} ", requests.size());

        for (WalletRequest request : requests) {
            log.info("record requestId: {} ", request.getId());
            Map reqMap = new HashMap();
            String refNo = MyUtility.generateRefNo();
            reqMap.put(WalletConstants.REFERENCE_NUMBER, refNo);
            reqMap.put(WalletConstants.REQUEST_OBJECT, request);
            reqMap.put(WalletConstants.REQUEST_WALLET_ID, request.getWalletId());
            taskExecutor.execute(new MySession(reqMap));
        }
    }

    private class MySession implements Runnable {
        Map map;

        private MySession(Map map) {
            this.map = map;
        }

        public void run() {
            String refNo = (String) map.get(WalletConstants.REFERENCE_NUMBER);
            WalletRequest walletRequest = (WalletRequest) map.get(WalletConstants.REQUEST_OBJECT);

            log.debug("threadName = {}, refNo = {}, originalRefNo = {}", Thread.currentThread().getName(), refNo, walletRequest.getReferenceNumber());
            try {
                offlineAddAccountFlow.process(map);
                handledFine(walletRequest);
            } catch (CustomException ce) {
                log.error("{}, {}", ce.getMessage(), ce.getSourceId());
                handledWithError(walletRequest, ce.getMessage(), ce.getSourceId());
            } catch (Exception e) {
                log.error("{}", e.getMessage());
                handledWithError(walletRequest, e.getMessage(), WalletConstants.EXCEPTION_SOURCE_ID_UNKNOWN);
            }
        }

        @Transactional
        private void handledWithError(WalletRequest walletRequest, String message, Integer sourceId) {
            Integer tryCount = walletRequest.getTryCount() + 1;
            Integer handle = WalletConstants.HANDLE;
            if (tryCount >= WalletConstants.MAX_TRY_COUNT)
                handle = WalletConstants.HANDLED_WITH_ERROR;

            ResponseCodeDto responseCodeDto = (ResponseCodeDto) ResponseConstants.responseMap.get(message);
            String actionCodes = walletRequest.getActionCodes().trim()
                    + sourceId + WalletConstants.SEPARATOR_COMMA + responseCodeDto.getResponseCode() + WalletConstants.SEPARATOR_PIPE;

            walletRequestRepository.changeWalletRequestByIdV2(
                    handle, tryCount, actionCodes,
                    Integer.valueOf(responseCodeDto.getResponseCode()), sourceId,
                    new Timestamp(System.currentTimeMillis()),
                    walletRequest.getId());
        }

        private void handledFine(WalletRequest walletRequest) {
            String actionCodes = walletRequest.getActionCodes().trim()
                    + WalletConstants.EXCEPTION_SOURCE_ID_NO_EXCEPTION
                    + WalletConstants.SEPARATOR_COMMA + ResponseConstants.APPROVED_RESPONSE_CODE + WalletConstants.SEPARATOR_PIPE;

            walletRequestRepository.changeWalletRequestByIdV2(
                    WalletConstants.HANDLED_FINE, walletRequest.getTryCount() + 1, actionCodes,
                    ResponseConstants.APPROVED_RESPONSE_CODE_INT, WalletConstants.EXCEPTION_SOURCE_ID_NO_EXCEPTION,
                    new Timestamp(System.currentTimeMillis()),
                    walletRequest.getId());
        }
    }

}
