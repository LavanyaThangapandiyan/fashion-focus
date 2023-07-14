package com.project.fashion.model;

import javax.persistence.Column;
import javax.persistence.Lob;


public class Product {

	public Product() {
		super();
	}
	private int id;
	private String name;
	private int price;
	private String type;
	private String size;
	private int quantity;
	private String fabric;
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private String image;
	private String gender;
	private String available;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAvailable() {
		return available;
	}
	public void setAvailable(String available) {
		this.available = available;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getFabric() {
		return fabric;
	}
	public void setFabric(String fabric) {
		this.fabric = fabric;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String fileName) {
		this.image = fileName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Product(int id, String name, int price, String type, String size, int quantity, String fabric, String image,
			String gender, String available) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.type = type;
		this.size = size;
		this.quantity = quantity;
		this.fabric = fabric;
		this.image = image;
		this.gender = gender;
		this.available = available;
	}
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price + ", type=" + type + ", size=" + size
				+ ", quantity=" + quantity + ", fabric=" + fabric + ", image=" + image + ", gender=" + gender
				+ ", available=" + available + "]";
	}
	
	
	
	
	
	
}
