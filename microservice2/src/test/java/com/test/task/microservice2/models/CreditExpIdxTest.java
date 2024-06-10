package com.test.task.microservice2.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreditExpIdxTest {

    @Test
    public void expiredOnFieldShouldBeInitializedAfterCreationTest(){
        CreditExpIdx creditExpIdx = new CreditExpIdx();

        assertNotNull(creditExpIdx.getExpiredOn(), "expiredOn should not be null after creation");
    }
}
