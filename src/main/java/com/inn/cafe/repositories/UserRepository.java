package com.inn.cafe.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.inn.cafe.entities.User;
import com.inn.cafe.wrapper.UserWrapper;

@Repository
public interface UserRepository extends MongoRepository<User, Integer> {

	@Query("{email:?0}")
	public User findByEmailId(String email);
	
	@Query("{role:USER}")
	public List<UserWrapper> getAllUser();

	@Query("{role:ADMIN}")
	public List<UserWrapper> getAllAdmin();
	
}
