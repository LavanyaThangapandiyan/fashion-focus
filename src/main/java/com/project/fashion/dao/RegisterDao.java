package com.project.fashion.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.fashion.mapper.UserMapper;
import com.project.fashion.mapper.UserMapperSingle;
import com.project.fashion.model.User;
import com.project.fashion.validation.Validation;

@Repository
public class RegisterDao {
	Validation valid=new Validation();
	@Autowired
	JdbcTemplate jdbcTemplate;

	public int saveDetails(User user) {
		List<User> userList = userList();
		String getUser = userList.toString();
		String userEmail = user.getEmail();
		String mobile = user.getMobile();
		boolean contains = getUser.contains(userEmail);
		boolean mobilecont = getUser.contains(mobile);
		System.out.println(mobilecont);
		System.out.println(contains);
		if (contains == true) {
			System.out.println(" Email Already Exist");
			return 2;
		} else if (mobilecont == true) {
			System.out.println(" Mobile Number Already Exist");
			return 3;
		}

		else {
			String insert = "insert into register(name,email,password,phone_number,gender)values(?,?,?,?,?)";
			System.out.println(user.getName());
			boolean name = valid.nameValidation(user.getName());
			boolean email1 = valid.emailValidation(user.getEmail());
			boolean password = valid.passwordValidation(user.getPassword());
			boolean phone = valid.phoneNumberValidation(user.getMobile());
			if (name == true && email1 == true && phone == true && password == true) {
				Object[] details = { user.getName(), user.getEmail(), user.getPassword(), user.getMobile() ,user.getGender()};
				int numberOfRows = jdbcTemplate.update(insert, details);
				System.out.println("Inserted Rows : " + numberOfRows);
				return 1;
			} else
				System.out.println("Invalid Data");
		}
		return 0;
	}

	public int findUserDetails(User user) {
		List<User> userList = userList();
		String getUser = userList.toString();
		String userEmail = user.getEmail();
		String userPassword=user.getPassword();
		boolean contains = getUser.contains(userEmail);
		boolean check=valid.adminEmailValidation(userEmail);
		String find="select password from register where email=?";
   	    User listUser=jdbcTemplate.queryForObject(find,new UserMapperSingle(),userEmail);
   	    String password = listUser.getPassword();
		System.out.println(contains);			
		if(check==true && password.equals(userPassword) )
			return 2;
		else if (contains == true && password.equals(userPassword) )
			return 1;
		else
			return 0;

	}

	public List<User> userList() {
		String userList = "select name,email,password,phone_number,gender from register";
		List<User> listUser = jdbcTemplate.query(userList, new UserMapper());
		System.out.println(listUser);
		return listUser;
	}


}
