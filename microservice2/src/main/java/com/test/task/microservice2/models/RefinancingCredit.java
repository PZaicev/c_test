package com.test.task.microservice2.models;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RefinancingCredit {

    private Long orderNum;

    private BigDecimal amount;
}
