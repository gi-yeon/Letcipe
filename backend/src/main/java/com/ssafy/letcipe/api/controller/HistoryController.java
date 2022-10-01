package com.ssafy.letcipe.api.controller;

import com.ssafy.letcipe.api.dto.history.ReqUpdateHistoryDto;
import com.ssafy.letcipe.api.dto.history.ResGetHistoryDto;
import com.ssafy.letcipe.api.service.HistoryService;
import com.ssafy.letcipe.api.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
public class HistoryController {
    private final HistoryService historyService;

    private final JwtService jwtService;

    @GetMapping("")
    public ResponseEntity<?> getHistoryList(HttpServletRequest request){
        Long userId = jwtService.getUserId(request);
        List<ResGetHistoryDto> historyList = historyService.getHistoryList(userId);
        return ResponseEntity.ok(historyList);
    }

    @GetMapping("/{history_id}")
    public ResponseEntity<?> getHistory(@PathVariable("history_id") Long historyId, HttpServletRequest request) throws Exception{
        Long userId = jwtService.getUserId(request);
        return ResponseEntity.ok(historyService.getHistory(userId, historyId));
    }

    @PatchMapping("/{history_id}")
    public ResponseEntity<?> deleteHistory(@PathVariable("history_id") Long historyId, HttpServletRequest request){
        Long userId = jwtService.getUserId(request);
        historyService.deleteHistory(userId, historyId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("")
    public ResponseEntity<?> updateHistory(@RequestBody ReqUpdateHistoryDto reqUpdateHistoryDto, HttpServletRequest request){
        Long userId = jwtService.getUserId(request);
        historyService.updateHistory(userId, reqUpdateHistoryDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/ingredient/{history_ingredient_id}")
    public ResponseEntity<?> checkHistoryIngredient(@PathVariable("history_ingredient_id") Long history_ingredient_id, HttpServletRequest request){
        Long userId = jwtService.getUserId(request);
        historyService.checkHistoryIngredient(userId, history_ingredient_id);
        return ResponseEntity.ok().build();
    }
}
