package com.test.task.microservice2.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Data
@Document(collection = "${spring.data.mongodb.collection}")
@NoArgsConstructor
public class Credit {

    @Id
    private String clientId;

    private String offerId;

    private String offerType;

    private Boolean verificationRequired;

    private String proofIncomeRequired;

    private Boolean identityDocumentsRequired;

    private String productSubTypeCode;

    private Integer term;

    private BigDecimal rate;

    private BigDecimal amount;

    private BigDecimal amountOverLimit;

    private BigDecimal insurancePremium;

    private BigDecimal commissionAmount;

    private BigDecimal discountRate;

    private BigDecimal refinancingAmount;

    private List<RefinancingCredit> refinancingCredits;
}
