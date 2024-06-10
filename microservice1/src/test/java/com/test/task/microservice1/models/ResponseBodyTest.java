package com.test.task.microservice1.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.test.task.microservice1.constants.MessagesConstant;
import com.test.task.microservice1.models.responses.ResponseBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class ResponseBodyTest {

    private ResponseBody<Object> responseBody;
    private Object object;
    private final ZonedDateTime zonedDateTime = ZonedDateTime.now();

    @BeforeEach
    public void initialize(){
        object = new Object();
        responseBody = new ResponseBody<>();
        responseBody.setStatus(MessagesConstant.SUCCESS_MESSAGE);
        responseBody.setCode(MessagesConstant.CREDIT_MESSAGES_CODE);
        responseBody.setDescription(MessagesConstant.CREDIT_MESSAGES_DESCRIPTION);
        responseBody.setActualDateTime(zonedDateTime);
        responseBody.setData(object);
    }

    @Test
    public void checkResponseAfterInitializationTest(){
        assertEquals(MessagesConstant.SUCCESS_MESSAGE, responseBody.getStatus(), "Status should be the same as in initialization method");
        assertEquals(MessagesConstant.CREDIT_MESSAGES_CODE, responseBody.getCode(), "Code should be the same as in initialization method");
        assertEquals(MessagesConstant.CREDIT_MESSAGES_DESCRIPTION, responseBody.getDescription(), "Description should be the same as in initialization method");
        assertEquals(zonedDateTime, responseBody.getActualDateTime(), "ActualDateTime should be the same as in initialization method");
        assertEquals(object, responseBody.getData(), "Data should be the same as in initialization method");
    }

    @Test
    public void statusShouldBeSuccessIfIsSuccessTrueTest(){
        responseBody = new ResponseBody<>()
                .createResponse(true, MessagesConstant.CREDIT_MESSAGES_CODE, MessagesConstant.CREDIT_MESSAGES_DESCRIPTION, new Object());

        assertEquals(MessagesConstant.SUCCESS_MESSAGE, responseBody.getStatus(), "Status should be SUCCESS if ifSuccess = true");
    }

    @Test
    public void statusShouldBeFailureIfIsSuccessFalseTest(){
        responseBody = new ResponseBody<>()
                .createResponse(false, MessagesConstant.CREDIT_MESSAGES_CODE, MessagesConstant.CREDIT_MESSAGES_DESCRIPTION, new Object());

        assertEquals(MessagesConstant.FAILURE_MESSAGE, responseBody.getStatus(), "Status should be FAILURE if ifSuccess = false");
    }

    @Test
    public void actualDateTimeShouldBeInCorrectFormatAfterConvertingToJSONTest() throws JsonProcessingException {
        responseBody.setData(null);

        //convert responseBody obj to json
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = objectMapper.writeValueAsString(responseBody);

        //get actualDateTime string from json
        int indexActualDateTime = json.indexOf("\"actualDateTime\":\"") + 18;
        String parsedActualTime = json.substring(indexActualDateTime, json.indexOf("\",", indexActualDateTime));

        assertEquals(parsedActualTime, responseBody.getActualDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")),
                "ActualDateTime should be correct and follow the format yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    }
}
