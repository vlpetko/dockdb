package ru.vlpetko.dockdb.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduledService {
    @Scheduled(cron = "0 */5 * * * *")
    public void getTimeEndRequests(){
        log.info("api is running");
    }

}
