package com.ssafy.letcipe.api.dto.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqGetCartReport {
    String attributes;
    String beginDate;
    String endDate;
}
