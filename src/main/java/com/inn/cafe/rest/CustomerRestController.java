package com.inn.cafe.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inn.cafe.constants.CafeConstants;
import com.inn.cafe.entities.Customers;
import com.inn.cafe.service.CustomerService;
import com.inn.cafe.util.CafeUtils;

@RestController
@RequestMapping("/customers")
public class CustomerRestController {

	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/add")
	public ResponseEntity<String> addNewCustomer(@RequestBody(required = true) Map<String, String> requestMap){
		try {
			return customerService.addNewCustomer(requestMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/get")
	public ResponseEntity<List<Customers>> getAllCustomers(){
		try {
			return customerService.getAllCustomers();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<Customers>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
