package com.ssafy.letcipe.api.service;

import com.ssafy.letcipe.api.dto.history.*;
import com.ssafy.letcipe.domain.detailCode.DetailCodeRepository;
import com.ssafy.letcipe.domain.history.History;
import com.ssafy.letcipe.domain.history.HistoryRepository;
import com.ssafy.letcipe.domain.historyIngredient.HistoryIngredient;
import com.ssafy.letcipe.domain.historyIngredient.HistoryIngredientRepository;
import com.ssafy.letcipe.domain.historyIngredient.PurchaseType;
import com.ssafy.letcipe.domain.historyItem.HistoryItem;
import com.ssafy.letcipe.domain.type.StatusType;
import com.ssafy.letcipe.exception.AuthorityViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepository historyRepository;
    private final HistoryIngredientRepository historyIngredientRepository;
    private final RecipeService recipeService;

    private final DetailCodeRepository detailCodeRepository;

    public History findHistory(Long historyId) {
        return historyRepository.findById(historyId).orElseThrow(() -> new NullPointerException("히스토리를 찾을 수 없습니다."));
    }

    @Transactional
    public List<ResGetHistoryDto> getHistoryList(Long userId) {
        List<History> historyList = historyRepository.findByUserIdAndIsDeleted(userId, StatusType.N);
        List<ResGetHistoryDto> result = new ArrayList<>();
        for (History history : historyList) {
            List<ResGetHistoryItemDto> historyItems = new ArrayList<>();
            for (HistoryItem historyItem : history.getHistoryItems()) {
                historyItems.add(new ResGetHistoryItemDto(historyItem, recipeService.getRecipeDto(historyItem.getRecipe())));
            }
            result.add(new ResGetHistoryDto(history, historyItems));
        }
        return result;
    }

    @Transactional
    public ResGetDetailHistoryDto getHistory(Long userId, Long historyId) throws Exception{
        History history = findHistory(historyId);
        if (!userId.equals(history.getUser().getId())) throw new AuthorityViolationException("접근 권한이 없습니다.");
        List<ResGetHistoryItemDto> historyItems = new ArrayList<>();
        for (HistoryItem historyItem : history.getHistoryItems()) {
            historyItems.add(new ResGetHistoryItemDto(historyItem, recipeService.getRecipeDto(historyItem.getRecipe())));
        }

        List<ResGetHistoryIngredientDto> historyIngredientDtoList = new ArrayList<>();

        for (HistoryIngredient historyIngredient : history.getHistoryIngredients()) {
            // TODO: 나중에 join하는 쿼리문 REPO에 만들어서 그거로 변경하기. ㅠㅠ
            String category = detailCodeRepository.findById(historyIngredient.getIngredient().getCategory()).orElseThrow(
                    ()-> new Exception() ).getHeader().getName();
            historyIngredientDtoList.add(new ResGetHistoryIngredientDto(historyIngredient, category));
        }
        return new ResGetDetailHistoryDto(history, historyItems, historyIngredientDtoList);
    }

    public void deleteHistory(Long userId, Long historyId) {
        History history = findHistory(historyId);
        if (!userId.equals(history.getUser().getId())) throw new AuthorityViolationException("삭제 권한이 없습니다.");
        history.delete();
        historyRepository.save(history);
    }

    public void updateHistory(Long userId, ReqUpdateHistoryDto reqUpdateHistoryDto) {
        History history = findHistory(reqUpdateHistoryDto.getId());
        if (!userId.equals(history.getUser().getId())) throw new AuthorityViolationException("수정 권한이 없습니다.");
        history.update(reqUpdateHistoryDto.getProcess());
        historyRepository.save(history);
    }

    @Transactional
    public void checkHistoryIngredient(Long userId, Long history_ingredient_id) {
        HistoryIngredient historyIngredient = historyIngredientRepository.findById(history_ingredient_id).orElseThrow(() -> new NullPointerException("히스토리 재료를 찾을 수 없습니다."));
        if (!userId.equals(historyIngredient.getHistory().getUser().getId()))
            throw new AuthorityViolationException("접근 권한이 없습니다.");
        PurchaseType purchaseType = historyIngredient.getPurchaseType();
        if (purchaseType == PurchaseType.N) historyIngredient.updateHistoryIngredient(PurchaseType.Y);
        else historyIngredient.updateHistoryIngredient(PurchaseType.N);
        historyIngredientRepository.save(historyIngredient);
    }
}
