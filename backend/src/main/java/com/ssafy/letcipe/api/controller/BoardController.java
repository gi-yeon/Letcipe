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
    public ResponseEntity<List<ResGetBoardListDto>> getBoardList(int page, int board_num) throws Exception{
        return new ResponseEntity<>(boardService.getBoardList(page, board_num), HttpStatus.OK);
    }

//    @GetMapping("")
//    public ResponseEntity<List<ResGetBoardListDto>> getBoardList() {
////        List<ResGetBoardListDto> boards = boardService.getBoardList();
////        Map<String, Object> res = new HashMap<>();
////        res.put("boardList", boards);
//        return new ResponseEntity<>(  boardService.getBoardList() , HttpStatus.OK);
//    }



    @GetMapping("/{board_id}")
    public ResponseEntity<ResGetBoardDto> getBoard(@PathVariable Long board_id) {
        Long user_id = 1L;
        return new ResponseEntity<>(boardService.getBoard(board_id, user_id), HttpStatus.OK);
    }

    @PostMapping("/{board_id}/comment")
    public ResponseEntity postBoardComment(@PathVariable Long board_id, @RequestBody ReqPostBoardCommentDto boardCommentDto) {
        Long user_id = 1L;
        boardService.postBoardComment(board_id, boardCommentDto, user_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity postBoard(@RequestBody ReqPostBoardDto reqPostBoardDto) {
        Long user_id = 1L;
        boardService.postBoard(reqPostBoardDto.getTitle(), reqPostBoardDto.getContent(), user_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{board_id}")
    public ResponseEntity updateBoard(@PathVariable Long board_id, @RequestBody ReqPostBoardDto reqPostBoardDto) throws Exception {
        Long user_id = 1L;
        boardService.putBoard(board_id, reqPostBoardDto.getTitle(), reqPostBoardDto.getContent(), user_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PatchMapping("/{board_id}")
    public ResponseEntity patchBoard(@PathVariable Long board_id) throws Exception {
        Long user_id = 1L;
        boardService.patchBoard(board_id, user_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/comment/{board_comment_id}")
    public ResponseEntity putBoardComment(@PathVariable Long board_comment_id, @RequestBody  ReqPutBoardCommentDto boardCommentDto) throws Exception {
        Long user_id  = 1L;
        boardService.putBoardComment(board_comment_id, boardCommentDto, user_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/comment/{board_comment_id}")
    public ResponseEntity deleteBoardComment(@PathVariable Long board_comment_id) throws Exception {
        Long user_id = 1L;
        boardService.deleteBoardComment(board_comment_id, user_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
