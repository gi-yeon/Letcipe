package com.letcipe.hadoop.controller;

import jdk.jshell.Snippet;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.zeroturnaround.exec.ProcessExecutor;

import java.io.OutputStream;

@Log4j2
@RestController
@RequestMapping("/hadoop")
public class HadoopController {
    @GetMapping("/test")
    public ResponseEntity test(){
        HttpStatus status=HttpStatus.OK;
        String msg="테스트 성공";
        try {
            String mapreduce= new ProcessExecutor()
                    .command("sh", "/home/letcipe/mapreduce.sh")
                    .readOutput(true)
                    .execute()
                    .outputUTF8();
            String copyToLocal = new ProcessExecutor()
                    .command("sh", "/home/letcipe/copy_to_local.sh")
                    .readOutput(true)
                    .execute()
                    .outputUTF8();
            System.out.println("mapreduce = "+mapreduce);
            System.out.println("copy to local = "+copyToLocal);
        }catch(Exception e){
            status=HttpStatus.BAD_REQUEST;
            msg=e.getMessage();
        }
        return ResponseEntity.status(status).body(msg);
    }
}
