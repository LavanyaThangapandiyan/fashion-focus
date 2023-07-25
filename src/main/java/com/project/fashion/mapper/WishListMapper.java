package com.project.fashion.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.fashion.model.WishList;

public class WishListMapper implements RowMapper<WishList>{

	@Override
	public WishList mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		WishList wish=new WishList();
		int customerId=rs.getInt("customer_id");
		int productId=rs.getInt("product_id");
		String image=rs.getString("image");
		String productName=rs.getString("product_name");
		int price=rs.getInt("price");
		String size=rs.getString("size");
		String category=rs.getString("category");
		wish.setCustomerId(customerId);
		wish.setProductId(productId);
		wish.setImage(image);
		wish.setProductName(productName);
		wish.setPrice(price);
		wish.setSize(size);
		wish.setCategory(category);
		return wish;
	}

}
