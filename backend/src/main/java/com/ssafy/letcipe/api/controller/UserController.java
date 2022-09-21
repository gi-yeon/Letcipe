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

    @PostMapping("")
    public ResponseEntity createUser(@RequestBody ReqPostUserDto requestDto){
        userService.createUser(requestDto);
        return ResponseEntity.ok().build();
    }

}
