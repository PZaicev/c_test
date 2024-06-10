package com.test.task.microservice1.models;

import com.test.task.microservice1.models.credit.RefinancingCredit;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RefinancingCreditTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private RefinancingCredit refinancingCredit;

    @BeforeEach
    public void initialize(){
        refinancingCredit = new RefinancingCredit();
        refinancingCredit.setOrderNum(2L);
        refinancingCredit.setAmount(BigDecimal.valueOf(100));
    }

    @Test
    public void checkRefinancingCreditAfterInitializationTest(){
        Set<ConstraintViolation<RefinancingCredit>> violations = validator.validate(refinancingCredit);

        assertTrue(violations.isEmpty(), "There should be no violations after initialization");
        assertEquals(2L, refinancingCredit.getOrderNum(), "Order number should be the same as in initialization method");
        assertEquals(BigDecimal.valueOf(100), refinancingCredit.getAmount(), "Amount should be the same as in initialization method");
    }

    @Test
    public void brokenIfNullOrderNumGivenTest(){
        refinancingCredit.setOrderNum(null);
        Set<ConstraintViolation<RefinancingCredit>> violations = validator.validate(refinancingCredit);

        assertEquals(1, violations.size(), "There must be violation with null order num");
    }

    @Test
    public void brokenIfminusOrderNumGivenTest(){
        refinancingCredit.setOrderNum(-1L);
        Set<ConstraintViolation<RefinancingCredit>> violations = validator.validate(refinancingCredit);

        assertEquals(1, violations.size(), "There must be violation with minus order num");
    }

    @Test
    public void brokenIfNullAmountGivenTest(){
        refinancingCredit.setAmount(null);
        Set<ConstraintViolation<RefinancingCredit>> violations = validator.validate(refinancingCredit);

        assertEquals(1, violations.size(), "There must be violation with null amount");
    }

    @Test
    public void brokenIfMinusAmountGivenTest(){
        refinancingCredit.setAmount(BigDecimal.valueOf(-1));
        Set<ConstraintViolation<RefinancingCredit>> violations = validator.validate(refinancingCredit);

        assertEquals(1, violations.size(), "There must be violation with minus amount");
    }

    @Test
    public void correctConstructorInitializeTest(){
        refinancingCredit = new RefinancingCredit(2L, BigDecimal.valueOf(100));

        assertEquals(2L, refinancingCredit.getOrderNum(), "Order number should be the same as in constructor");
        assertEquals(BigDecimal.valueOf(100), refinancingCredit.getAmount(), "Amount should be the same as constructor");
    }


}
