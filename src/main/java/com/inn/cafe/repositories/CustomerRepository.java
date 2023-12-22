package com.inn.cafe.repositories;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.inn.cafe.entities.Customers;

@Repository
public interface CustomerRepository extends MongoRepository<Customers, ObjectId> {

	@Query("{}")
	List<Customers> getAllCustomers();

}
