package com.ssafy.letcipe.api.dto.history;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReqHistoryReviewDto {
    Long historyId;
    String review;

}
