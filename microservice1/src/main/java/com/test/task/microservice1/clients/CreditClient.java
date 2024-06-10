package com.test.task.microservice1.clients;

import com.test.task.microservice1.models.credit.Credit;
import com.test.task.microservice1.models.credit.CreditForMS2;
import org.hibernate.validator.constraints.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "${credit-service-client.name}",
             url = "${credit-service-client.domain-url}")
public interface CreditClient {

    @GetMapping(path = "${credit-service-client.get-credit.url}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Credit> getCredit(@RequestHeader("Client-Id") @UUID String clientId);

    @PostMapping(path = "${credit-service-client.post-credit.url}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity postCredit(@RequestBody CreditForMS2 credit);

}
