package com.ssafy.letcipe.api.controller;

import com.ssafy.letcipe.api.dto.*;

import com.ssafy.letcipe.api.dto.board.ReqPostBoardDto;
import com.ssafy.letcipe.api.dto.board.ResGetBoardDto;
import com.ssafy.letcipe.api.dto.board.ResGetBoardListDto;
import com.ssafy.letcipe.api.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("")
    public ResponseEntity<List<ResGetBoardListDto>> getBoardList(Pageable pageable) throws Exception{
        return new ResponseEntity<>(boardService.getBoardList(pageable), HttpStatus.OK);
    }

    @GetMapping("/{board_id}")
    public ResponseEntity<ResGetBoardDto> getBoard(@PathVariable("board_id") Long boardId) {

        return new ResponseEntity<>(boardService.getBoard(boardId), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity postBoard(@RequestBody ReqPostBoardDto reqPostBoardDto) {
        Long userId = 1L;
        boardService.postBoard(reqPostBoardDto.getTitle(), reqPostBoardDto.getContent(), userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{board_id}")
    public ResponseEntity updateBoard(@PathVariable("board_id") Long boardId, @RequestBody ReqPostBoardDto reqPostBoardDto) throws Exception {
        Long userId = 1L;
        boardService.putBoard(boardId, reqPostBoardDto.getTitle(), reqPostBoardDto.getContent(), userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PatchMapping("/{board_id}")
    public ResponseEntity patchBoard(@PathVariable("board_id") Long boardId) throws Exception {
        Long userId = 1L;
        boardService.patchBoard(boardId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
