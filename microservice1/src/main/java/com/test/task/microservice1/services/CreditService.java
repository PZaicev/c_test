package com.test.task.microservice1.services;

import com.test.task.microservice1.clients.CreditClient;
import com.test.task.microservice1.models.credit.Credit;
import com.test.task.microservice1.models.credit.CreditForMS2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CreditService {

    private final CreditClient creditClient;

    @Autowired
    public CreditService(CreditClient creditClient) {
        this.creditClient = creditClient;
    }

    public ResponseEntity<Credit> getCredit(String clientId) {
        return creditClient.getCredit(clientId);
    }

    public ResponseEntity createCredit(CreditForMS2 credit) {
        return creditClient.postCredit(credit);
    }
}
