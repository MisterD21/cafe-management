package com.inn.cafe.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.inn.cafe.entities.BillEntity;

@Repository
public interface BillingRepository extends MongoRepository<BillEntity, Integer> {

	@Query("{}")
	List<BillEntity> getAllBills();

}
