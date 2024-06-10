package com.test.task.microservice1.models;

import com.test.task.microservice1.models.credit.Credit;
import com.test.task.microservice1.models.credit.CreditForMS2;
import com.test.task.microservice1.models.credit.OfferType;
import com.test.task.microservice1.models.credit.RefinancingCredit;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditForMS2Test {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private Credit credit;
    private CreditForMS2 creditForMS2;
    private List<RefinancingCredit> refinancingCredits;

    @BeforeEach
    public void initialize(){
        refinancingCredits = List.of(new RefinancingCredit(2L, BigDecimal.valueOf(100)));

        credit = CreditHelper.getValidCreditWithRefinancingCredits(refinancingCredits);

        creditForMS2 = new CreditForMS2();
        creditForMS2.setClientId(CreditHelper.getValidClientId());
    }

    @Test
    public void checkCreditForMC2AfterInitializationTest(){
        assertEquals(CreditHelper.getValidClientId(), creditForMS2.getClientId(), "ClientId should be the same as in initialization method");
    }

    @Test
    public void checkCreditForMC2AfterConstrInitializationTest() {
        creditForMS2 = new CreditForMS2(CreditHelper.getValidClientId(), credit);

        assertEquals(CreditHelper.getValidClientId(), creditForMS2.getClientId(), "ClientId should be the same as in initialization method");
        assertEquals("123e4567-e89b-12d3-a456-426655440000", creditForMS2.getOfferId(), "OfferId should be the same as in initialization method");
        assertEquals(OfferType.STANDARD, creditForMS2.getOfferType(), "OfferType should be the same as in initialization method");
        assertEquals(true, creditForMS2.getVerificationRequired(), "VerificationRequired should be the same as in initialization method");
        assertEquals("N", creditForMS2.getProofIncomeRequired(), "ProofIncomeRequired should be the same as in initialization method");
        assertEquals(false, creditForMS2.getIdentityDocumentsRequired(), "IdentityDocumentsRequired should be the same as in initialization method");
        assertEquals("subtype", creditForMS2.getProductSubTypeCode(), "ProductSubTypeCode should be the same as in initialization method");
        assertEquals(1, creditForMS2.getTerm(), "Term should be the same as in initialization method");
        assertEquals(BigDecimal.valueOf(12.5), creditForMS2.getRate(), "Rate should be the same as in initialization method");
        assertEquals(BigDecimal.valueOf(500000), creditForMS2.getAmount(), "Amount should be the same as in initialization method");
        assertEquals(BigDecimal.valueOf(450000), creditForMS2.getAmountOverLimit(), "AmountOverLimit should be the same as in initialization method");
        assertEquals(BigDecimal.valueOf(1), creditForMS2.getInsurancePremium(), "InsurancePremium should be the same as in initialization method");
        assertEquals(BigDecimal.valueOf(5000), creditForMS2.getCommissionAmount(), "CommissionAmount should be the same as in initialization method");
        assertEquals(BigDecimal.valueOf(1.2), creditForMS2.getDiscountRate(), "DiscountRate should be the same as in initialization method");
        assertEquals(BigDecimal.valueOf(100000), creditForMS2.getRefinancingAmount(), "RefinancingAmount should be the same as in initialization method");
        assertEquals(refinancingCredits, creditForMS2.getRefinancingCredits(), "RefinancingCredits should be the same as in initialization method");
    }
}
