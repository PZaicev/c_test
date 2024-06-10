package com.test.task.microservice1.constants;

public class MessagesConstant {

    private MessagesConstant() {
    }

    public static final String INVALID_UUID = "invalid UUID character sequence";
    public static final String NOT_NULL = "must not be null";
    public static final String STRING_PATTERN_Y_OR_N = "must contain only Y or N";
    public static final String OUT_OF_RANGE = "must be in the range from {min} to {max}";
    public static final String NOT_POSITIVE_VALUE = "must be greater than 0";
    public static final String INVALID_DISCOUNT_RATE_VALUE = "discountRate - must be not null and positive number or zero, when offerType = Premium or Gold";
    public static final String INVALID_INSURANCE_PREMIUM_VALUE = "insurancePremium must be not null and positive number or zero, when offerType = Premium";

    public static final String SUCCESS_MESSAGE = "SUCCESS";
    public static final String FAILURE_MESSAGE = "FAILURE";
    public static final String FAILURE_VALIDATION_CODE_MESSAGE = "Validation Exception";
    public static final String FAILURE_VALIDATION_DESCRIPTION_MESSAGE = "Correct your request regarding error messages stored in data.errors field and try again";
    public static final String INTERNAL_SERVER_ERROR_CODE_MESSAGE = "Internal Server Error";
    public static final String INTERNAL_SERVER_ERROR_DESCRIPTION_MESSAGE = "Internal Server Error has occurred, check list of errors";



    public static final String CREDIT_MESSAGES_CODE = "selectionOffer";
    public static final String CREDIT_MESSAGES_DESCRIPTION = "предложение по кредиту";



}
