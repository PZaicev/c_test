package com.test.task.microservice1.models.credit.constarints;

import com.test.task.microservice1.constants.MessagesConstant;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = InsurancePremiumValidator.class)
@Target( {ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface InsurancePremiumConstraint {

    String message() default MessagesConstant.INVALID_INSURANCE_PREMIUM_VALUE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
