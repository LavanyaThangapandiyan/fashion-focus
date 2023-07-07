package com.project.fashion.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.fashion.model.Category;

public class CategoryNameMapper implements RowMapper<Category> {

	@Override
	public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Category category=new Category();
		
		String name=rs.getString("category_name");
		category.setCategoryName(name);
		return category;
	}

}
