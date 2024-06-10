package com.test.task.microservice1.models.credit;

import com.test.task.microservice1.constants.MessagesConstant;

import com.test.task.microservice1.models.credit.constarints.DiscountRateConstraint;
import com.test.task.microservice1.models.credit.constarints.InsurancePremiumConstraint;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.UUID;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@InsurancePremiumConstraint
@DiscountRateConstraint
public class Credit {

    @UUID(message = MessagesConstant.INVALID_UUID)
    @NotNull(message = MessagesConstant.NOT_NULL)
    private String offerId;

    @NotNull(message = MessagesConstant.NOT_NULL)
    private OfferType offerType;

    @NotNull(message = MessagesConstant.NOT_NULL)
    private Boolean verificationRequired;

    @NotNull(message = MessagesConstant.NOT_NULL)
    @Pattern(regexp = "^[YN]$", message = MessagesConstant.STRING_PATTERN_Y_OR_N)
    private String proofIncomeRequired;

    private Boolean identityDocumentsRequired;

    private String productSubTypeCode;

    @NotNull(message = MessagesConstant.NOT_NULL)
    @Range(min = 1, max = 12, message = MessagesConstant.OUT_OF_RANGE)
    private Integer term;

    @NotNull(message = MessagesConstant.NOT_NULL)
    @Positive(message = MessagesConstant.NOT_POSITIVE_VALUE)
    private BigDecimal rate;

    @NotNull(message =  MessagesConstant.NOT_NULL)
    @Positive(message = MessagesConstant.NOT_POSITIVE_VALUE)
    private BigDecimal amount;

    @NotNull(message =  MessagesConstant.NOT_NULL)
    @Positive(message = MessagesConstant.NOT_POSITIVE_VALUE)
    private BigDecimal amountOverLimit;

    //checked by @InsurancePremiumConstraint, this field is required if offerType = PREMIUM
    private BigDecimal insurancePremium;

    @NotNull(message =  MessagesConstant.NOT_NULL)
    @Positive(message = MessagesConstant.NOT_POSITIVE_VALUE)
    private BigDecimal commissionAmount;

    //checked by @DiscountRateConstraint, this field is required if offerType = GOLD or PREMIUM
    private BigDecimal discountRate;

    private BigDecimal refinancingAmount;

    @Valid
    private List<RefinancingCredit> refinancingCredits;

    public Credit() {
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public OfferType getOfferType() {
        return offerType;
    }

    public void setOfferType(OfferType offerType) {
        this.offerType = offerType;
    }

    public Boolean getVerificationRequired() {
        return verificationRequired;
    }

    public void setVerificationRequired(Boolean verificationRequired) {
        this.verificationRequired = verificationRequired;
    }

    public String getProofIncomeRequired() {
        return proofIncomeRequired;
    }

    public void setProofIncomeRequired(String proofIncomeRequired) {
        this.proofIncomeRequired = proofIncomeRequired;
    }

    public Boolean getIdentityDocumentsRequired() {
        return identityDocumentsRequired;
    }

    public void setIdentityDocumentsRequired(Boolean identityDocumentsRequired) {
        this.identityDocumentsRequired = identityDocumentsRequired;
    }

    public String getProductSubTypeCode() {
        return productSubTypeCode;
    }

    public void setProductSubTypeCode(String productSubTypeCode) {
        this.productSubTypeCode = productSubTypeCode;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmountOverLimit() {
        return amountOverLimit;
    }

    public void setAmountOverLimit(BigDecimal amountOverLimit) {
        this.amountOverLimit = amountOverLimit;
    }

    public BigDecimal getInsurancePremium() {
        return insurancePremium;
    }

    public void setInsurancePremium(BigDecimal insurancePremium) {
        this.insurancePremium = insurancePremium;
    }

    public BigDecimal getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(BigDecimal commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    public BigDecimal getRefinancingAmount() {
        return refinancingAmount;
    }

    public void setRefinancingAmount(BigDecimal refinancingAmount) {
        this.refinancingAmount = refinancingAmount;
    }

    public List<RefinancingCredit> getRefinancingCredits() {
        return refinancingCredits;
    }

    public void setRefinancingCredits(List<RefinancingCredit> refinancingCredit) {
        this.refinancingCredits = refinancingCredit;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "offerId='" + offerId + '\'' +
                ", offerType=" + offerType +
                ", verificationRequired=" + verificationRequired +
                ", proofIncomeRequired='" + proofIncomeRequired + '\'' +
                ", identityDocumentsRequired=" + identityDocumentsRequired +
                ", productSubTypeCode='" + productSubTypeCode + '\'' +
                ", term=" + term +
                ", rate=" + rate +
                ", amount=" + amount +
                ", amountOverLimit=" + amountOverLimit +
                ", insurancePremium=" + insurancePremium +
                ", commissionAmount=" + commissionAmount +
                ", discountRate=" + discountRate +
                ", refinancingAmount=" + refinancingAmount +
                ", refinancingCredits=" + refinancingCredits.stream().map(Object::toString).collect(Collectors.joining(", ")) +
                '}';
    }
}
