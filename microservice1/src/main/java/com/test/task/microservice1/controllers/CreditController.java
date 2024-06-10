package com.test.task.microservice1.controllers;

import com.test.task.microservice1.constants.MessagesConstant;
import com.test.task.microservice1.loggers.LogControllerMethod;
import com.test.task.microservice1.models.credit.Credit;
import com.test.task.microservice1.models.credit.CreditForMS2;
import com.test.task.microservice1.models.responses.ResponseBody;
import com.test.task.microservice1.services.CreditService;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/credits")
public class CreditController {

    @Autowired
    private CreditService creditService;

    @LogControllerMethod
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCredit(@RequestHeader("Client-Id") @UUID(message = MessagesConstant.INVALID_UUID) String clientId) {
        ResponseEntity<Credit> creditResponse = creditService.getCredit(clientId);

        return ResponseEntity.status(creditResponse.getStatusCode()).body(
                new ResponseBody<Credit>()
                        .createResponse(null != creditResponse.getBody(),
                                MessagesConstant.CREDIT_MESSAGES_CODE,
                                MessagesConstant.CREDIT_MESSAGES_DESCRIPTION,
                                creditResponse.getBody()
                        )
        );
    }

    @LogControllerMethod
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity setCredit(@RequestHeader("Client-Id") @UUID(message = MessagesConstant.INVALID_UUID) String clientId,
                                    @RequestBody @Valid Credit credit){

        ResponseEntity response =  creditService.createCredit(new CreditForMS2(clientId, credit));

        return ResponseEntity.status(response.getStatusCode()).build();
    }
}
