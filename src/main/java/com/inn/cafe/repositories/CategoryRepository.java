package com.inn.cafe.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.inn.cafe.entities.Category;

@Repository
public interface CategoryRepository extends MongoRepository<Category, Integer>{

	@Query("{}")
	List<Category> getAllCategory();
}
