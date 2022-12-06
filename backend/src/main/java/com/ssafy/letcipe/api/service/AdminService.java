package com.ssafy.letcipe.api.service;

import com.ssafy.letcipe.api.dto.report.JPQLApiReportDto;
import com.ssafy.letcipe.domain.recipe.Recipe;
import com.ssafy.letcipe.domain.report.ApiReport;
import com.ssafy.letcipe.domain.report.ApiReportRepository;
import com.ssafy.letcipe.domain.report.CartReport;
import com.ssafy.letcipe.domain.report.CartReportRepository;
import com.ssafy.letcipe.util.FileHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import java.io.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final FileHandler fileHandler;
    private final CartReportRepository cartReportRepository;
    private final ApiReportRepository apiReportRepository;
    private final RecipeService recipeService;
    private final String CART_RESULT_PATH = "./var/letcipe/cart_result";

    private final String API_RESULT_PATH = "./var/letcipe/api_result";

    @Transactional
    public void saveCartReport(String fileName) throws IOException {
        // 분석결과 파일 읽기
        File result = fileHandler.getFile(CART_RESULT_PATH, fileName);
        if (!result.exists())
            throw new FileNotFoundException("장바구니 분석 결과 파일이 없습니다.");

        BufferedReader br = new BufferedReader(new FileReader(result));
        String str;
        // 한 줄씩 읽어서 db에 저장
        while ((str = br.readLine()) != null) {
            // 결과 파싱
            String[] tokens = str.split("\t");
            String[] recipeCounts = tokens[1].split(",");
            // 레시피 찾기
            for (String recipeCount : recipeCounts) {
                String[] recipeTokens = recipeCount.split(":");
                Recipe recipe;
                try {
                    recipe = recipeService.getRecipe(Long.parseLong(recipeTokens[0]));
                } catch (NullPointerException e) {
                    continue;
                }

                CartReport cartReport = CartReport.builder()
                        .attributes(tokens[0])
                        .recipe(recipe)
                        .date(LocalDate.parse(fileName.substring(0, fileName.indexOf(".txt"))))
                        .count(Integer.parseInt(recipeTokens[1]))
                        .build();

                cartReportRepository.save(cartReport);
            }
        }
    }

    @Transactional
    public void saveApiReport(String fileName) throws IOException, DataIntegrityViolationException {
        // 분석결과 파일 읽기
        File result = fileHandler.getFile(API_RESULT_PATH, fileName);
        if (!result.exists())
            throw new FileNotFoundException("API 분석 결과 파일이 없습니다.");

        BufferedReader br = new BufferedReader(new FileReader(result));
        String str;
        // 한 줄씩 읽어서 db에 저장
        while ((str = br.readLine()) != null) {
            // 결과 파싱
            String[] tokens = str.split("\t");
            ApiReport apiReport = ApiReport.builder()
                    .methodName(tokens[0])
                    .count(Integer.parseInt(tokens[1]))
                    .date(LocalDate.parse(fileName.substring(0, fileName.indexOf(".txt"))))
                    .build();

            apiReportRepository.save(apiReport);
        }
    }

    @Transactional
    public List<JPQLApiReportDto> getApiReport(LocalDate beginDate, LocalDate endDate) {
        List<JPQLApiReportDto> apiReports = apiReportRepository.rangeSearch(beginDate, endDate);
        apiReports.sort(Comparator.comparing(JPQLApiReportDto::getCount).reversed());
        return apiReports;
    }
}
