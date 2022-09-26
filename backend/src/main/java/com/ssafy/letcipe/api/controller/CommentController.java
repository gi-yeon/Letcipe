package com.ssafy.letcipe.api.controller;

import com.ssafy.letcipe.api.dto.ReqPostBoardCommentDto;
import com.ssafy.letcipe.api.dto.ReqPutBoardCommentDto;
import com.ssafy.letcipe.api.dto.comment.ReqPatchCommentDto;
import com.ssafy.letcipe.api.dto.comment.ReqPostCommentDto;
import com.ssafy.letcipe.api.dto.comment.ReqPutCommentDto;
import com.ssafy.letcipe.api.dto.comment.ResGetCommentDto;
import com.ssafy.letcipe.api.service.CommentService;
import com.ssafy.letcipe.domain.comment.BoardType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{board_type}/{board_id}")
    public ResponseEntity<List<ResGetCommentDto>> getComment(@PathVariable("board_type") BoardType boardType
                                                            ,@PathVariable("board_id") Long boardId) {
        return new ResponseEntity<>(commentService.getComment(boardType, boardId), HttpStatus.OK);
    }


    @PutMapping("")
    public ResponseEntity putComment(@RequestBody ReqPutCommentDto commentDto) throws Exception {
        Long userId = 1L;
        commentService.putComment(commentDto, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("")
    public ResponseEntity patchBoardComment(@RequestBody ReqPatchCommentDto commentDto) throws Exception {
        Long userId = 1L;
        commentService.patchComment(commentDto, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity postComment(@RequestBody ReqPostCommentDto commentDto){
        Long userId = 1L;
        commentService.postComment(commentDto, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
