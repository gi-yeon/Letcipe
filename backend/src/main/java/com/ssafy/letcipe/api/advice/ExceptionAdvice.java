package com.ssafy.letcipe.api.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public  ResponseEntity duplicatedException(Exception e) {
        printLog(e);
        return new ResponseEntity(HttpStatus.CONFLICT);
    }
}
