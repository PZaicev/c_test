package com.test.task.microservice1.models;

import com.test.task.microservice1.models.credit.Credit;
import com.test.task.microservice1.models.credit.OfferType;
import com.test.task.microservice1.models.credit.RefinancingCredit;

import java.math.BigDecimal;
import java.util.List;

public class CreditHelper {

    private static String validClientId = "123e4567-e89b-12d3-a456-426655440000";

    private CreditHelper() {
    }

    public static Credit getValidCredit(){
        Credit credit = new Credit();
        credit.setOfferId("123e4567-e89b-12d3-a456-426655440000");
        credit.setOfferType(OfferType.valueOf("STANDARD"));
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

        return credit;
    }

    public static Credit getValidCreditWithRefinancingCredits(List<RefinancingCredit> refinancingCreditList) {
        Credit credit = getValidCredit();
        credit.setRefinancingCredits(refinancingCreditList);

        return credit;
    }

    public static String getValidClientId(){
        return validClientId;
    }
}
