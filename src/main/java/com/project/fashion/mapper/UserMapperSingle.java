package com.project.fashion.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.fashion.model.User;


public class UserMapperSingle implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		User user=new User();
		String email=rs.getString("email");
		String password=rs.getString("password");
		user.setPassword(password);
		user.setEmail(email);
		return user;
	}

}
