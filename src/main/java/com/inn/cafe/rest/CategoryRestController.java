package com.inn.cafe.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inn.cafe.constants.CafeConstants;
import com.inn.cafe.service.CategoryService;
import com.inn.cafe.util.CafeUtils;
import com.inn.cafe.wrapper.CategoriesDto;

@RestController
@RequestMapping("/category")
public class CategoryRestController {

	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/add")
	public ResponseEntity<String> addCategory(@RequestBody(required = true) Map<String, String> requestMap){
		try {
			return categoryService.addNewCategory(requestMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/get")
	public ResponseEntity<CategoriesDto> getAllCategory(@RequestParam(required = false) String filterValue){
		try {
			return categoryService.getAllCategory(filterValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<CategoriesDto>(new CategoriesDto(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping("/update")
	public ResponseEntity<String> updateCategory(@RequestBody(required = true) Map<String, String> requestMap){
		try {
			return categoryService.updateCategory(requestMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping("/delete/{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable Integer id){
		try {
			return categoryService.deleteCategory(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
