package com.ssafy.letcipe.api.service;

import com.ssafy.letcipe.api.dto.recipe.ResGetRecipeDto;
import com.ssafy.letcipe.api.dto.report.JPQLCartReportDto;
import com.ssafy.letcipe.api.dto.report.ResGetCartReport;
import com.ssafy.letcipe.domain.report.ApiReport;
import com.ssafy.letcipe.domain.report.ApiReportRepository;
import com.ssafy.letcipe.domain.report.CartReport;
import com.ssafy.letcipe.domain.report.CartReportRepository;
import com.ssafy.letcipe.domain.user.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final CartReportRepository cartReportRepository;
    private final RecipeService recipeService;



    @Transactional
    public List<ResGetCartReport> getCartReport(String attributes, LocalDate beginDate, LocalDate endDate, Pageable pageable) {
        List<ResGetCartReport> reports = new ArrayList<>();

        List<JPQLCartReportDto> cartReports = cartReportRepository.findAllByAttr(attributes, beginDate, endDate, pageable);
        cartReports.sort(Comparator.comparing(JPQLCartReportDto::getCount).reversed());

        for (JPQLCartReportDto cartReport : cartReports) {
            ResGetRecipeDto recipeDto = recipeService.getRecipeDto(cartReport.getRecipe());
            reports.add(ResGetCartReport.builder()
                            .recipe(recipeDto)
                            .count(cartReport.getCount())
                    .build());
        }

        return reports;
    }
}
