package com.inn.cafe.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.inn.cafe.wrapper.CategoriesDto;

public interface CategoryService {

	ResponseEntity<String> addNewCategory(Map<String, String> requestMap);

	ResponseEntity<CategoriesDto> getAllCategory(String filterValue);

	ResponseEntity<String> updateCategory(Map<String, String> requestMap);

	ResponseEntity<String> deleteCategory(Integer id);

}
