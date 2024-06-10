package com.test.task.microservice2.controllers;

import com.test.task.microservice2.dao.CreditRepository;
import com.test.task.microservice2.loggers.LogControllerMethod;
import com.test.task.microservice2.models.Credit;
import com.test.task.microservice2.models.CreditExpIdx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/offers")
public class OfferController {

    @Autowired
    private CreditRepository creditRepository;

    @LogControllerMethod
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity postCredit(@RequestBody CreditExpIdx credit) {
        creditRepository.save(credit);

        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

    @LogControllerMethod
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Credit> getCredit(@RequestHeader("Client-Id") String clientId) {
        CreditExpIdx creditExpIdx = creditRepository.findById(clientId).orElse(null);

        Credit credit = null != creditExpIdx ? creditExpIdx.getCredit() : null;

        return ResponseEntity.status(HttpStatus.OK).body(credit);
    }

}
