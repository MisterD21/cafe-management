package com.inn.cafe.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.inn.cafe.entities.Customers;

public interface CustomerService {

	ResponseEntity<String> addNewCustomer(Map<String, String> requestMap);

	ResponseEntity<List<Customers>> getAllCustomers();

}
