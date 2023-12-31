package com.inn.cafe.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inn.cafe.constants.CafeConstants;
import com.inn.cafe.entities.BillEntity;
import com.inn.cafe.service.BillingService;
import com.inn.cafe.util.CafeUtils;

@RestController
@RequestMapping("/bill")
public class BillRestController {

	@Autowired
	private BillingService billingService;
	
	@PostMapping("/add")
	public ResponseEntity<String> addNewBill(@RequestBody(required = true) Map<String, String> requestMap){
		try {
			System.out.println(requestMap);
			return billingService.addNewBill(requestMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/get")
	public ResponseEntity<List<BillEntity>> getAllBills(){
		try {
			return billingService.getAllBills();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<BillEntity>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping("/delete/{id}")
	public ResponseEntity<String> deleteBillById(@PathVariable Integer id){
		try {
			return billingService.deleteBillById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
