package com.ssafy.letcipe.api.service;

import com.ssafy.letcipe.api.dto.recipe.ResGetRecipeDto;
import com.ssafy.letcipe.api.dto.report.ResGetCartReport;
import com.ssafy.letcipe.domain.report.ApiReport;
import com.ssafy.letcipe.domain.report.ApiReportRepository;
import com.ssafy.letcipe.domain.report.CartReport;
import com.ssafy.letcipe.domain.report.CartReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ApiReportRepository apiReportRepository;
    private final CartReportRepository cartReportRepository;
    private final RecipeService recipeService;

    @Transactional
    public List<ApiReport> getApiReport(LocalDate beginDate, LocalDate endDate) {
        List<ApiReport> apiReports = apiReportRepository.rangeSearch(beginDate, endDate);
        apiReports.sort(Comparator.comparing(ApiReport::getCount).reversed());
        return apiReports;
    }

    @Transactional
    public List<ResGetCartReport> getCartReport(String attributes, LocalDate beginDate, LocalDate endDate) {
        List<ResGetCartReport> reports = new ArrayList<>();

        List<CartReport> cartReports = cartReportRepository.findAllByAttr(attributes, beginDate, endDate);
        cartReports.sort(Comparator.comparing(CartReport::getCount).reversed());

        for (CartReport cartReport : cartReports) {
            ResGetRecipeDto recipeDto = recipeService.getRecipeDto(cartReport.getRecipe());
            reports.add(ResGetCartReport.builder()
                            .recipe(recipeDto)
                            .date(cartReport.getDate())
                            .count(cartReport.getCount())
                    .build());
        }
        return reports;
    }
}
