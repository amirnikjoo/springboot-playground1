package ir.shaparak.eWallet.offline.trigger;

import ir.shaparak.eWallet.core.IHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class PersonalWalletChangeStatusCronTriggerJob {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final IHandler fetchChangeStatusRequest;

    @Value("${job3.active.flag}")
    private final boolean isActive = false;

    @Autowired
    public PersonalWalletChangeStatusCronTriggerJob(@Qualifier("fetchChangeStatusRequests") IHandler fetchChangeStatusRequest) {
        this.fetchChangeStatusRequest = fetchChangeStatusRequest;
    }

    @Scheduled(cron = "${job3.personal.wallet.change.status}")
    public void run() {
//        log.info("start job");
//        if (isActive) {
        try {
            fetchChangeStatusRequest.process(new HashMap());
        } catch (Exception e) {
            log.error("main register flow {}", e.getMessage());
        }
        log.info("finish job");
//        }
    }
}
