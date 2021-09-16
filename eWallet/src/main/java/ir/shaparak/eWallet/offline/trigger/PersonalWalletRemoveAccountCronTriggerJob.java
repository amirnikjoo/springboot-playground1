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
public class PersonalWalletRemoveAccountCronTriggerJob {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final IHandler fetchRemoveAccountRequests;

    @Value("${job5.active.flag}")
    private final boolean isActive = false;

    @Autowired
    public PersonalWalletRemoveAccountCronTriggerJob(@Qualifier("fetchRemoveAccountRequests") IHandler fetchRemoveAccountRequests) {
        this.fetchRemoveAccountRequests = fetchRemoveAccountRequests;
    }

    @Scheduled(cron = "${job6.personal.wallet.remove.account}")
    public void run() {
//        log.info("start job");
//        if (isActive) {
        try {
            fetchRemoveAccountRequests.process(new HashMap());
        } catch (Exception e) {
            log.error("main register flow {}", e.getMessage());
        }
        log.info("finish job");
//        }
    }
}
