package com.inn.cafe.entities;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bills")
public class BillEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Transient
	public static final String SEQUENCE_NAME="bills_sequence";
	
	@Id
	private Integer id;
	//private String uuid;
	private String customerName;
	private String customerMobileNo;
	private int categoryId;
	private int productId;
	private int timePlayedInMins;
	private int quantity;
	private Float finalPrice;
	private String paymentMode;
	private Date createdOn;
	public String getCustomerMobileNo() {
		return customerMobileNo;
	}
	public void setCustomerMobileNo(String customerMobileNo) {
		this.customerMobileNo = customerMobileNo;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getTimePlayedInMins() {
		return timePlayedInMins;
	}
	public void setTimePlayedInMins(int timePlayedInMins) {
		this.timePlayedInMins = timePlayedInMins;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Float getFinalPrice() {
		return finalPrice;
	}
	public void setFinalPrice(Float finalPrice) {
		this.finalPrice = finalPrice;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	
	
}
