package com.project.fashion.model;

public class Category {
	private int id;
	private String categoryName;
	private String availability;
	public Category() {
		super();
	}
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
	public String getAvailability() {
		return availability;
	}
	public void setAvailability(String availability) {
		this.availability = availability;
	}
	public Category(int id, String categoryName, String availability) {
		super();
		this.id = id;
		this.categoryName = categoryName;
		this.availability = availability;
	}
	
	
	
	public Category(String categoryName) {
		super();
		this.categoryName = categoryName;
	}
	@Override
	public String toString() {
		return categoryName;
	}

	
	

}
