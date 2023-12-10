package com.inn.cafe.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.inn.cafe.constants.CafeConstants;
import com.inn.cafe.entities.Category;
import com.inn.cafe.jwt.JwtRequestFilter;
import com.inn.cafe.repositories.CategoryRepository;
import com.inn.cafe.service.CategoryService;
import com.inn.cafe.service.SequenceGeneratorService;
import com.inn.cafe.util.CafeUtils;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
	@Override
	public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
		try {
			if(jwtRequestFilter.isAdmin()) {
				if(validateCategoryMap(requestMap, false)) {
					categoryRepository.save(getCategoryMap(requestMap, true));
					
					return CafeUtils.getResponseEntity(CafeConstants.SUCCESS, HttpStatus.OK);
				}
			}else {
				return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private boolean validateCategoryMap(Map<String, String> requestMap, boolean validateId) {
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
	
	private Category getCategoryMap(Map<String, String> requestMap, Boolean isAdd) {
		Category category = new Category();
		if(isAdd) {
			category.setId(sequenceGeneratorService.generateSequence("category_sequence"));
			
		}else {
			Optional<Category> byId = categoryRepository.findById(Integer.parseInt(requestMap.get("id")));
			if(!byId.isEmpty()) {
				category = byId.get();
			}else {
				return null;
			}
		}
		category.setName(requestMap.get("name"));
		return category;
	}

	@Override
	public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
		try {
			if(!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")) {
				return new ResponseEntity<List<Category>>(categoryRepository.getAllCategory(), HttpStatus.OK);
			}
			return new ResponseEntity<List<Category>>(categoryRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ResponseEntity<List<Category>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
		try {
			if(jwtRequestFilter.isAdmin()) {
				if(validateCategoryMap(requestMap, true)) {
					Optional<Category> categoryById = categoryRepository.findById(Integer.parseInt(requestMap.get("id")));
					if(!categoryById.isEmpty()) {
						categoryRepository.save(getCategoryMap(requestMap, false));
						return CafeUtils.getResponseEntity("category updated successfully", HttpStatus.OK);

					}else {
						return CafeUtils.getResponseEntity("category doesnot exist", HttpStatus.OK);
					}
				}
				return CafeUtils.getResponseEntity("category id doesnot exist", HttpStatus.BAD_REQUEST);

			}else {
				return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
