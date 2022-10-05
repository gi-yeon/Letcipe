package com.ssafy.letcipe.api.schedule;

import com.ssafy.letcipe.api.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Component
@Log4j2
@RequiredArgsConstructor
public class ScheduleTask {
    private static final DateTimeFormatter formatter
            = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final AdminService adminService;

    @Scheduled(cron = "0 10 0 * * ?")
    public void cronExpression() {
        log.info("분석 결과 저장 - {}", formatter.format(LocalDateTime.now()));
        String date = formatter.format(LocalDateTime.now());
        try {
            adminService.saveCartReport(date);
        } catch (IOException e) {
            log.error("장바구니 분석 결과 db저장 에러:{}",e.getMessage());
        }
        try {
            adminService.saveApiReport(date);
        } catch (IOException e) {
            log.error("API 분석 결과 db저장 에러:{}",e.getMessage());

        }
    }
}
