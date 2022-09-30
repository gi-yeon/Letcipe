package com.ssafy.letcipe.api.controller;

import com.ssafy.letcipe.api.dto.report.ReqGetApiReport;
import com.ssafy.letcipe.api.dto.report.ReqGetCartReport;
import com.ssafy.letcipe.api.dto.report.ReqPostReportDto;
import com.ssafy.letcipe.api.service.AdminService;
import com.ssafy.letcipe.domain.report.ApiReport;
import com.ssafy.letcipe.domain.report.CartReport;
import com.ssafy.letcipe.util.FileHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@Log4j2
public class AdminController {
    private final FileHandler fileHandler;
    private final AdminService adminService;

    /**
     * 하둡 카트 분석 결과를 db에 저장
     * @param reqDto 분석 날짜
     * @return 200 OK 저장 성공시 응답
     */
    @PostMapping("/report/cart")
    public ResponseEntity postCartReport(@RequestBody ReqPostReportDto reqDto) {
        try {
            adminService.saveCartReport(reqDto.getDate()+".txt");
        } catch (IOException e) {
            log.warn("{}",e.getMessage());
            return ResponseEntity.internalServerError().body("분석 파일이 없습니다");
        }catch (DataIntegrityViolationException e) {
            log.warn("이미 저장된 값이 있습니다.:{}",e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    /**
     * 하둡 api 호출 분석 결과를 db에 저장
     * @param reqDto 분석 날짜
     * @return
     */
    @PostMapping("/report/api")
    public ResponseEntity postApiReport(@RequestBody ReqPostReportDto reqDto) {
        try {
            adminService.saveApiReport(reqDto.getDate()+".txt");
        } catch (IOException e) {
            log.warn("{}",e.getMessage());
            return ResponseEntity.internalServerError().body("분석 파일이 없습니다");
        }catch (DataIntegrityViolationException e) {
            log.warn("이미 저장된 값이 있습니다.:{}",e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/report/api")
    public ResponseEntity getApiReport(@RequestBody ReqGetApiReport reqDto) {
        List<ApiReport> apiReport = adminService.getApiReport(LocalDate.parse(reqDto.getBeginDate()),LocalDate.parse(reqDto.getEndDate()));
        return ResponseEntity.ok(apiReport);
    }

    @GetMapping("/report/cart")
    public ResponseEntity getCartReport(@RequestBody ReqGetCartReport reqDto) {
        List<CartReport> cartReport = adminService.getCartReport(reqDto.getAttributes(), LocalDate.parse(reqDto.getBeginDate()), LocalDate.parse(reqDto.getEndDate()));
    return ResponseEntity.ok(cartReport);
    }


}
