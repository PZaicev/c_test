package com.test.task.microservice2.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class ErrorResponseBody {

    private String status;

    private Integer code;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private ZonedDateTime timestamp;

    private String message;

    private String description;

    private String path;

    private List<String> errors;

    public ErrorResponseBody(HttpStatus httpStatus, String message, String description, String path, List<String> errors) {
        this.status = httpStatus.name();
        this.code = httpStatus.value();
        this.message = message;
        this.description = description;
        this.path = path;
        this.errors = errors;
        this.timestamp = ZonedDateTime.now();
    }
}
