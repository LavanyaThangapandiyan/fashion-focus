package com.project.fashion.model;

public class Cart {
	private int id;
	private int orderId;
	private int customerId;
	private int productId;
	private String productName;
	private int price;
	private String size;
	private String product_type;
	private int quantity;
	private int amount;
	private int status;
	public Cart(int id, int orderId, int customerId, int productId, String productName, int price, String size,
			String product_type, int quantity, int amount, int status) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.customerId = customerId;
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.size = size;
		this.product_type = product_type;
		this.quantity = quantity;
		this.amount = amount;
		this.status = status;
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
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
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
	public String getProduct_type() {
		return product_type;
	}
	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Cart() {
		super();
	}
	@Override
	public String toString() {
		return "Cart [id=" + id + ", orderId=" + orderId + ", customerId=" + customerId + ", productId=" + productId
				+ ", productName=" + productName + ", price=" + price + ", size=" + size + ", product_type="
				+ product_type + ", quantity=" + quantity + ", amount=" + amount + ", status=" + status + "]";
	}
	

}