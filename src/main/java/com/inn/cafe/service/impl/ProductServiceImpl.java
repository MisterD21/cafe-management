package com.inn.cafe.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.inn.cafe.constants.CafeConstants;
import com.inn.cafe.entities.Category;
import com.inn.cafe.entities.Product;
import com.inn.cafe.jwt.JwtRequestFilter;
import com.inn.cafe.repositories.CategoryRepository;
import com.inn.cafe.repositories.ProductRepository;
import com.inn.cafe.service.ProductService;
import com.inn.cafe.service.SequenceGeneratorService;
import com.inn.cafe.util.CafeUtils;
import com.inn.cafe.wrapper.ProductWrapper;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
		try {
			if(true||jwtRequestFilter.isAdmin()) {
				if(validateProductMap(requestMap, false)) {
					productRepository.save(getProductMap(requestMap, true));
					
					return CafeUtils.getResponseEntity(CafeConstants.SUCCESS, HttpStatus.OK);
				}
				return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);

			}else {
				return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private boolean validateProductMap(Map<String, String> requestMap, boolean validateId) {
		if(requestMap.containsKey("name")) {
			if(requestMap.containsKey("id") && validateId) {
				return true;
			}else if(!validateId){
				return true;
			}else {
				return false;
			}
		}
		return false;
	}

	private Product getProductMap(Map<String, String> requestMap, boolean isAdd) {
		Product product = new Product();
		Category category = new Category();
		category.setId(Integer.parseInt(requestMap.get("categoryId")));
		if(isAdd) {
			product.setId(sequenceGeneratorService.generateSequence("product_sequence"));
			
		}else {
			Optional<Product> byId = productRepository.findById(Integer.parseInt(requestMap.get("id")));
			if(!byId.isEmpty()) {
				product = byId.get();
			}else {
				return null;
			}
		}
		product.setStatus("TRUE");
		product.setCategory(category);
		product.setName(requestMap.get("name"));
		product.setDescription(requestMap.get("description"));
		product.setPrice(Integer.parseInt(requestMap.get("price")));
		return product;
	}

	@Override
	public ResponseEntity<List<ProductWrapper>> getAllProduct() {
		try {
			return new ResponseEntity<List<ProductWrapper>>(productRepository.getAllProducts(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<ProductWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@Override
	public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
		try {
			if(jwtRequestFilter.isAdmin()) {
				if(validateProductMap(requestMap, true)) {
					Optional<Product> productById = productRepository.findById(Integer.parseInt(requestMap.get("id")));
					Optional<Category> categoryById = categoryRepository.findById(Integer.parseInt(requestMap.get("categoryId")));
					if(!productById.isEmpty() && !categoryById.isEmpty()) {
						productRepository.save(getProductMapForUpdate(requestMap,productById.get(),categoryById.get()));
						return CafeUtils.getResponseEntity("product updated successfully", HttpStatus.OK);

					}else {
						return CafeUtils.getResponseEntity("product or category doesnot exist", HttpStatus.OK);
					}
				}
				return CafeUtils.getResponseEntity("product id doesnot exist", HttpStatus.BAD_REQUEST);

			}else {
				return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	private Product getProductMapForUpdate(Map<String, String> requestMap, Product product, Category category) {
		product.setStatus(requestMap.get("status").toUpperCase());
		product.setCategory(category);
		product.setName(requestMap.get("name"));
		product.setDescription(requestMap.get("description"));
		product.setPrice(Integer.parseInt(requestMap.get("price")));
		return product;
	}

	@Override
	public ResponseEntity<String> deleteProduct(Integer id) {
		try {
			if(jwtRequestFilter.isAdmin()) {
				Optional<Product> productById = productRepository.findById(id);
				if(!productById.isEmpty()) {
					productRepository.delete(productById.get());
					return CafeUtils.getResponseEntity("DATA DELETED SUCCESSFULLY", HttpStatus.OK);
				}else {
					return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
				}
			}else {
				return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@Override
	public ResponseEntity<String> updateProductStatus(Map<String, String> requestMap) {
		try {
			if(jwtRequestFilter.isAdmin()) {
				Optional<Product> productById = productRepository.findById(Integer.parseInt(requestMap.get("id")));
				if(!productById.isEmpty()) {
					Product product = productById.get();
					product.setStatus(requestMap.get("status"));
					productRepository.save(product);
					return CafeUtils.getResponseEntity("STATUS UPDATED SUCCESSFULLY", HttpStatus.OK);
				}else {
					return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
				}
			}else {
				return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@Override
	public ResponseEntity<List<Product>> getByCategoryId(Integer id) {
		try {
			List<Product> productList = new ArrayList<Product>();
			if(jwtRequestFilter.isAdmin()) {
				productList = productRepository.findByCategory_Id(id);
				if(productList.size()>0) {
					return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
				}else {
					return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
				}
			}else {
				return new ResponseEntity<List<Product>>(productList, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<Product>>(new ArrayList<>(), HttpStatus.OK);

	}

	@Override
	public ResponseEntity<Product> getById(Integer id) {
		try {
			if(jwtRequestFilter.isAdmin()) {
				Optional<Product> byId = productRepository.findById(id);
				if(!byId.isEmpty()) {
					return new ResponseEntity<Product>(byId.get(), HttpStatus.OK);
				}else {
					return new ResponseEntity<>(null, HttpStatus.OK);
				}
			}else {
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(null, HttpStatus.OK);

	}

}
