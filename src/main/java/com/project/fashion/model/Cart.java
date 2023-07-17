package com.project.fashion.model;

public class Cart {
	private String image;
	private int id;
	private int orderId;
	private int customerId;
	private int productId;
	private String productName;
	private int price;
	private String size;
	private String product_type;
	private String fabric;
	private String gender;
	private int quantity;
	private int amount;
	private String status;
	
	
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getFabric() {
		return fabric;
	}
	public void setFabric(String fabric) {
		this.fabric = fabric;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Cart() {
		super();
	}
	public Cart(String image, int id, int orderId, int customerId, int productId, String productName, int price,
			String size, String product_type, String fabric, String gender, int quantity, int amount, String status) {
		super();
		this.image = image;
		this.id = id;
		this.orderId = orderId;
		this.customerId = customerId;
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.size = size;
		this.product_type = product_type;
		this.fabric = fabric;
		this.gender = gender;
		this.quantity = quantity;
		this.amount = amount;
		this.status = status;
	}
	@Override
	public String toString() {
		return "Cart [image=" + image + ", id=" + id + ", orderId=" + orderId + ", customerId=" + customerId
				+ ", productId=" + productId + ", productName=" + productName + ", price=" + price + ", size=" + size
				+ ", product_type=" + product_type + ", fabric=" + fabric + ", gender=" + gender + ", quantity="
				+ quantity + ", amount=" + amount + ", status=" + status + "]";
	}
	
	

}
