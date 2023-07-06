package com.project.fashion.dao;

import java.util.List;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import com.project.fashion.mapper.UserMapper;
import com.project.fashion.mapper.UserMapperSingle;
import com.project.fashion.model.User;
import com.project.fashion.validation.Validation;

@Repository
public class UserDao {
	Validation valid = new Validation();
	@Autowired
	JdbcTemplate jdbcTemplate;

	//----Inserting User Details
	public int saveDetails(User user) 
	{	
		
		List<User> userList = userList();
		String getUser = userList.toString();
		String userEmail = user.getEmail();
		String mobile = user.getMobile();
		boolean contains = getUser.contains(userEmail);
		boolean mobilecont = getUser.contains(mobile);
		if (contains == true) {
			System.out.println(" Email Already Exist");
			return 2;
		} else if (mobilecont == true) {
			System.out.println(" Mobile Number Already Exist");
			return 3;
		}

		else 
		 {
			String password = user.getPassword();
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String encodedPassword = encoder.encode(password);

			String insert = "insert into register(username,email,password,phone_number,gender)values(?,?,?,?,?)";
			boolean name = valid.nameValidation(user.getName());
			boolean email1 = valid.emailValidation(user.getEmail());
			boolean password1 = valid.passwordValidation(user.getPassword());
			boolean phone = valid.phoneNumberValidation(user.getMobile());
			if (name == true && email1 == true && phone == true && password1 == true) {
				Object[] details = { user.getName(), user.getEmail(), encodedPassword, user.getMobile(),
						user.getGender() };
				int numberOfRows = jdbcTemplate.update(insert, details);
				System.out.println("Inserted Rows : " + numberOfRows);
				return 1;
			} else
				System.out.println("Invalid Data");
		}
		return 0;
	}
	// --------FindUser-----------
	public int findUserDetails(User user)
	{
		String userEmail = user.getEmail();
		String password = user.getPassword();
		String check = valid.adminEmailValidation(userEmail);
		String find = "select password,email from register";
		List<User> listUser = jdbcTemplate.query(find, new UserMapperSingle());
		List<User> users = listUser.stream().filter(userOne -> userOne.getEmail().equals(user.getEmail()))
				.collect(Collectors.toList());
		for (User userModel : users) 
		{
			String dbpass = userModel.getPassword();
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			boolean match = encoder.matches(password, dbpass);
			
			if (userModel!= null&&check=="true"&& match)
			{
					return 2;
		    }
			else if(match)
				return 1;			
		}
		return 0;	
	}
	
	//---User List----

	public List<User> userList() {
		String userList = "select username,email,password,phone_number,gender from register";
		List<User> listUser = jdbcTemplate.query(userList, new UserMapper());
		return listUser;
	}
	
	
	//----Delete User Details
	public int deleteUserDetails(User user)
	{
		String delete="update register set is_active=0 where email=?";
		Object[] details= {user.getEmail()};
		int numberOfRows=jdbcTemplate.update(delete,details);
		System.out.println("Deleted Rows :" +numberOfRows);
		return 1;	
	}
	
	
	//--Update user Password
	public int updateUserPassword(User user)
	{
		String password = user.getPassword();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(password);

		String updatePassword="update register set password=? where email=?";
		Object[] details= {encodedPassword,user.getEmail()};
		int numberOfRows=jdbcTemplate.update(updatePassword,details);
		System.out.println("Update Password : "+numberOfRows);
		return 1;
	}
}
