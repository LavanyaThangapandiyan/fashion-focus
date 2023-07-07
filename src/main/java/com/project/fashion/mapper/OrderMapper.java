package com.project.fashion.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.fashion.model.Order;

public class OrderMapper implements RowMapper<Order> {

	@Override
	public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Order order=new Order();
		int customerId=rs.getInt("customer_id");
		int productId=rs.getInt("product-id");
		String productName=rs.getString("product_name");
		int price=rs.getInt("price");
		String size=rs.getString("size");
		String category=rs.getString("category");
		int quantity=rs.getInt("quantity");
		String status =rs.getString("is_available");
		order.setCustomerId(customerId);
		order.setProductId(productId);
		order.setProductName(productName);
		order.setPrice(price);
		order.setSize(size);
		order.setCategory(category);
		order.setQuantity(quantity);
		order.setStatus(status);
		return order;
	}

}
