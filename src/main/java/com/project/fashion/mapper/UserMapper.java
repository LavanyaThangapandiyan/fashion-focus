package com.project.fashion.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.fashion.model.User;

public class UserMapper implements RowMapper<User>{

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		User register=new User();
		String name=rs.getString("name");
		String email=rs.getString("email");
		String password=rs.getString("password");
		String mobile=rs.getString("phone_number");
		register.setName(name);
		register.setEmail(email);
		register.setPassword(password);
		register.setMobile(mobile);
		System.out.println(name+email+mobile);
		return register;
	}
	

}
