package com.test.task.microservice2.exceptions;

import com.test.task.microservice2.constants.MessagesConstant;
import com.test.task.microservice2.models.ErrorResponseBody;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponseBody> handleCommonException(Exception exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResponseBody(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        MessagesConstant.INTERNAL_SERVER_ERROR_CODE_MESSAGE,
                        MessagesConstant.INTERNAL_SERVER_ERROR_DESCRIPTION_MESSAGE,
                        request.getRequestURI(),
                        List.of(exception.getMessage())
                )
        );
    }
}
