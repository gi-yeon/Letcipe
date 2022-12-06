package com.ssafy.letcipe.api.dto.report;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JPQLApiReportDto {
    private String methodName;
    private long count;
}
