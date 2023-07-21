package com.project.fashion.model;

import java.sql.Date;

public class Payment {
	
	private int id;
	private int orderId;
	private int amount;
	private String paymentType;
	private Date date;
	
	
	
	public Payment() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "Payment [id=" + id + ", orderId=" + orderId + ", amount=" + amount + ", paymentType=" + paymentType
				+ ", date=" + date + "]";
	}
	public Payment(int id, int orderId, int amount, String paymentType, Date date) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.amount = amount;
		this.paymentType = paymentType;
		this.date = date;
	}
	

}
