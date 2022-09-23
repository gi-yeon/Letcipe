package com.ssafy.letcipe.api.dto.history;

import com.ssafy.letcipe.domain.history.History;
import com.ssafy.letcipe.domain.history.ProcessType;
import com.ssafy.letcipe.domain.historyIngredient.HistoryIngredient;
import com.ssafy.letcipe.domain.historyItem.HistoryItem;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ResGetDetailHistoryDto {
    private Long id;
    private LocalDateTime regTime;
    private ProcessType process;
    private String review;
    private List<ResGetHistoryItemDto> historyItems;
    private List<ResGetHistoryIngredientDto> historyIngredients;

    public ResGetDetailHistoryDto(History history) {
        this.id = history.getId();
        this.regTime = history.getRegTime();
        this.process = history.getProcess();
        this.review = history.getReview();
        this.historyItems = new ArrayList<>();
        for (HistoryItem historyItem : history.getHistoryItems()) {
            historyItems.add(new ResGetHistoryItemDto(historyItem));
        }
        for (HistoryIngredient historyIngredient : history.getHistoryIngredients()) {
//            historyItems.add(new ResGetHistoryIngredientDto(historyIngredient.getIngredient()));
        }
    }
}
