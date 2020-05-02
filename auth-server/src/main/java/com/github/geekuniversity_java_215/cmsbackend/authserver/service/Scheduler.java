package com.github.geekuniversity_java_215.cmsbackend.authserver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

@Service
public class Scheduler {

    private final static Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final TokenService tokenService;
    private final BlacklistTokenService blacklistTokenService;

    @Autowired
    public Scheduler(TokenService tokenService, BlacklistTokenService blacklistTokenService) {
        this.tokenService = tokenService;
        this.blacklistTokenService = blacklistTokenService;
    }


    // every hour -  пылесосить таблицы - убирать протухшие токены
    @Scheduled(cron = "0 0 * * * *")
    private void vacuumTables() {

        log.info("Vacuuming tables ...");

        tokenService.vacuum();
        blacklistTokenService.vacuum();

        log.info("Vacuuming tables done");
    }

}
