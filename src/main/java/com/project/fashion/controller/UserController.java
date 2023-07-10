package com.project.fashion.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.fashion.dao.AdminDao;
import com.project.fashion.dao.UserDao;
import com.project.fashion.exception.ExistMailIdException;
import com.project.fashion.exception.ExistMobileException;
import com.project.fashion.model.Cart;
import com.project.fashion.model.Order;
import com.project.fashion.model.Payment;
import com.project.fashion.model.Product;
import com.project.fashion.model.User;
import com.project.fashion.model.WishList;
import com.project.fashion.validation.Validation;

@Controller
public class UserController {
	Validation valid = new Validation();
	@Autowired
	JdbcTemplate jdbcTemplate;
   
	Product product=new Product();
	@Autowired
	AdminDao productDao;
	@Autowired
	UserDao userdao;
    
	User user=new User();

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
	public String showFormForforgotPassword() {

		return "forgot";
	}

	@GetMapping("forgot-submit")
	public String resetPassword(@RequestParam("email") String email, @RequestParam("password") String password) {
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
	public String submitUserRegisterForm(@RequestParam("email") String email, @RequestParam("password") String password,
			@ModelAttribute User user, Model model, HttpSession session) {
		user.setEmail(email);
		user.setPassword(password);
		int number = userdao.findUserDetails(user);
		if (number == 2)
			return "list";
		else if (number == 1)
			return "/productlist";
		else
			return "login";

	}

	@GetMapping(path = "/register")
	public String getRegisterForm() {
		return "register";

	}

	@GetMapping(path = "/register-submit")
	public String saveUserDetails(@RequestParam("username") String name, @RequestParam("email") String email,
			@RequestParam("password") String password, @RequestParam("mobile") String mobile,
			@RequestParam("gender") String gender) throws ExistMailIdException, ExistMobileException {
		System.out.println("Start Register");
		User user=new User();
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
	public String getAllUsers(Model model) {
		List<User> users = userdao.userList();
		model.addAttribute("userlist", users);
		return "list.jsp";
	}
	
	@GetMapping(path="/productlist-submit")
	public String viewProductList(Model model)
	{
	     model.addAttribute("productCard",productDao.allProductList());
		 return "/productlist";
	}
	
	
   @GetMapping(path="/product-card-submit")
     public String saveCartDetails(@RequestParam("name")String name,@RequestParam("price")int price,
    		 @RequestParam("type")String type,@RequestParam("quantity")int quantity,@RequestParam("gender")String gender)
     {
	    Cart cart=new Cart();
	    cart.setProductName(name);
	    cart.setPrice(price);
	    cart.setProduct_type(type);
	    cart.setQuantity(quantity);
	    userdao.saveCartDetails(cart);
		return "/productlist";
     }
   
   @GetMapping(path="/update-size/{id}")
   public String updateSize(@PathVariable(value="id")int id,Model model)
   {
	  Cart carts=userdao.updateProductSize(id);
	  model.addAttribute("cart",carts);
	   return "cart";
   }
   
   @GetMapping(path="/update-quantity/{id}")
   public String updateQuantity(@PathVariable(value="id")int id,Model model)
   {
	   Cart carts=userdao.updateProductquantity(id);
	   model.addAttribute("cart",carts);
	    return "cart";
   }
  
   @GetMapping(path="/active-un-active/{id}")
   public String activeAndUnactiveCart(@PathVariable(value="id")int id)
   {
	   userdao.activeAndUnActiveDetails(id);
	return "cart";
   }
   
   @GetMapping(path="wish_list")
   public String saveWishList(@RequestParam("name")String name,@RequestParam("price")int price,@RequestParam("size")String size,@RequestParam("category")String category)
   {
	   
	   WishList wish=new WishList();
	   wish.setProductName(name);
	   wish.setPrice(price);
	   wish.setCategory(category);
	   wish.setSize(size);
	   userdao.saveWishList(wish);
	   return "/productlist";
   }
   
   @GetMapping(path="/activeUnactive/{id}")
   public String activeAndUnActiveWishList(@PathVariable(value="id")int id)
   {
	userdao.activeAndUnActiveWishList(id);
	return "wish_list";
   }
   
   @GetMapping(path="/order")
   public String saveOrderDetails(@RequestParam("name")String name,@RequestParam("price")int price,@RequestParam("size")String size,@RequestParam("category")String category)
   {
	Order order=new Order();
	order.setProductName(name);
	order.setPrice(price);
	order.setCategory(category);
	order.setSize(size);
	order.setQuantity(price);
	return "order";
   }
   
   @GetMapping(path="/cancel-order/{id}")
   public String cancelOrder(@PathVariable(value="id")int id)
   {
	userdao.cancelOrder(id);
	return "redirect:order";
   }
   
   @GetMapping(path="/payment")
   public String savePaymentDetails(@RequestParam("OrderId")int id,@RequestParam("Amount")int amount,@RequestParam("PaymentType")String PaymentType ,@RequestParam("date")Date date)
   {
	Payment pay=new Payment();
	pay.setOrderId(id);
	pay.setPaymentType(PaymentType);
	pay.setAmount(amount);
	pay.setDate(date);
	userdao.savePaymentDetails(pay);
	return "/payment";
   }
   

}
