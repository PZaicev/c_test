package com.test.task.microservice2.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class RefinancingCreditTest {

    private RefinancingCredit refinancingCredit;

    @BeforeEach
    public void initialize(){
        refinancingCredit = new RefinancingCredit();
        refinancingCredit.setOrderNum(2L);
        refinancingCredit.setAmount(BigDecimal.valueOf(100));
    }

    @Test
    public void correctConstructorInitializeTest(){
        assertEquals(2L, refinancingCredit.getOrderNum(), "Order number should be the same as in constructor");
        assertEquals(BigDecimal.valueOf(100), refinancingCredit.getAmount(), "Amount should be the same as constructor");
    }
}
