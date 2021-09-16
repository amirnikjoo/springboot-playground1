package ir.shaparak.eWallet.offline.trigger;

import ir.shaparak.eWallet.core.IHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class PersonalWalletRegisterCronTriggerJob {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final IHandler fetchRegisterRequests;

    @Autowired
    public PersonalWalletRegisterCronTriggerJob(@Qualifier("fetchRegisterRequests") IHandler fetchRegisterRequests) {
        this.fetchRegisterRequests = fetchRegisterRequests;
    }

    //    @Scheduled(fixedRate = 60000)
//    @Scheduled(fixedRate = 60000, initialDelay = 1000)
    @Scheduled(cron = "${job1.personal.wallet.register}")
    public void run() {
        try {
            fetchRegisterRequests.process(new HashMap());
        } catch (Exception e) {
            log.error("main register flow {}", e.getMessage());
        }
        log.info("finish job");
    }
}
