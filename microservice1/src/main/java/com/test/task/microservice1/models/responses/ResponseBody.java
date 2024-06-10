package com.test.task.microservice1.models.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.test.task.microservice1.constants.MessagesConstant;

import java.time.ZonedDateTime;

public class ResponseBody<T> {

    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private ZonedDateTime actualDateTime;

    private String code;

    private String description;

    private T data;

    public ResponseBody<T> createResponse(Boolean isSuccess, String code, String description, T data) {
        this.status = isSuccess ? MessagesConstant.SUCCESS_MESSAGE : MessagesConstant.FAILURE_MESSAGE;
        this.actualDateTime = ZonedDateTime.now();
        this.code = code;
        this.description = description;
        this.data = data;

        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ZonedDateTime getActualDateTime() {
        return actualDateTime;
    }

    public void setActualDateTime(ZonedDateTime actualDateTime) {
        this.actualDateTime = actualDateTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
