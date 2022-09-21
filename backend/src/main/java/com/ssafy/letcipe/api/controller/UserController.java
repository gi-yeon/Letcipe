package com.ssafy.letcipe.api.controller;

import com.ssafy.letcipe.api.dto.user.ReqLoginUserDto;
import com.ssafy.letcipe.api.dto.user.ReqPostUserDto;
import com.ssafy.letcipe.api.dto.user.ReqPutUserDto;
import com.ssafy.letcipe.api.dto.user.ResGetUserDto;
import com.ssafy.letcipe.api.service.JwtService;
import com.ssafy.letcipe.api.service.UserService;
import com.ssafy.letcipe.domain.user.User;
import com.ssafy.letcipe.util.EncryptUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    private final Logger logger = LoggerFactory.getLogger(RecipeController.class);

    /**
     * 회원가입
     * 
     * @param requestDto
     * @return
     * @throws NoSuchAlgorithmException
     */
    @PostMapping("")
    public ResponseEntity createUser(@RequestBody ReqPostUserDto requestDto) throws NoSuchAlgorithmException {
        userService.createUser(requestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("login")
    public ResponseEntity loginUser(@RequestBody ReqLoginUserDto requestDto) throws NoSuchAlgorithmException {
        User user = userService.loginUser(requestDto);
        String token = jwtService.createToken(user);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("access-token", token);

        return ResponseEntity.ok(resultMap);
    }

}
