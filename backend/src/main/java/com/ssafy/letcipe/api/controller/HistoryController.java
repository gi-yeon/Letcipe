package com.ssafy.letcipe.api.controller;

import com.ssafy.letcipe.api.dto.history.ReqUpdateHistoryDto;
import com.ssafy.letcipe.api.dto.history.ResGetHistoryDto;
import com.ssafy.letcipe.api.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/{history_id}")
    public ResponseEntity<?> deleteHistory(@PathVariable("history_id") Long historyId){
        historyService.deleteHistory(historyId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("")
    public ResponseEntity<?> updateHistory(@RequestBody ReqUpdateHistoryDto reqUpdateHistoryDto){
        historyService.updateHistory(reqUpdateHistoryDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/ingredient/{history_ingredient_id}")
    public ResponseEntity<?> checkHistoryIngredient(@PathVariable("history_ingredient_id") Long history_ingredient_id){
        historyService.checkHistoryIngredient(history_ingredient_id);
        return ResponseEntity.ok().build();
    }
}
