package com.ssafy.letcipe.api.controller;

import com.ssafy.letcipe.util.FileHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {
    private final FileHandler fileHandler;

    @GetMapping("/{file}")
    public ResponseEntity<byte[]> getImage(@PathVariable("file") String fileName) throws IOException {
        byte[] fileStream = null;
        HttpStatus status = HttpStatus.OK;
        HttpHeaders header = new HttpHeaders();

        File file = fileHandler.getImageFile(fileName);

        header.add("Content-Type", Files.probeContentType(file.toPath()));

        fileStream = FileCopyUtils.copyToByteArray(file);

        return new ResponseEntity(fileStream, header, status);

    }
}
