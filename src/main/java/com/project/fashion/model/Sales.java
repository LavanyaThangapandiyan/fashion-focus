package com.project.fashion.model;

import java.sql.Date;

public class Sales {
	
	
	public Sales() {
		super();
	}
	private int id;
	private String categoryName;
	private int productId;
	private int quantity;
	private Date date;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "Sales [id=" + id + ", categoryName=" + categoryName + ", productId=" + productId + ", quantity="
				+ quantity + ", date=" + date + "]";
	}
	public Sales(int id, String categoryName, int productId, int quantity, Date date) {
		super();
		this.id = id;
		this.categoryName = categoryName;
		this.productId = productId;
		this.quantity = quantity;
		this.date = date;
	}

}
