package com.test.task.microservice1.models;

import com.test.task.microservice1.models.credit.Credit;
import com.test.task.microservice1.models.credit.OfferType;
import com.test.task.microservice1.models.credit.RefinancingCredit;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CreditTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private Credit credit;
    private List<RefinancingCredit> refinancingCredits;

    @BeforeEach
    public void initialize(){
        refinancingCredits = List.of(new RefinancingCredit(2L, BigDecimal.valueOf(100)));

        credit = CreditHelper.getValidCreditWithRefinancingCredits(refinancingCredits);
    }

    @Test
    public void checkCreditAfterInitializationTest(){
        Set<ConstraintViolation<Credit>> violations = validator.validate(credit);
        Set<ConstraintViolation<List<RefinancingCredit>>> violationsRefCredit = validator.validate(refinancingCredits);

        assertTrue(violations.isEmpty(), "There should be no violations in Credit object after initialization");
        assertTrue(violationsRefCredit.isEmpty(), "There should be no violations in Credit object after initialization");

        assertEquals("123e4567-e89b-12d3-a456-426655440000", credit.getOfferId(), "OfferId should be the same as in initialization method");
        assertEquals(OfferType.STANDARD, credit.getOfferType(), "OfferType should be the same as in initialization method");
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
        assertEquals(refinancingCredits, credit.getRefinancingCredits(), "RefinancingCredits should be the same as in initialization method");
    }

    @Test
    public void brokenIfOfferIdIsNotUUIDTest(){
        credit.setOfferId("1234");
        Set<ConstraintViolation<Credit>> violationSet = validator.validate(credit);

        credit.setOfferId(null);
        Set<ConstraintViolation<Credit>> violationSetWithNullValue = validator.validate(credit);

        assertFalse(violationSet.isEmpty(), "OfferId should accept only valid UUID string");
        assertFalse(violationSetWithNullValue.isEmpty(), "OfferId should accept only valid UUID string");
    }

    @Test
    public void brokenIfOfferTypeIsNotInOfferTypeEnumTest(){
        String notExistOfferType = "PLATINUM";
        assertThrows(IllegalArgumentException.class, ()-> credit.setOfferType(OfferType.valueOf(notExistOfferType)),
                "OfferType should accept only existing enum values from OfferType enum");
    }

    @Test
    public void brokenIfVerificationRequiredIsNullTest(){
        credit.setVerificationRequired(null);
        Set<ConstraintViolation<Credit>> violationSet = validator.validate(credit);

        assertFalse(violationSet.isEmpty(), "VerificationRequired should accept only valid Boolean value");
    }

    @Test
    public void brokenIfProfIncomeRequiredNotYorNTest(){
        credit.setProofIncomeRequired("yes");
        Set<ConstraintViolation<Credit>> violationSet = validator.validate(credit);

        credit.setProofIncomeRequired("");
        Set<ConstraintViolation<Credit>> violationSetEmptyStr = validator.validate(credit);

        credit.setProofIncomeRequired(null);
        Set<ConstraintViolation<Credit>> violationSetWithNull = validator.validate(credit);

        assertFalse(violationSet.isEmpty(), "ProfIncomeRequired should accept only Y or N string values");
        assertFalse(violationSetEmptyStr.isEmpty(), "ProfIncomeRequired should accept only Y or N string values");
        assertFalse(violationSetWithNull.isEmpty(), "ProfIncomeRequired should accept only Y or N string values");
    }

    @Test
    public void brokenIfTermNotBetween1And12Test(){
        credit.setTerm(0);
        Set<ConstraintViolation<Credit>> violationSetWithTermLessThan1 = validator.validate(credit);

        credit.setTerm(13);
        Set<ConstraintViolation<Credit>> violationSetWithTermMoreThan12 = validator.validate(credit);

        assertFalse(violationSetWithTermLessThan1.isEmpty(), "Term should accept only values in range from 1 to 12");
        assertFalse(violationSetWithTermMoreThan12.isEmpty(), "Term should accept only values in range from 1 to 12");
    }

    @Test
    public void brokenIfRateZeroOrNegativeValueTest(){
        credit.setRate(BigDecimal.valueOf(0));
        Set<ConstraintViolation<Credit>> violationSetWithZero = validator.validate(credit);

        credit.setRate(BigDecimal.valueOf(-1));
        Set<ConstraintViolation<Credit>> violationSetWithMinus = validator.validate(credit);

        assertFalse(violationSetWithZero.isEmpty(), "Rate should accept only positive values");
        assertFalse(violationSetWithMinus.isEmpty(), "Rate should accept only positive values");
    }

    @Test
    public void brokenIfAmountZeroOrNegativeValueTest(){
        credit.setAmount(BigDecimal.valueOf(0));
        Set<ConstraintViolation<Credit>> violationSetWithZero = validator.validate(credit);

        credit.setAmount(BigDecimal.valueOf(-1));
        Set<ConstraintViolation<Credit>> violationSetWithMinus = validator.validate(credit);

        assertFalse(violationSetWithZero.isEmpty(), "Amount should accept only positive values");
        assertFalse(violationSetWithMinus.isEmpty(), "Amount should accept only positive values");
    }

    @Test
    public void brokenIfAmountOverLimitZeroOrNegativeValueTest(){
        credit.setAmountOverLimit(BigDecimal.valueOf(0));
        Set<ConstraintViolation<Credit>> violationSetWithZero = validator.validate(credit);

        credit.setAmountOverLimit(BigDecimal.valueOf(-1));
        Set<ConstraintViolation<Credit>> violationSetWithMinus = validator.validate(credit);


        assertFalse(violationSetWithZero.isEmpty(), "Amount should accept only positive values");
        assertFalse(violationSetWithMinus.isEmpty(), "Amount should accept only positive values");
    }

    @Test
    public void brokenIfOfferTypeEqPremiumAndInsurancePremiumIsNegativeValueTest(){
        credit.setOfferType(OfferType.PREMIUM);
        credit.setInsurancePremium(BigDecimal.valueOf(-1));
        Set<ConstraintViolation<Credit>> violationSetWithMinus = validator.validate(credit);

        credit.setInsurancePremium(null);
        Set<ConstraintViolation<Credit>> violationSetWithNull = validator.validate(credit);

        assertFalse(violationSetWithMinus.isEmpty(), "InsurancePremium should be 0 or positive value when OfferType = PREMIUM");
        assertFalse(violationSetWithNull.isEmpty(), "InsurancePremium should be 0 or positive value when OfferType = PREMIUM");
    }

    @Test
    public void brokenIfCommissionAmountZeroOrNegativeValueTest(){
        credit.setCommissionAmount(BigDecimal.valueOf(0));
        Set<ConstraintViolation<Credit>> violationSetWithZero = validator.validate(credit);

        credit.setCommissionAmount(BigDecimal.valueOf(-1));
        Set<ConstraintViolation<Credit>> violationSetWithMinus = validator.validate(credit);

        assertFalse(violationSetWithZero.isEmpty(), "Amount should accept only positive values");
        assertFalse(violationSetWithMinus.isEmpty(), "Amount should accept only positive values");
    }

    @Test
    public void brokenIfOfferTypeEqPremiumOrGoldAndDiscountRateIsNegativeValueTest(){
        credit.setOfferType(OfferType.GOLD);
        credit.setDiscountRate(BigDecimal.valueOf(-1));
        Set<ConstraintViolation<Credit>> violationSetWithGoldType = validator.validate(credit);

        credit.setOfferType(OfferType.PREMIUM);
        credit.setDiscountRate(null);
        Set<ConstraintViolation<Credit>> violationSetWithPremiumType = validator.validate(credit);

        assertFalse(violationSetWithGoldType.isEmpty(), "DiscountRate should be 0 or positive value when OfferType = GOLD");
        assertFalse(violationSetWithPremiumType.isEmpty(), "DiscountRate should be 0 or positive value when OfferType = PREMIUM");
    }

    @Test
    public void brokenIfRefinancingCreditListContainNotValidValuesTest(){
        RefinancingCredit refinancingCredit1 = new RefinancingCredit(1L, BigDecimal.valueOf(1));
        RefinancingCredit refinancingCredit2 = new RefinancingCredit(2L, BigDecimal.valueOf(-3));
        credit.setRefinancingCredits(List.of(refinancingCredit1, refinancingCredit2));
        Set<ConstraintViolation<Credit>> violationSetWithGoldType = validator.validate(credit);

        assertFalse(violationSetWithGoldType.isEmpty(), "RefinancingCredits should contain only valid RefinancingCredit records");
    }
}
