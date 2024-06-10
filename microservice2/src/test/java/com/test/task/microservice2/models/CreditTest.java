package com.test.task.microservice2.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditTest {

    private Credit credit;
    private List<RefinancingCredit> refinancingCredits;

    @BeforeEach
    public void initialize(){
        RefinancingCredit refinancingCredit = new RefinancingCredit();
        refinancingCredit.setOrderNum(2L);
        refinancingCredit.setAmount(BigDecimal.valueOf(100));

        refinancingCredits = List.of(refinancingCredit);

        credit = CreditHelper.getValidCredit();
        credit.setRefinancingCredits(refinancingCredits);
    }

    @Test
    public void checkCreditAfterInitializationTest(){
        assertEquals("123e4567-e89b-12d3-a456-426655440001", credit.getClientId(), "ClientId should be the same as in initialization method");
        assertEquals("123e4567-e89b-12d3-a456-426655440000", credit.getOfferId(), "OfferId should be the same as in initialization method");
        assertEquals("STANDARD", credit.getOfferType(), "OfferType should be the same as in initialization method");
        assertEquals(true, credit.getVerificationRequired(), "VerificationRequired should be the same as in initialization method");
        assertEquals("N", credit.getProofIncomeRequired(), "ProofIncomeRequired should be the same as in initialization method");
        assertEquals(false, credit.getIdentityDocumentsRequired(), "IdentityDocumentsRequired should be the same as in initialization method");
        assertEquals("subtype", credit.getProductSubTypeCode(), "ProductSubTypeCode should be the same as in initialization method");
        assertEquals(1, credit.getTerm(), "Term should be the same as in initialization method");
        assertEquals(BigDecimal.valueOf(12.5), credit.getRate(), "Rate should be the same as in initialization method");
        assertEquals(BigDecimal.valueOf(500000), credit.getAmount(), "Amount should be the same as in initialization method");
        assertEquals(BigDecimal.valueOf(450000), credit.getAmountOverLimit(), "AmountOverLimit should be the same as in initialization method");
        assertEquals(BigDecimal.valueOf(1), credit.getInsurancePremium(), "InsurancePremium should be the same as in initialization method");
        assertEquals(BigDecimal.valueOf(5000), credit.getCommissionAmount(), "CommissionAmount should be the same as in initialization method");
        assertEquals(BigDecimal.valueOf(1.2), credit.getDiscountRate(), "DiscountRate should be the same as in initialization method");
        assertEquals(BigDecimal.valueOf(100000), credit.getRefinancingAmount(), "RefinancingAmount should be the same as in initialization method");
        assertEquals(refinancingCredits.size(), credit.getRefinancingCredits().size(), "RefinancingCredits should be the same as in initialization method");
    }


}
