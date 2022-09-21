package com.ssafy.letcipe.api.controller;

import com.ssafy.letcipe.api.dto.ReqPostBoardCommentDto;
import com.ssafy.letcipe.api.dto.ReqPutBoardCommentDto;
import com.ssafy.letcipe.api.dto.ResGetBoardDto;
import com.ssafy.letcipe.api.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

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
