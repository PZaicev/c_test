package com.test.task.microservice2.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.test.task.microservice2.dao.CreditRepository;
import com.test.task.microservice2.models.Credit;
import com.test.task.microservice2.models.CreditExpIdx;
import com.test.task.microservice2.models.CreditHelper;
import com.test.task.microservice2.models.ErrorResponseBody;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OfferController.class)
public class OfferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreditRepository creditRepository;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    public void validPostCreditShouldReturn201WithEmptyBodyTest() throws Exception {
        Credit credit = CreditHelper.getValidCredit();
        CreditExpIdx creditExpIdx = CreditHelper.getValidCreditExpIdx();
        Mockito.when(creditRepository.save(creditExpIdx)).thenReturn(null);

        MvcResult mvcResult = mockMvc.perform(
                post("/v1/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                mapper.writeValueAsString(credit)
                        )
        ).andExpect(status().isCreated())
         .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("", content, "Body should be empty after successful POST execution");
    }

    @Test
    public void postWithoutBodyShouldReturnErrorResponseBodyTest() throws Exception {
        Mockito.when(creditRepository.save(new CreditExpIdx())).thenReturn(null);

        MvcResult mvcResult = mockMvc.perform(
                post("/v1/offers")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is5xxServerError())
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

    @Test
    public void postWithEmptyBodyShouldReturnErrorResponseBodyTest() throws Exception {
        Mockito.when(creditRepository.save(new CreditExpIdx())).thenThrow(IllegalArgumentException.class);

        MvcResult mvcResult = mockMvc.perform(
                        post("/v1/offers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("")
        ).andExpect(status().is5xxServerError())
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

    @Test
    public void validGetCreditShouldReturn200WithECreditBodyTest() throws Exception {
        CreditExpIdx creditExpIdx = CreditHelper.getValidCreditExpIdx();
        Mockito.when(creditRepository.findById(creditExpIdx.getClientId())).thenReturn(Optional.of(creditExpIdx));

        MvcResult mvcResult = mockMvc.perform(
                get("/v1/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Client-Id", creditExpIdx.getClientId())
        ).andExpect(status().isOk())
         .andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        assertEquals(mapper.writeValueAsString(creditExpIdx.getCredit()), content, "Body should match Credit json");
    }

    @Test
    public void validGetCreditShouldReturn200WithEmptyBodyWhenCreditNotInDBTest() throws Exception {
        CreditExpIdx creditExpIdx = CreditHelper.getValidCreditExpIdx();
        Mockito.when(creditRepository.findById(creditExpIdx.getClientId())).thenReturn(Optional.empty());

        MvcResult mvcResult = mockMvc.perform(
                        get("/v1/offers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Client-Id", creditExpIdx.getClientId())
        ).andExpect(status().isOk())
         .andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        assertEquals("", content, "Body should be empty after successful GET execution");
    }

    @Test
    public void invalidGetCreditWithoutClientIdShouldReturn500ErrorResponseBodyTest() throws Exception {
        CreditExpIdx creditExpIdx = CreditHelper.getValidCreditExpIdx();
        Mockito.when(creditRepository.findById(creditExpIdx.getClientId())).thenReturn(Optional.empty());

        MvcResult mvcResult = mockMvc.perform(
                        get("/v1/offers")
                                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is5xxServerError())
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
