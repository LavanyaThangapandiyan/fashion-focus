package com.project.fashion.model;

import java.sql.Date;

public class Payment {
	
	
	
	
	
	public Payment() {
		super();
	}
	private int id;
	private int orderId;
	private String userName;
	private String email;
	private int amount;
	private String paymentType;
	private long cardNumber;
	private int cvv;
	private String month;
	private String year;
	private Date date;
	public int getId() {
		return id;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public long getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(long cardNumber) {
		this.cardNumber = cardNumber;
	}
	public int getCvv() {
		return cvv;
	}
	public void setCvv(int cvv) {
		this.cvv = cvv;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Payment(int id, int orderId, String userName, String email, int amount, String paymentType, long cardNumber,
			int cvv, String month, String year, Date date) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.userName = userName;
		this.email = email;
		this.amount = amount;
		this.paymentType = paymentType;
		this.cardNumber = cardNumber;
		this.cvv = cvv;
		this.month = month;
		this.year = year;
		this.date = date;
	}
	@Override
	public String toString() {
		return "Payment [id=" + id + ", orderId=" + orderId + ", userName=" + userName + ", email=" + email
				+ ", amount=" + amount + ", paymentType=" + paymentType + ", cardNumber=" + cardNumber + ", cvv=" + cvv
				+ ", month=" + month + ", year=" + year + ", date=" + date + "]";
	}
}
