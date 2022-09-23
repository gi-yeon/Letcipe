package com.ssafy.letcipe.api.controller;

import com.ssafy.letcipe.api.dto.history.ResGetHistoryDto;
import com.ssafy.letcipe.api.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
public class HistoryController {
    private final HistoryService historyService;

    @GetMapping("")
    public ResponseEntity<?> getHistoryList(){
        List<ResGetHistoryDto> historyList = historyService.getHistoryList(1L);
        return ResponseEntity.ok(historyList);
    }

    @GetMapping("/{history_id}")
    public ResponseEntity<?> getHistory(@PathVariable("history_id") Long historyId){
        return ResponseEntity.ok(historyService.getHistory(historyId));
    }
}
