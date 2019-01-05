package com.afagoal.hamster.service.newYearFlag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Created by BaoCai on 2019/1/5. Description:
 */
@Service
public class CacheTask {

    @Autowired
    private UserFlagService userFlagService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void flagCacheTask() {
        userFlagService.cacheFlags();
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void goodSayingCacheTask() {
        userFlagService.cacheGoodSaying();
    }

    @Scheduled(cron = "* */15 * * * ?")
    public void userRecordCacheTask() {
        userFlagService.cacheUserRecode();
    }
}
