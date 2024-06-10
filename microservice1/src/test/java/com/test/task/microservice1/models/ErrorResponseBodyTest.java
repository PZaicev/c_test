package com.test.task.microservice1.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.test.task.microservice1.models.responses.ErrorResponseBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ErrorResponseBodyTest {

    private ErrorResponseBody errorResponseBody;
    private List<String> errorList;
    private final ZonedDateTime zonedDateTime = ZonedDateTime.now();

    @BeforeEach
    public void initialize(){
        errorList = List.of("error1", "error2", "error3");

        errorResponseBody = new ErrorResponseBody();
        errorResponseBody.setCode(HttpStatus.BAD_REQUEST.value());
        errorResponseBody.setStatus(HttpStatus.BAD_REQUEST.name());
        errorResponseBody.setMessage("error message");
        errorResponseBody.setDescription("description");
        errorResponseBody.setPath("some/path");
        errorResponseBody.setErrors(errorList);
        errorResponseBody.setTimestamp(zonedDateTime);
    }

    @Test
    public void checkErrorResponseBodyAfterInitializationTest(){
        assertEquals(HttpStatus.BAD_REQUEST.value(), errorResponseBody.getCode(), "Code should be the same as in initialization method");
        assertEquals(HttpStatus.BAD_REQUEST.name(), errorResponseBody.getStatus(), "Status should be the same as in initialization method");
        assertEquals("error message", errorResponseBody.getMessage(), "Message should be the same as in initialization method");
        assertEquals("description", errorResponseBody.getDescription(), "Description should be the same as in initialization method");
        assertEquals("some/path", errorResponseBody.getPath(), "Path should be the same as in initialization method");
        assertEquals(errorList.size(), errorResponseBody.getErrors().size(), "Size of Error list should be the same as in initialization method");
        assertEquals(zonedDateTime, errorResponseBody.getTimestamp(), "Timestamp should be the same as in initialization method");
    }

    @Test
    public void checkErrorResponseBodyAfterConstructorInitializationTest(){
        errorResponseBody = new ErrorResponseBody(HttpStatus.BAD_REQUEST, "error message", "description", "some/path", errorList);

        assertEquals(HttpStatus.BAD_REQUEST.value(), errorResponseBody.getCode(), "Code should be the same as in initialization method");
        assertEquals(HttpStatus.BAD_REQUEST.name(), errorResponseBody.getStatus(), "Status should be the same as in initialization method");
        assertEquals("error message", errorResponseBody.getMessage(), "Message should be the same as in initialization method");
        assertEquals("description", errorResponseBody.getDescription(), "Description should be the same as in initialization method");
        assertEquals("some/path", errorResponseBody.getPath(), "Path should be the same as in initialization method");
        assertEquals(errorList.size(), errorResponseBody.getErrors().size(), "Size of Error list should be the same as in initialization method");
        assertNotNull(errorResponseBody.getTimestamp(), "Timestamp should be initialized in constructor");
    }

    @Test
    public void actualDateTimeShouldBeInCorrectFormatAfterConvertingToJSONTest() throws JsonProcessingException {
        //convert responseBody obj to json
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = objectMapper.writeValueAsString(errorResponseBody);

        //get actualDateTime string from json
        int indexActualDateTime = json.indexOf("\"timestamp\":\"") + 13;
        String parsedActualTime = json.substring(indexActualDateTime, json.indexOf("\",", indexActualDateTime));

        assertEquals(parsedActualTime, errorResponseBody.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")),
                "Timestamp should be correct and follow the format yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    }
}
