package com.project.fashion.model;

public class Item {
	private String type;
	private String name;
	private int quantity;
	@Override
	public String toString() {
		return "Item [type=" + type + ", name=" + name + ", quantity=" + quantity + "]";
	}
	public Item() {
		super();
	}
	public Item(String type, String name, int quantity) {
		super();
		this.type = type;
		this.name = name;
		this.quantity = quantity;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	

}
