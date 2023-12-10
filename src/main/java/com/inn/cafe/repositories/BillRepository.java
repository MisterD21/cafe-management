package com.inn.cafe.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.inn.cafe.entities.Bill;

@Repository
public interface BillRepository extends MongoRepository<Bill, Integer> {

}
