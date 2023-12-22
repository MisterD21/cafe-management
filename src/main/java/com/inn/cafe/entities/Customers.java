package com.inn.cafe.entities;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customers")
public class Customers implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Transient
	public static final String SEQUENCE_NAME="customers_sequence";
	
	@Id
	private ObjectId _id;
	private Integer id;
	private String customerName;
	private String customerMobileNo;
	private String totalTimePlayed;
	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	public String getTotalTimePlayed() {
		return totalTimePlayed;
	}
	public void setTotalTimePlayed(String totalTimePlayed) {
		this.totalTimePlayed = totalTimePlayed;
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
	public String getCustomerMobileNo() {
		return customerMobileNo;
	}
	public void setCustomerMobileNo(String customerMobileNo) {
		this.customerMobileNo = customerMobileNo;
	} 
	
	

}
