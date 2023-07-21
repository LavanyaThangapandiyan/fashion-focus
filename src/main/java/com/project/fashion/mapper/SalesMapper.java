package com.project.fashion.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.fashion.model.Sales;

public class SalesMapper implements RowMapper<Sales> {

	@Override
	public Sales mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Sales sale=new Sales();
		int id=rs.getInt("id");
		String name=rs.getString("category_name");
		int productId=rs.getInt("product_id");
		int quantity=rs.getInt("quantity");
		Date date=rs.getDate("Date");
		sale.setId(id);
        sale.setCategoryName(name);
        sale.setProductId(productId);
        sale.setDate(date);
        sale.setQuantity(quantity);
		return sale;
	}

}
