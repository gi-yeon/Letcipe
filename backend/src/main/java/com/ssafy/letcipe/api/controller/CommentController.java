package com.ssafy.letcipe.api.controller;

import com.ssafy.letcipe.api.dto.comment.ReqPatchCommentDto;
import com.ssafy.letcipe.api.dto.comment.ReqPostCommentDto;
import com.ssafy.letcipe.api.dto.comment.ReqPutCommentDto;
import com.ssafy.letcipe.api.dto.comment.ResGetCommentDto;
import com.ssafy.letcipe.api.service.CommentService;
import com.ssafy.letcipe.api.service.JwtService;
import com.ssafy.letcipe.domain.comment.BoardType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    private final JwtService jwtService;

    @GetMapping("/{board_type}/{board_id}")
    public ResponseEntity<List<ResGetCommentDto>> getComment(@PathVariable("board_type") BoardType boardType
            ,@PathVariable("board_id") Long boardId, Pageable pageable) {
        return new ResponseEntity<>(commentService.getComment(boardType, boardId, pageable),   HttpStatus.OK);
    }


    @PutMapping("")
    public ResponseEntity putComment(@RequestBody ReqPutCommentDto commentDto, HttpServletRequest request) throws Exception {
        Long userId = jwtService.getUserId(request);;
        commentService.putComment(commentDto, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("")
    public ResponseEntity patchBoardComment(@RequestBody ReqPatchCommentDto commentDto, HttpServletRequest request) throws Exception {
        Long userId = jwtService.getUserId(request);;
        commentService.patchComment(commentDto, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity postComment(@RequestBody ReqPostCommentDto commentDto, HttpServletRequest request){
        Long userId = jwtService.getUserId(request);;
        commentService.postComment(commentDto, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{board_type}/{board_id}/commentNum")
    public ResponseEntity<Long> getCommentNum(@PathVariable("board_type") BoardType boardType
            ,@PathVariable("board_id") Long boardId){
        return new ResponseEntity<>(commentService.getCommentNum(boardType, boardId), HttpStatus.OK);
    }
}
