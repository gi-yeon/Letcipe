package com.ssafy.letcipe.api.controller;

import com.ssafy.letcipe.api.dto.*;
import com.ssafy.letcipe.api.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    //페이지네이션
    @GetMapping("")
    public ResponseEntity<List<ResGetBoardListDto>> getBoardList(int page, int boardNum) throws Exception{
        return new ResponseEntity<>(boardService.getBoardList(page, boardNum), HttpStatus.OK);
    }


    @GetMapping("/{boardId}")
    public ResponseEntity<ResGetBoardDto> getBoard(@PathVariable Long boardId) {
        Long userId = 1L;
        return new ResponseEntity<>(boardService.getBoard(boardId, userId), HttpStatus.OK);
    }

    @PostMapping("/{boardId}/comment")
    public ResponseEntity postBoardComment(@PathVariable Long boardId, @RequestBody ReqPostBoardCommentDto boardCommentDto) {
        Long userId = 1L;
        boardService.postBoardComment(boardId, boardCommentDto, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity postBoard(@RequestBody ReqPostBoardDto reqPostBoardDto) {
        Long userId = 1L;
        boardService.postBoard(reqPostBoardDto.getTitle(), reqPostBoardDto.getContent(), userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{boardId}")
    public ResponseEntity updateBoard(@PathVariable Long boardId, @RequestBody ReqPostBoardDto reqPostBoardDto) throws Exception {
        Long userId = 1L;
        boardService.putBoard(boardId, reqPostBoardDto.getTitle(), reqPostBoardDto.getContent(), userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PatchMapping("/{boardId}")
    public ResponseEntity patchBoard(@PathVariable Long boardId) throws Exception {
        Long userId = 1L;
        boardService.patchBoard(boardId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/comment/{boardCommentId}")
    public ResponseEntity putBoardComment(@PathVariable Long boardCommentId, @RequestBody  ReqPutBoardCommentDto boardCommentDto) throws Exception {
        Long userId  = 1L;
        boardService.putBoardComment(boardCommentId, boardCommentDto, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/comment/{boardCommentId}")
    public ResponseEntity patchBoardComment(@PathVariable Long boardCommentId) throws Exception {
        Long userId = 1L;
        boardService.patchBoardComment(boardCommentId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
