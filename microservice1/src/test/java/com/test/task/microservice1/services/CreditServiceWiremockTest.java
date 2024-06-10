package com.test.task.microservice1.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.test.task.microservice1.models.CreditHelper;
import com.test.task.microservice1.models.credit.Credit;
import com.test.task.microservice1.models.credit.CreditForMS2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@WireMockTest
public class CreditServiceWiremockTest {

    @Autowired
    private CreditService creditService;

    @Value("${credit-service-client.post-credit.url}")
    private String postCreditURI;

    @Value("${credit-service-client.post-credit.url}")
    private String getCreditURI;

    private CreditForMS2 creditForMS2;

    private final ObjectMapper mapper = new ObjectMapper();

    @RegisterExtension
    private static WireMockExtension wireMockExtension = WireMockExtension.newInstance()
            .options(WireMockConfiguration.wireMockConfig().dynamicPort())
            .build();

    @DynamicPropertySource
    private static void configureWireMock(DynamicPropertyRegistry registry) {
        registry.add("${credit-service-client.domain-url}", wireMockExtension::baseUrl);
    }

    @BeforeEach
    void setUp() {
        this.creditForMS2 = new CreditForMS2(CreditHelper.getValidClientId(),
                CreditHelper.getValidCredit());
    }

    @Test
    public void postCreditWireMockTest() {
        wireMockExtension.stubFor(
                post(postCreditURI).willReturn(
                        aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withStatus(201)
                                .withBody("")
                )
        );

        ResponseEntity creditResponse = creditService.createCredit(creditForMS2);
        assertEquals(HttpStatus.CREATED, creditResponse.getStatusCode(), "Wrong status code");
        assertNull(creditResponse.getBody(), "Response body should be null");
    }

    @Test
    public void getCreditWireMockTest() throws JsonProcessingException {
        wireMockExtension.stubFor(
                get(getCreditURI)
                        .withHeader("Client-Id", equalTo(creditForMS2.getClientId()))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withStatus(200)
                                .withBody(
                                        mapper.writeValueAsString(creditForMS2)
                                )
                        )
        );

        ResponseEntity<Credit> creditResponse = creditService.getCredit(creditForMS2.getClientId());
        assertEquals(HttpStatus.OK, creditResponse.getStatusCode(), "Wrong status code");
        assertNotNull(creditResponse.getBody(), "Response body should not be null");
        assertEquals(mapper.writeValueAsString(CreditHelper.getValidCredit()),
                mapper.writeValueAsString(creditResponse.getBody()), "Credit should be the same as initialized");
    }

}
