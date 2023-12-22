package com.inn.cafe.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.inn.cafe.constants.CafeConstants;
import com.inn.cafe.entities.BillEntity;
import com.inn.cafe.entities.Category;
import com.inn.cafe.jwt.JwtRequestFilter;
import com.inn.cafe.repositories.BillingRepository;
import com.inn.cafe.service.BillingService;
import com.inn.cafe.service.SequenceGeneratorService;
import com.inn.cafe.util.CafeUtils;

@Service
public class BillingServiceImpl implements BillingService {

	@Autowired
	private BillingRepository billingRepository;

	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Override
	public ResponseEntity<String> addNewBill(Map<String, String> requestMap) {
		try {

			billingRepository.save(getBillsMap(requestMap, true));

			return CafeUtils.getResponseEntity(CafeConstants.SUCCESS, HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private BillEntity getBillsMap(Map<String, String> requestMap, boolean isAdd) {
		BillEntity bills = new BillEntity();
		Category category = new Category();
		category.setId(Integer.parseInt(requestMap.get("categoryId")));
		if (isAdd) {
			bills.setId(sequenceGeneratorService.generateSequence("bills_sequence"));

		} else {
			Optional<BillEntity> byId = billingRepository.findById(Integer.parseInt(requestMap.get("id")));
			if (!byId.isEmpty()) {
				bills = byId.get();
			} else {
				return null;
			}
		}
		if (requestMap.get("categoryId") != null && !requestMap.get("categoryId").isEmpty()) {
			bills.setCategoryId(Integer.parseInt(requestMap.get("categoryId")));
		}
		if (requestMap.get("productId") != null && !requestMap.get("productId").isEmpty()) {
			bills.setProductId(Integer.parseInt(requestMap.get("productId")));
		}

		bills.setCreatedOn(new Timestamp(System.currentTimeMillis()));

		if (requestMap.get("customerName") != null && !requestMap.get("customerName").isEmpty()) {
			bills.setCustomerName(requestMap.get("customerName"));
		}
		if (requestMap.get("finalPrice") != null && !requestMap.get("finalPrice").isEmpty()) {
			bills.setFinalPrice(Float.parseFloat(requestMap.get("finalPrice")));
		}
		if (requestMap.get("paymentMode") != null && !requestMap.get("paymentMode").isEmpty()) {
			bills.setPaymentMode(requestMap.get("paymentMode"));
		}
		if (requestMap.get("quantity") != null && !requestMap.get("quantity").isEmpty()) {
			bills.setQuantity(Integer.parseInt(requestMap.get("quantity")));
		}
		if (requestMap.get("timePlayedInMins") != null && !requestMap.get("timePlayedInMins").isEmpty()) {
			bills.setTimePlayedInMins(Integer.parseInt(requestMap.get("timePlayedInMins")));
		}
		if (requestMap.get("mobileNumber") != null && !requestMap.get("mobileNumber").isEmpty()) {
			bills.setCustomerMobileNo(requestMap.get("mobileNumber"));
		}

		return bills;
	}

	@Override
	public ResponseEntity<List<BillEntity>> getAllBills() {
		try {
			return new ResponseEntity<List<BillEntity>>(billingRepository.getAllBills(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<BillEntity>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@Override
	public ResponseEntity<String> deleteBillById(Integer id) {
		try {
			if(jwtRequestFilter.isAdmin()) {
				Optional<BillEntity> billsById = billingRepository.findById(id);
				if(!billsById.isEmpty()) {
					billingRepository.delete(billsById.get());
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
}
