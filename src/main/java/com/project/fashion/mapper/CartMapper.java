package com.project.fashion.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.fashion.model.Cart;

public class CartMapper implements RowMapper<Cart> {

	@Override
	public Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Cart cart=new Cart();
		/*customer_id,product_id,product_name,price,size,product_type,quantity,total_amount,is_available
		 * 
		 * */
		int customerId=rs.getInt("customer_id");
		int productId=rs.getInt("product_id");
		String name=rs.getString("product_name");
		int price=rs.getInt("price");
		String size=rs.getString("size");
		String productType=rs.getString("product_type");
		int quantity=rs.getInt("quantity");
		int amount=rs.getInt("total_amount");
		String status=rs.getString("is_available");
		cart.setCustomerId(customerId);
		cart.setProductId(productId);
		cart.setProductName(name);
		cart.setPrice(price);
		cart.setSize(size);
		cart.setProduct_type(productType);
		cart.setQuantity(quantity);
		cart.setAmount(amount);
		cart.setStatus(status);
		return cart;
	}

}
