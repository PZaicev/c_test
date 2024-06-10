package com.test.task.microservice2.dao;

import com.test.task.microservice2.models.CreditExpIdx;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CreditRepository extends MongoRepository<CreditExpIdx, String> {
}
