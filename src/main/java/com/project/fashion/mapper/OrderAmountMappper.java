package com.project.fashion.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.fashion.model.Order;

public class OrderAmountMappper  implements RowMapper<Order>{

	@Override
	public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Order order=new Order();
		int amount=rs.getInt("SUM(total_amount)");
		order.setAmount(amount);
		return order;
	}

}
