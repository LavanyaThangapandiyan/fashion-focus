package com.project.fashion.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.fashion.model.Category;

public class CategoryMapperSingle implements RowMapper<Category>{

	@Override
	public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Category category=new Category();
		int id=rs.getInt("id");
		String name=rs.getString("category_name");
		String available=rs.getString("is_available");
		category.setId(id);
		category.setCategoryName(name);
		category.setAvailability(available);
		return category;
	}

}
