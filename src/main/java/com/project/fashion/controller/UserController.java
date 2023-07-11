package com.project.fashion.controller;

import java.util.Date;
import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.fashion.dao.AdminDao;
import com.project.fashion.dao.UserDao;
import com.project.fashion.exception.ExistMailIdException;
import com.project.fashion.exception.ExistMobileException;
import com.project.fashion.exception.InvalidEmailException;
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
    
	
	AdminDao productDao=new AdminDao();
	UserDao userdao=new UserDao();
    Product product=new Product();
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

	@GetMapping("forgotpassword")
	public String resetPassword(@RequestParam("email") String email, @RequestParam("password") String password) throws InvalidEmailException {
		user.setEmail(email);
		user.setPassword(password);
		int number = userdao.updateUserPassword(user);
		if (number == 1)
			return "login";
		else
			return "forgot";

	}

	// method to get register form details

	@PostMapping(path = "/loginsubmit")
	public String submitUserRegisterForm(@RequestParam("email") String email, @RequestParam("password") String password,
			@ModelAttribute User user, Model model) throws InvalidEmailException {
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

	@PostMapping(path = "/register-submit")
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
		else
			return "register";
	}

	@GetMapping("userlist")
	public String getAllUsers(Model model) {
		List<User> users = userdao.userList();
		model.addAttribute("userlist", users);
		return "list.jsp";
	}
	
	@GetMapping(path="/productlist")
	public String viewProductList(Model model)
	{
	     model.addAttribute("productCard",productDao.allProductList());
		 return "productlist";
	}
	
	
   @GetMapping(path="/addcart/{id}")
     public String saveCartDetails(@PathVariable(value="id")int id,@PathVariable(value="name")String name,@PathVariable(value="price")int price,@PathVariable(value="type")String type,
    		 @PathVariable(value="size")String size,@PathVariable(value="fabric")String fabric,@PathVariable(value="gender")String gender,@PathVariable("quantity")int quantity)
     {
	    Cart cart=new Cart();
	    cart.setProductName(name);
	    cart.setPrice(price);
	    cart.setProduct_type(type);
	    cart.setQuantity(price);
	    userdao.saveCartDetails(cart);
		return "/productlist";
     }
   
   @GetMapping(path="/updatesize/{id}")
   public String updateSize(@PathVariable(value="id")int id,Model model)
   {
	  Cart carts=userdao.updateProductSize(id);
	  model.addAttribute("cart",carts);
	   return "cart";
   }
   
   @GetMapping(path="/updatequantity/{id}")
   public String updateQuantity(@PathVariable(value="id")int id,Model model)
   {
	   Cart carts=userdao.updateProductquantity(id);
	   model.addAttribute("cart",carts);
	    return "cart";
   }
  
   @GetMapping(path="/activeinactive/{id}")
   public String activeAndInactiveCart(@PathVariable(value="id")int id)
   {
	userdao.activeAndInActiveCart(id);
	return "cart";
   }
   
   @GetMapping(path="wishlist")
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
   
   @GetMapping(path="/activeInactive/{id}")
   public String activeAndInActiveWishList(@PathVariable(value="id")int id)
   {
	userdao.activeAndInActiveWishList(id);
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
   
   @GetMapping(path="/cancelorder/{id}")
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
