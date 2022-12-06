package com.ssafy.letcipe.api.advice;

import com.ssafy.letcipe.exception.AuthorityViolationException;
import com.ssafy.letcipe.exception.BadRequestException;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class ExceptionAdvice {
    public static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    private final boolean showTrace = true;

    private void printLog(Exception e) {
        logger.error("에러 핸들링 : {}", e.getMessage());
        if (showTrace) e.printStackTrace();
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity handleSQLException(SQLException e) {
        printLog(e);
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({NullPointerException.class,IllegalArgumentException.class})
    public ResponseEntity handleNPE(Exception e) {
        printLog(e);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({FileUploadException.class, FileNotFoundException.class})
    public ResponseEntity handleFileUploadException(Exception e) {
        printLog(e);
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthorityViolationException.class)
    public ResponseEntity illegalRequestException(Exception e) {
        logger.warn("해킹 시도 감지!");
        printLog(e);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public  ResponseEntity duplicatedException(Exception e) {
        printLog(e);
        return new ResponseEntity(HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity BadRequestException(Exception e) {
        logger.warn("이미 존재하는데 시도");
        printLog(e);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
