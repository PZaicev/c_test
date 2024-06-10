package com.test.task.microservice1.models.credit.constarints;

import com.test.task.microservice1.models.credit.Credit;
import com.test.task.microservice1.models.credit.OfferType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class DiscountRateValidator implements ConstraintValidator<DiscountRateConstraint, Credit> {

    @Override
    public void initialize(DiscountRateConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(Credit credit, ConstraintValidatorContext constraintValidatorContext) {
        return !(OfferType.PREMIUM.equals(credit.getOfferType()) || OfferType.GOLD.equals(credit.getOfferType()))
                || null != credit.getDiscountRate() && BigDecimal.ZERO.compareTo(credit.getDiscountRate()) <= 0;
    }
}
