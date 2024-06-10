package com.test.task.microservice1.exceptions;

import com.test.task.microservice1.constants.MessagesConstant;
import com.test.task.microservice1.models.responses.ErrorResponseBody;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponseBody> handleValidationErrors(HandlerMethodValidationException exception, HttpServletRequest request) {
        List<ParameterValidationResult> parameterValidationResultList = exception.getAllValidationResults();

        List<String> errors = parameterValidationResultList.stream()
                .map(vr -> getPreparedVariableName(vr.getResolvableErrors().getFirst().getCodes()[0])
                        + vr.getResolvableErrors().getFirst().getDefaultMessage())
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponseBody(
                        HttpStatus.BAD_REQUEST,
                        MessagesConstant.FAILURE_VALIDATION_CODE_MESSAGE,
                        MessagesConstant.FAILURE_VALIDATION_DESCRIPTION_MESSAGE,
                        request.getRequestURI(),
                        errors
                )
        );
    }

    @ExceptionHandler(Exception.class)
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

    private String getPreparedVariableName(String str){
        return str.substring(str.lastIndexOf(".") + 1) + " - ";
    }
}
