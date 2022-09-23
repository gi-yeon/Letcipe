package com.ssafy.letcipe.api.service;

import com.ssafy.letcipe.api.dto.history.ReqUpdateHistoryDto;
import com.ssafy.letcipe.api.dto.history.ResGetDetailHistoryDto;
import com.ssafy.letcipe.api.dto.history.ResGetHistoryDto;
import com.ssafy.letcipe.domain.history.History;
import com.ssafy.letcipe.domain.history.HistoryRepository;
import com.ssafy.letcipe.domain.history.ProcessType;
import com.ssafy.letcipe.domain.type.StatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepository historyRepository;

    public History findHistory(Long historyId) {
        return historyRepository.findById(historyId).orElseThrow(() -> new NullPointerException("히스토리를 찾을 수 없습니다."));
    }

    @Transactional
    public List<ResGetHistoryDto> getHistoryList(Long userId) {
        List<History> historyList = historyRepository.findByUserIdAndIsDeleted(userId, StatusType.N);
        List<ResGetHistoryDto> result = new ArrayList<>();
        for (History history : historyList) {
            result.add(new ResGetHistoryDto(history));
        }
        return result;
    }

    @Transactional
    public ResGetDetailHistoryDto getHistory(Long historyId) {
        History history = findHistory(historyId);
        return new ResGetDetailHistoryDto(history);
    }

    public void deleteHistory(Long historyId) {
        History history = findHistory(historyId);
        history.delete();
        historyRepository.save(history);
    }

    public void updateHistory(ReqUpdateHistoryDto reqUpdateHistoryDto) {
        History history = findHistory(reqUpdateHistoryDto.getId());
        history.update(reqUpdateHistoryDto.getProcess());
        historyRepository.save(history);
    }
}
