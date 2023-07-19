package com.project.fashion.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.fashion.model.Cart;

public class UpdateCartMapper implements RowMapper<Cart> {

	@Override
	public Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Cart cart=new Cart();
		int id=rs.getInt("id");
		String size=rs.getString("size");
		int quantity=rs.getInt("quantity");
		int totalAmount=rs.getInt("total_amount");
		cart.setId(id);
		cart.setSize(size);
		cart.setQuantity(quantity);
		cart.setAmount(totalAmount);
		return cart;
	}

}
