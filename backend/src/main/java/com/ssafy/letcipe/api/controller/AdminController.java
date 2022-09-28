package com.ssafy.letcipe.api.controller;

import com.ssafy.letcipe.util.FileHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final FileHandler fileHandler;

    @GetMapping("test")
    public ResponseEntity getResult() throws IOException {
        File report = fileHandler.getReport("res.txt");
        BufferedReader br = new BufferedReader(new FileReader(report));
        StringBuilder sb = new StringBuilder();
        String str;
        while ((str = br.readLine()) != null) {
            sb.append(str).append("\n");
        }
        System.out.println(sb.toString());
        return ResponseEntity.ok(sb.toString());
    }
}
