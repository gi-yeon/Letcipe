package com.ssafy.letcipe.api.dto.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqGetApiReport {
    String beginDate;
    String endDate;
}
