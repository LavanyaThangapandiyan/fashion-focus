package com.project.fashion.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.fashion.model.Product;

public class ProductMapperAll implements RowMapper<Product> {

	@Override
	public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Product product=new Product();
		String name=rs.getString("name");
		int price=rs.getInt("price");
		String type=rs.getString("Category");
		String size=rs.getString("size");
		int quantity=rs.getInt("quantity");
		String fabric=rs.getString("fabric");
		String gender=rs.getString("gender");
		String image=rs.getString("image");
		product.setName(name);
		product.setPrice(price);
		product.setType(type);
		product.setSize(size);
		product.setQuantity(quantity);
		product.setFabric(fabric);
		product.setGender(gender);
		product.setImage(image);
		return product;
	}

}
