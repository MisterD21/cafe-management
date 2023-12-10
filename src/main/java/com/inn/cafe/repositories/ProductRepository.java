package com.inn.cafe.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.inn.cafe.entities.Product;
import com.inn.cafe.wrapper.ProductWrapper;

@Repository
public interface ProductRepository extends MongoRepository<Product, Integer> {

	@Query("{}")
	List<ProductWrapper> getAllProducts();
	
	@Query("{category.$id:?0}")
	List<Product> getProductByCategory(Integer id);
	
    List<Product> findByCategory_Id(Integer categoryId);


}
