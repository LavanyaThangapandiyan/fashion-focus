package com.project.fashion.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.fashion.model.Order;

public class UpdateOrderMapper implements RowMapper<Order> {

	@Override
	public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Order order=new Order();
		int id=rs.getInt("id");
		int quantity=rs.getInt("quantity");
		int amount=rs.getInt("total_amount");
		order.setId(id);
		order.setQuantity(quantity);
		order.setAmount(amount);
		return order;
	}

}
