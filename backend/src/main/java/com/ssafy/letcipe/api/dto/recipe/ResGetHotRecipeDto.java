package com.ssafy.letcipe.api.dto.recipe;

import com.ssafy.letcipe.api.dto.report.ResGetCartReport;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ResGetHotRecipeDto {
    String title;
    List<ResGetCartReport> report;

    public ResGetHotRecipeDto(String title, List<ResGetCartReport> cartReport) {
        this.title = title;
        this.report=new ArrayList<>();
        for(ResGetCartReport report:cartReport){
            this.report.add(report);
        }
    }
}
