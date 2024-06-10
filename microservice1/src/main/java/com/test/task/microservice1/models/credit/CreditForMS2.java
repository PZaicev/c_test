package com.test.task.microservice1.models.credit;

import org.hibernate.validator.constraints.UUID;

public class CreditForMS2 extends Credit {

    @UUID
    private String clientId;

    public CreditForMS2() {
    }

    public CreditForMS2(String clientId, Credit credit) {
        this.clientId = clientId;
        this.setOfferId(credit.getOfferId());
        this.setOfferType(credit.getOfferType());
        this.setVerificationRequired(credit.getVerificationRequired());
        this.setProofIncomeRequired(credit.getProofIncomeRequired());
        this.setIdentityDocumentsRequired(credit.getIdentityDocumentsRequired());
        this.setProductSubTypeCode(credit.getProductSubTypeCode());
        this.setTerm(credit.getTerm());
        this.setRate(credit.getRate());
        this.setAmount(credit.getAmount());
        this.setAmountOverLimit(credit.getAmountOverLimit());
        this.setInsurancePremium(credit.getInsurancePremium());
        this.setCommissionAmount(credit.getCommissionAmount());
        this.setDiscountRate(credit.getDiscountRate());
        this.setRefinancingAmount(credit.getRefinancingAmount());
        this.setRefinancingCredits(credit.getRefinancingCredits());
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
