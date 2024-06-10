package com.test.task.microservice2.models;

import java.math.BigDecimal;

public class CreditHelper {

    private CreditHelper() {
    }

    public static Credit getValidCredit(){
        Credit credit = new Credit();
        setValidValues(credit);
        return credit;
    }

    public static CreditExpIdx getValidCreditExpIdx(){
        CreditExpIdx creditExpIdx = new CreditExpIdx();
        setValidValues(creditExpIdx);
        return creditExpIdx;
    }

    private static void setValidValues(Credit credit){
        credit.setClientId("123e4567-e89b-12d3-a456-426655440001");
        credit.setOfferId("123e4567-e89b-12d3-a456-426655440000");
        credit.setOfferType("STANDARD");
        credit.setVerificationRequired(true);
        credit.setProofIncomeRequired("N");
        credit.setIdentityDocumentsRequired(false);
        credit.setProductSubTypeCode("subtype");
        credit.setTerm(1);
        credit.setRate(BigDecimal.valueOf(12.5));
        credit.setAmount(BigDecimal.valueOf(500000));
        credit.setAmountOverLimit(BigDecimal.valueOf(450000));
        credit.setInsurancePremium(BigDecimal.valueOf(1));
        credit.setCommissionAmount(BigDecimal.valueOf(5000));
        credit.setDiscountRate(BigDecimal.valueOf(1.2));
        credit.setRefinancingAmount(BigDecimal.valueOf(100000));
    }
}
