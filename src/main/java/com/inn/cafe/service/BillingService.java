package com.inn.cafe.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.inn.cafe.entities.BillEntity;

public interface BillingService {

	ResponseEntity<String> addNewBill(Map<String, String> requestMap);

	ResponseEntity<List<BillEntity>> getAllBills();

	ResponseEntity<String> deleteBillById(Integer id);

}
