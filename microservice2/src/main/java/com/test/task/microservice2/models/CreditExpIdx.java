package com.test.task.microservice2.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class CreditExpIdx extends Credit {

    @Indexed(expireAfterSeconds = 1800)
    private LocalDateTime expiredOn = LocalDateTime.now();

    public Credit getCredit(){
        Credit credit = new Credit();
        credit.setClientId(this.getClientId());
        credit.setOfferId(this.getOfferId());
        credit.setOfferType(this.getOfferType());
        credit.setVerificationRequired(this.getVerificationRequired());
        credit.setProofIncomeRequired(this.getProofIncomeRequired());
        credit.setIdentityDocumentsRequired(this.getIdentityDocumentsRequired());
        credit.setProductSubTypeCode(this.getProductSubTypeCode());
        credit.setTerm(this.getTerm());
        credit.setRate(this.getRate());
        credit.setAmount(this.getAmount());
        credit.setAmountOverLimit(this.getAmountOverLimit());
        credit.setInsurancePremium(this.getInsurancePremium());
        credit.setCommissionAmount(this.getCommissionAmount());
        credit.setDiscountRate(this.getDiscountRate());
        credit.setRefinancingAmount(this.getRefinancingAmount());
        credit.setRefinancingCredits(this.getRefinancingCredits());

        return credit;
    }
}
