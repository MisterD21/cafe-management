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
import com.inn.cafe.entities.Customers;
import com.inn.cafe.repositories.CustomerRepository;
import com.inn.cafe.service.CustomerService;
import com.inn.cafe.service.SequenceGeneratorService;
import com.inn.cafe.util.CafeUtils;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;

	@Override
	public ResponseEntity<String> addNewCustomer(Map<String, String> requestMap) {
		try {

			customerRepository.save(getCustomerMap(requestMap, true));

			return CafeUtils.getResponseEntity(CafeConstants.SUCCESS, HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private Customers getCustomerMap(Map<String, String> requestMap, boolean isAdd) {
		Customers customers = new Customers();
		if (isAdd) {
			customers.setId(sequenceGeneratorService.generateSequence("customers_sequence"));

		} else {
//			Optional<Customers> byId = customerRepository.findById(requestMap.get("id"));
//			if (!byId.isEmpty()) {
//				customers = byId.get();
//			} else {
//				return null;
//			}
		}
		customers.setCustomerName(requestMap.get("customerName"));
		customers.setCustomerMobileNo(requestMap.get("customerMobileNo"));
		customers.setTotalTimePlayed("30");
		return customers;
	}

	@Override
	public ResponseEntity<List<Customers>> getAllCustomers() {
		try {
			List<Customers> allCustomers = customerRepository.getAllCustomers();
			return new ResponseEntity<List<Customers>>(allCustomers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<Customers>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
