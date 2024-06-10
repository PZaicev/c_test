package com.test.task.microservice1.models.credit.constarints;

import com.test.task.microservice1.models.credit.Credit;
import com.test.task.microservice1.models.credit.OfferType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class InsurancePremiumValidator implements ConstraintValidator<InsurancePremiumConstraint, Credit> {

    @Override
    public void initialize(InsurancePremiumConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(Credit credit, ConstraintValidatorContext constraintValidatorContext) {
        return !OfferType.PREMIUM.equals(credit.getOfferType())
                || null != credit.getInsurancePremium() && BigDecimal.ZERO.compareTo(credit.getInsurancePremium()) <= 0;
    }
}
