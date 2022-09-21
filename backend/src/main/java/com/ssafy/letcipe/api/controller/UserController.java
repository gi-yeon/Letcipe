package com.ssafy.letcipe.api.controller;

import com.ssafy.letcipe.api.dto.user.ReqPostUserDto;
import com.ssafy.letcipe.api.dto.user.ReqPutUserDto;
import com.ssafy.letcipe.api.dto.user.ResGetUserDto;
import com.ssafy.letcipe.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/admin")
    public ResponseEntity createAdmin(@RequestBody ReqPostUserDto requestDto){
        userService.createAdmin(requestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("")
    public ResponseEntity createUser(@RequestBody ReqPostUserDto requestDto){
        userService.createUser(requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity readUserInfo(){
        Long userId=1L; // TODO 토큰에서 가져오기
        ResGetUserDto responseDto = userService.readUserInfo(userId);
        return ResponseEntity.ok().body(responseDto);
    }

    @PatchMapping("")
    public ResponseEntity deleteUser(){
        Long userId=1L; // TODO 토큰에서 가져오기
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("")
    public ResponseEntity updateUser(@RequestBody ReqPutUserDto requestDto){
        Long userId=1L; // TODO 토큰에서 가져오기
        userService.updateUser(requestDto, userId);
        return ResponseEntity.ok().build();
    }
}
