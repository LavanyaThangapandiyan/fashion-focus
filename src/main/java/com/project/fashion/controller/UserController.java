package com.project.fashion.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.fashion.dao.UserDao;
import com.project.fashion.exception.ExistMailIdException;
import com.project.fashion.exception.ExistMobileException;
import com.project.fashion.model.User;
import com.project.fashion.validation.Validation;

@Controller
public class UserController {
	Validation valid = new Validation();
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	UserDao userdao;

	User user = new User();

	@GetMapping("/")
	public String showHome() {
		System.out.println("Start");
		return "index";
	}

	// method to get register form
	@GetMapping(path = "/login")
	public String getLoginForm() {
		return "login";

	}

	@GetMapping(path = "/forgot")
	public String forgotPassword() {

		return "forgot";
	}

	@GetMapping("forgot-submit")
	public String submitForgot(@RequestParam("email") String email, @RequestParam("password") String password) {
		System.out.println("Start Forgot Password");
		user.setEmail(email);
		user.setPassword(password);
		int number = userdao.updateUserPassword(user);
		if (number == 1)
			return "login";
		else
			return "forgot";

	}

	// method to get register form details

	@GetMapping(path = "/login-submit")
	public String submitRegister(@RequestParam("email") String email, @RequestParam("password") String password,
			@ModelAttribute User user, Model model, HttpSession session) {
		user.setEmail(email);
		user.setPassword(password);
		int number = userdao.findUserDetails(user);
		if (number == 2)
			return "list";
		else if (number == 1)
			return "productlist";
		else
			return "login";

	}

	@GetMapping(path = "/register")
	public String getRegisterForm() {
		return "register";

	}

	@GetMapping(path = "/register-submit")
	public String saveUser(@RequestParam("username") String name, @RequestParam("email") String email,
			@RequestParam("password") String password, @RequestParam("mobile") String mobile,
			@RequestParam("gender") String gender) throws ExistMailIdException, ExistMobileException {
		System.out.println("Start Register");
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);
		user.setMobile(mobile);
		user.setGender(gender);
		int number = userdao.saveDetails(user);
		if (number == 1)
			return "login";
		else if (number == 2)
			throw new ExistMailIdException("Exist Email Exception");
		else if (number == 3)
			throw new ExistMobileException("Exist Mobile Number Exception");
		else
			return "register";
	}

	@GetMapping("userlist")
	public String getAllUser(Model model) {
		List<User> users = userdao.userList();
		model.addAttribute("userlist", users);
		return "list.jsp";
	}

}
