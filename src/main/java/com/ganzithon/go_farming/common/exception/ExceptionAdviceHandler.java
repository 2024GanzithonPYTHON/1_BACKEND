package com.ganzithon.go_farming.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class ExceptionAdviceHandler {

    /*개발자가 직접 예외처리*/
    @ExceptionHandler({CustomException.class})
    public ResponseEntity<ExceptionDTO> handleUncheckException(CustomException e) {
        return new ResponseEntity<>(
                new ExceptionDTO(e),
                HttpStatus.valueOf(e.getCode()));
    }

    /*지원하지 않는 HTTP METHOD 요청*/
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        return new ResponseEntity<>(e.getMessage(),
                HttpStatus.METHOD_NOT_ALLOWED);
    }

    /*이외 예상치 못한 예외상황*/
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
