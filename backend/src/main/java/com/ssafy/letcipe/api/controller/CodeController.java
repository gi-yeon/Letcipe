package com.ssafy.letcipe.api.controller;

import com.ssafy.letcipe.api.service.CodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("code")
public class CodeController {

    private final CodeService codeService;

    @GetMapping("detail/recipe")
    public ResponseEntity readRecipeDetailCode(){
        return ResponseEntity.ok(codeService.readRecipeDetailCode());
    }


}
