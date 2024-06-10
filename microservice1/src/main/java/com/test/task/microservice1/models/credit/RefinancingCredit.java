package com.test.task.microservice1.models.credit;

import com.test.task.microservice1.constants.MessagesConstant;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class RefinancingCredit {

    @NotNull(message = MessagesConstant.NOT_NULL)
    @Positive
    private Long orderNum;

    @NotNull(message = MessagesConstant.NOT_NULL)
    @Positive
    private BigDecimal amount;

    public RefinancingCredit() {
    }

    public RefinancingCredit(Long orderNum, BigDecimal amount) {
        this.orderNum = orderNum;
        this.amount = amount;
    }

    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "RefinancingCredit{" +
                "orderNum=" + orderNum +
                ", amount=" + amount +
                '}';
    }
}