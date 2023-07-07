package com.project.fashion.model;

public class Order {
	private int orderId;
	private int customerId;
	private int productId;
	private String productName;
	private int price;
	private String size;
	private String category;
	private int quantity;
	private String status;
	
	

	public Order(int orderId, int customerId, int productId, String productName, int price, String size,
			String category, int quantity, String status) {
		super();
		this.orderId = orderId;
		this.customerId = customerId;
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.size = size;
		this.category = category;
		this.quantity = quantity;
		this.status = status;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Order() {
		super();
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", customerId=" + customerId + ", productId=" + productId
				+ ", productName=" + productName + ", price=" + price + ", size=" + size + ", category=" + category
				+ ", quantity=" + quantity + ", status=" + status + "]";
	}

	
}
