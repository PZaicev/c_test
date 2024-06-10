package com.test.task.microservice1.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.test.task.microservice1.constants.MessagesConstant;
import com.test.task.microservice1.models.CreditHelper;
import com.test.task.microservice1.models.credit.Credit;
import com.test.task.microservice1.models.credit.CreditForMS2;
import com.test.task.microservice1.models.responses.ErrorResponseBody;
import com.test.task.microservice1.models.responses.ResponseBody;
import com.test.task.microservice1.services.CreditService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = CreditController.class)
public class CreditControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreditService creditService;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    public void validPostCreditShouldReturn201WithEmptyBody() throws Exception {
        String clientId = CreditHelper.getValidClientId();
        CreditForMS2 creditForMS2 = new CreditForMS2(clientId, CreditHelper.getValidCredit());

        Mockito.when(this.creditService.createCredit(any()))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(""));

        mockMvc.perform(
                post("/v1/credits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Client-Id", clientId)
                        .content(
                                mapper.writeValueAsString(creditForMS2)
                        )
                ).andExpect(status().isCreated());
    }

    @Test
    public void invalidClientIdForPostCreditShouldReturn404ErrorResponseBody() throws Exception {
        String clientId = "wrongClientIdHere";
        CreditForMS2 creditForMS2 = new CreditForMS2(clientId, CreditHelper.getValidCredit());

        Mockito.when(this.creditService.createCredit(any()))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(""));

        MvcResult mvcResult = mockMvc.perform(
                post("/v1/credits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Client-Id", clientId)
                        .content(
                                mapper.writeValueAsString(creditForMS2)
                        )
        ).andExpect(status().isBadRequest())
         .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponseBody errorResponseBody = mapper.readValue(content, ErrorResponseBody.class);

        assertEquals(errorResponseBody.getCode(), HttpStatus.BAD_REQUEST.value(), "The Code should be the same in response body");
        assertEquals(errorResponseBody.getStatus(), HttpStatus.BAD_REQUEST.name(), "The Status should be the same in response body");
        assertNotNull(errorResponseBody.getTimestamp(), "Timestamp should be present");
        assertNotNull(errorResponseBody.getPath(), "Path should be present");
        assertNotNull(errorResponseBody.getErrors(), "List of errors should be present");
        assertFalse(errorResponseBody.getErrors().isEmpty(), "List of errors should contain at least one error");
    }

    @Test
    public void invalidCreditForPostCreditShouldReturn404ErrorResponseBody() throws Exception {
        String clientId = CreditHelper.getValidClientId();
        CreditForMS2 creditForMS2 = new CreditForMS2(clientId, CreditHelper.getValidCredit());
        creditForMS2.setOfferId("wrongId");
        creditForMS2.setVerificationRequired(null);
        creditForMS2.setProofIncomeRequired("not");

        Mockito.when(this.creditService.createCredit(any()))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(""));

        MvcResult mvcResult = mockMvc.perform(
                        post("/v1/credits")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Client-Id", clientId)
                                .content(
                                        mapper.writeValueAsString(creditForMS2)
                                )
                ).andExpect(status().isBadRequest())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponseBody errorResponseBody = mapper.readValue(content, ErrorResponseBody.class);

        assertEquals(errorResponseBody.getCode(), HttpStatus.BAD_REQUEST.value(), "The Code should be the same in response body");
        assertEquals(errorResponseBody.getStatus(), HttpStatus.BAD_REQUEST.name(), "The Status should be the same in response body");
        assertNotNull(errorResponseBody.getTimestamp(), "Timestamp should be present");
        assertNotNull(errorResponseBody.getPath(), "Path should be present");
        assertNotNull(errorResponseBody.getErrors(), "List of errors should be present");
        assertFalse(errorResponseBody.getErrors().isEmpty(), "List of errors should contain at least one error");
    }

    @Test
    public void validGetCreditShouldReturnResponseBodyWithCreditTest() throws Exception {
        String clientId = CreditHelper.getValidClientId();
        Credit credit = CreditHelper.getValidCredit();

        Mockito.when(this.creditService.getCredit(clientId))
                .thenReturn(ResponseEntity.status(HttpStatus.OK).body(credit));

        MvcResult mvcResult = mockMvc.perform(
                get("/v1/credits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Client-Id", clientId)
                )
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        ResponseBody<Credit> creditResponseBody = mapper.readValue(content, ResponseBody.class);

        assertEquals(creditResponseBody.getStatus(), MessagesConstant.SUCCESS_MESSAGE, "The Message should be success in response body");
        assertEquals(creditResponseBody.getCode(), MessagesConstant.CREDIT_MESSAGES_CODE, "The Code should be the same in response body");
        assertEquals(new String(creditResponseBody.getDescription().getBytes("ISO-8859-1"), "UTF-8"),
                MessagesConstant.CREDIT_MESSAGES_DESCRIPTION, "The Code should be the same in response body");
        assertNotNull(creditResponseBody.getData(), "The Credit should be null in response body");
        assertEquals(mapper.writeValueAsString(credit), mapper.writeValueAsString(creditResponseBody.getData()),
                "The Credit should be the same in response body");
    }

    @Test
    public void validGetCreditShouldReturnEmptyDataWithNotExistingClientIdTest() throws Exception {
        String clientId = CreditHelper.getValidClientId();

        Mockito.when(this.creditService.getCredit(clientId))
                .thenReturn(ResponseEntity.status(HttpStatus.OK).body(null));

        MvcResult mvcResult = mockMvc.perform(
                        get("/v1/credits")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Client-Id", clientId)
                )
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        ResponseBody<Credit> creditResponseBody = mapper.readValue(content, ResponseBody.class);

        assertEquals(creditResponseBody.getStatus(), MessagesConstant.FAILURE_MESSAGE, "The Message should be failure in response body");
        assertEquals(creditResponseBody.getCode(), MessagesConstant.CREDIT_MESSAGES_CODE, "The Code should be the same in response body");
        assertEquals(new String(creditResponseBody.getDescription().getBytes("ISO-8859-1"), "UTF-8"),
                MessagesConstant.CREDIT_MESSAGES_DESCRIPTION, "The Code should be the same in response body");
        assertNull(creditResponseBody.getData(), "The Credit should be null in response body");
    }

    @Test
    public void invalidGetCreditShouldReturnErrorResponseTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        get("/v1/credits")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Client-Id", "asd")
                )
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponseBody errorResponseBody = mapper.readValue(content, ErrorResponseBody.class);

        assertEquals(errorResponseBody.getCode(), HttpStatus.BAD_REQUEST.value(), "The Code should be the same in response body");
        assertEquals(errorResponseBody.getStatus(), HttpStatus.BAD_REQUEST.name(), "The Status should be the same in response body");
        assertNotNull(errorResponseBody.getTimestamp(), "Timestamp should be present");
        assertNotNull(errorResponseBody.getPath(), "Path should be present");
        assertNotNull(errorResponseBody.getErrors(), "List of errors should be present");
        assertFalse(errorResponseBody.getErrors().isEmpty(), "List of errors should contain at least one error");
    }

    @Test
    public void withoutSecondServiceGetCreditShouldReturnErrorResponseTest() throws Exception {
        String clientId = CreditHelper.getValidClientId();
        Mockito.when(this.creditService.getCredit(any())).thenThrow(new RuntimeException("Error"));

        MvcResult mvcResult = mockMvc.perform(
                        get("/v1/credits")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Client-Id", clientId)
                )
                .andExpect(status().is5xxServerError())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        ErrorResponseBody errorResponseBody = mapper.readValue(content, ErrorResponseBody.class);

        assertEquals(errorResponseBody.getCode(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "The Code should be the same in response body");
        assertEquals(errorResponseBody.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR.name(), "The Status should be the same in response body");
        assertNotNull(errorResponseBody.getTimestamp(), "Timestamp should be present");
        assertNotNull(errorResponseBody.getPath(), "Path should be present");
        assertNotNull(errorResponseBody.getErrors(), "List of errors should be present");
        assertFalse(errorResponseBody.getErrors().isEmpty(), "List of errors should contain at least one error");
    }
}
