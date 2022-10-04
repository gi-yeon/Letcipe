package com.ssafy.letcipe.api.controller;

import com.ssafy.letcipe.api.dto.admin.ResGetRecipeChartReport;
import com.ssafy.letcipe.api.dto.report.*;
import com.ssafy.letcipe.api.service.AdminService;
import com.ssafy.letcipe.api.service.JwtService;
import com.ssafy.letcipe.api.service.ReportService;
import com.ssafy.letcipe.domain.report.ApiReport;
import com.ssafy.letcipe.domain.report.CartReport;
import com.ssafy.letcipe.util.FileHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.bytebuddy.asm.Advice;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@Log4j2
public class AdminController {
    private final AdminService adminService;
    private final JwtService jwtService;
    private final ReportService reportService;

    /**
     * 하둡 카트 분석 결과를 db에 저장
     *
     * @param reqDto 분석 날짜
     * @return 200 OK 저장 성공시 응답
     */
    @PostMapping("/report/cart")
    public ResponseEntity postCartReport(@RequestBody ReqPostReportDto reqDto, HttpServletRequest request) {
        if (!jwtService.isAdmin(request)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        try {
            adminService.saveCartReport(reqDto.getDate() + ".txt");
        } catch (IOException e) {
            log.warn("{}", e.getMessage());
            return ResponseEntity.internalServerError().body("분석 파일이 없습니다");
        } catch (DataIntegrityViolationException e) {
            log.warn("이미 저장된 값이 있습니다.:{}", e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    /**
     * 하둡 api 호출 분석 결과를 db에 저장
     *
     * @param reqDto 분석 날짜
     * @return
     */
    @PostMapping("/report/api")
    public ResponseEntity postApiReport(@RequestBody ReqPostReportDto reqDto, HttpServletRequest request) {
        if (!jwtService.isAdmin(request)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        try {
            adminService.saveApiReport(reqDto.getDate() + ".txt");
        } catch (IOException e) {
            log.warn("{}", e.getMessage());
            return ResponseEntity.internalServerError().body("분석 파일이 없습니다");
        } catch (DataIntegrityViolationException e) {
            log.warn("이미 저장된 값이 있습니다.:{}", e.getMessage());
        }
        return ResponseEntity.ok().build();
    }


    @GetMapping("/report/api")
    public ResponseEntity getApiReport(@RequestBody ReqGetApiReport reqDto, HttpServletRequest request) {
        if (!jwtService.isAdmin(request)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        List<JPQLApiReportDto> apiReport = adminService.getApiReport(LocalDate.parse(reqDto.getBeginDate()), LocalDate.parse(reqDto.getEndDate()));
        return ResponseEntity.ok(apiReport);
    }

    @GetMapping("/recipe")
    public ResponseEntity getRecipe(
            @RequestParam String reqDto,
            @RequestParam String begin,
            @RequestParam String end,
            Pageable pageable,
            HttpServletRequest request) {
        //if (!jwtService.isAdmin(request)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        LocalDate sdate=LocalDate.parse(begin);
        LocalDate edate=LocalDate.parse(end);
        System.out.println(reqDto);
        System.out.println(sdate);
        System.out.println(edate);
        System.out.println(pageable);
        System.out.println(pageable.toString());
        ResGetRecipeChartReport response = reportService. getRecipeChartReport(
                reqDto,
                sdate,
                edate,
                pageable
        );
        System.out.println(response.toString());
        return ResponseEntity.ok(response);
    }
}
