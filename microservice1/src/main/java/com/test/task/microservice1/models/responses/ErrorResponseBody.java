package com.test.task.microservice1.models.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.List;

public class ErrorResponseBody {

    private String status;

    private Integer code;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private ZonedDateTime timestamp;

    private String message;

    private String description;

    private String path;

    private List<String> errors;

    public ErrorResponseBody() {
    }

    public ErrorResponseBody(HttpStatus httpStatus, String message, String description, String path, List<String> errors) {
        this.status = httpStatus.name();
        this.code = httpStatus.value();
        this.message = message;
        this.description = description;
        this.path = path;
        this.errors = errors;
        this.timestamp = ZonedDateTime.now();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
