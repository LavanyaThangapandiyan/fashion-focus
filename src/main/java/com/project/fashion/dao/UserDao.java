package com.project.fashion.dao;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.fashion.exception.ExistMailIdException;
import com.project.fashion.exception.ExistMobileException;
import com.project.fashion.exception.InvalidEmailException;
import com.project.fashion.interfaces.UserInterface;
import com.project.fashion.mapper.CartMapper;
import com.project.fashion.mapper.OrderAmountMappper;
import com.project.fashion.mapper.OrderMapper;
import com.project.fashion.mapper.PaymentMapper;
import com.project.fashion.mapper.ProductMapperAll;
import com.project.fashion.mapper.UpdateCartMapper;
import com.project.fashion.mapper.UpdateOrderMapper;
import com.project.fashion.mapper.UserMapper;
import com.project.fashion.mapper.UserMapperSingle;
import com.project.fashion.mapper.WishListMapper;
import com.project.fashion.model.Cart;
import com.project.fashion.model.Order;
import com.project.fashion.model.Payment;
import com.project.fashion.model.Product;
import com.project.fashion.model.User;
import com.project.fashion.model.WishList;
import com.project.fashion.util.ConnectionUtil;
import com.project.fashion.validation.Validation;

@Repository
public class UserDao implements UserInterface {
	Logger logger = LoggerFactory.getLogger(UserDao.class);
	Validation valid = new Validation();
	JdbcTemplate jdbcTemplate = ConnectionUtil.getJdbcTemplate();

	// ----Inserting User Details
	public int saveDetails(User user, Model model)
			throws ExistMailIdException, ExistMobileException, JsonProcessingException {
		List<User> userList = userList(model);
		String getUser = userList.toString();
		String userEmail = user.getEmail();
		String mobile = user.getMobile();
		boolean contains = getUser.contains(userEmail);
		boolean mobilecont = getUser.contains(mobile);
		if (contains == true) {
			throw new ExistMailIdException("Exist Email Exception");

		} else if (mobilecont == true) {
			throw new ExistMobileException("Exist Mobile Number Exception");
		} else {
			String password = user.getPassword();
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String encodedPassword = encoder.encode(password);

			String insert = "insert into admin_user(username,email,password,phone_number,gender,is_available)values(?,?,?,?,?,?)";
			boolean name = valid.nameValidation(user.getName());
			boolean email1 = valid.emailValidation(user.getEmail());
			boolean password1 = valid.passwordValidation(user.getPassword());
			boolean phone = valid.phoneNumberValidation(user.getMobile());
			if (name == true && email1 == true && phone == true && password1 == true) {
				String input = user.getName();
				String userName = input.substring(0, 1).toUpperCase() + input.substring(1);
				Object[] details = { userName, user.getEmail(), encodedPassword, user.getMobile(), user.getGender(),
						"Available" };
				int numberOfRows = jdbcTemplate.update(insert, details);
				logger.info("Inserted Rows : " + numberOfRows);
				return 1;
			} else
				logger.error("Invalid Data");
		}
		return 0;
	}

	// --------FindUser-----------
	public int findUserDetails(User user) throws InvalidEmailException {
		String userEmail = user.getEmail();
		String password = user.getPassword();
		String check = valid.adminEmailValidation(userEmail);
		String find = "select password,email from admin_user where is_available=?";
		List<User> listUser = jdbcTemplate.query(find, new UserMapperSingle(), "Available");
		List<User> users = listUser.stream().filter(userOne -> userOne.getEmail().equals(user.getEmail()))
				.collect(Collectors.toList());
		for (User userModel : users) {
			String dbpass = userModel.getPassword();
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			boolean match = encoder.matches(password, dbpass);
			if (userModel != null && check == "true" && match) {
				return 2;
			} else if (match) {
				return 1;
			}
		}
		throw new InvalidEmailException("Invalid Email Exception");
	}

	// find User Details By UserId

	// ---User List----
	public List<User> userList(Model model) throws JsonProcessingException {
		String userList = "select id,username,email,password,phone_number,gender from admin_user where is_available=?";
		List<User> listUser = jdbcTemplate.query(userList, new UserMapper(), "Available");
		ObjectMapper object = new ObjectMapper();
		String users = object.writeValueAsString(listUser);
		model.addAttribute("listofuser", users);
		return listUser;
	}

	// ----Delete User Details
	public int deleteUserDetails(User user) {
		String delete = "update admin_user set is_active=0 where email=?";
		Object[] details = { user.getEmail() };
		int numberOfRows = jdbcTemplate.update(delete, details);
		logger.info("Deleted Rows :" + numberOfRows);
		return 1;
	}

	// --Update user Password
	public int updateUserPassword(User user, Model model) throws InvalidEmailException, JsonProcessingException {
		String password = user.getPassword();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(password);
		List<User> userList = userList(model);
		String getUser = userList.toString();
		String userEmail = user.getEmail();
		boolean contains = getUser.contains(userEmail);
		if (contains == true) {
			String updatePassword = "update admin_user set password=? where email=?";
			Object[] details = { encodedPassword, user.getEmail() };
			int numberOfRows = jdbcTemplate.update(updatePassword, details);
			logger.info("Update Password : " + numberOfRows);
			return 1;
		} else
			throw new InvalidEmailException("Invalid Email ID");
	}

	// ----Find User ID By Email-----
	public int findIdByEmail(String email, HttpSession session) {
		String find = "select id,username,email,password,phone_number,gender from admin_user where email=?";
		List<User> query = jdbcTemplate.query(find, new UserMapper(), email);
		for (User userModel : query) {
			session.setAttribute("id", userModel.getId());
			session.setAttribute("userName", userModel.getName());
			session.setAttribute("email", userModel.getEmail());
		}
		return 0;
	}

	// ----Cart CRUD----

	// ----save Cart details--
	Cart cart = new Cart();

	// ----update order details------
	public int updateOrderDetails(int id, String size, int quantity, int amount, int userId) {
		String findCart = "select id,size,quantity,total_amount from cart where id=? and is_available=? ";
		Cart queryForObject = jdbcTemplate.queryForObject(findCart, new UpdateCartMapper(), id, "Available");
		int amount2 = queryForObject.getAmount();
		int quantity2 = queryForObject.getQuantity();
		if (quantity > quantity2) {
			int addAmount = quantity * amount;
			String updateCart = "update cart set size=?,quantity=?,total_amount=? where id=?";
			Object[] details = { size, quantity, addAmount, id };
			String updateOrder = "update orders set size=?,quantity=?,total_amount=? where id=?";
			Object[] orderDetails = { size, quantity, addAmount, id };
			int update = jdbcTemplate.update(updateOrder, orderDetails);
			logger.info("Updated Order Details : " + update);
			int update2 = jdbcTemplate.update(updateCart, details);
			logger.info("Update Cart details : " + update2);
			return 1;
		} else {
			int updateAmount = amount2 - (amount2 / quantity2);
			System.out.println("Calculation :" + updateAmount);
			String updateCart = "update cart set size=?,quantity=?,total_amount=? where id=?";
			Object[] details = { size, quantity, updateAmount, id };
			String updateOrder = "update orders set size=?,quantity=?,total_amount=? where id=?";
			Object[] orderDetails = { size, quantity, updateAmount, id };
			int update = jdbcTemplate.update(updateOrder, orderDetails);
			logger.info("Updated Order Details : " + update);
			int update2 = jdbcTemplate.update(updateCart, details);
			logger.info("Update Cart details : " + update2);
			return 1;
		}
	}

	// --- Show Update Details---
	public Cart getcartUpdateDetails(int cartId) {
		String findCart = "select id,size,quantity,total_amount from cart where id=? and is_available=? ";
		Cart queryForObject = jdbcTemplate.queryForObject(findCart, new UpdateCartMapper(), cartId, "Available");
		return queryForObject;
	}

	// --- Save Cart Details----
	public int saveCartDetails(int userId, int id, String productName, int price, String type, int quantity,
			String size) {
		String findCart = "select id,customer_id,product_id,product_name,price,size,product_type,quantity,total_amount,image,is_available from cart where customer_id=? ";
		List<Cart> query = jdbcTemplate.query(findCart, new CartMapper(), userId);
		for (Cart cartModel : query) {
			int productId = cartModel.getProductId();
			int quantity2 = cartModel.getQuantity();
			int amount = cartModel.getAmount();
			int addQuantity = quantity + quantity2;
			int calculateCurrentAmount = addQuantity * amount;
			if (productId == id) {
				String update = "update cart set quantity=?,total_amount=? where product_id=? and customer_id=? ";
				Object[] details = { addQuantity, calculateCurrentAmount, productId, userId };
				int update2 = jdbcTemplate.update(update, details);
				logger.info("Updated Quantity : " + update2);
				return 1;
			}

		}
		String find = "select id,name,price,category,size,quantity,fabric,gender,image from product where id=?";
		Product getDetails = jdbcTemplate.queryForObject(find, new ProductMapperAll(), id);
		String image = getDetails.getImage();
		int amount = price;
		int totalAmount = amount * quantity;
		String inserts = "insert into cart(customer_id ,product_id ,image,product_name ,price,size ,product_type ,quantity ,total_amount ,is_available )values(?,?,?,?,?,?,?,?,?,?)";
		Object[] details = { userId, id, image, productName, price, size, type, quantity, totalAmount, "Available" };
		int rows = jdbcTemplate.update(inserts, details);
		logger.info("Insert Cart details : " + rows);
		return 2;
	}

	// ---- In active cart details---
	public void cancelCartDetails(int id) {
		String statusUpdate = "update cart set is_available=? where id=?";
		Object[] details = { "Not Available", id };
		int update = jdbcTemplate.update(statusUpdate, details);
		logger.info("Update status Cart : " + update);
	}

	// -----Active Cart details----
	public void clicktoActiveCartDetails(int id) {
		String statusUpdate = "update cart set is_available=? where id=?";
		Object[] details = { "Available", id };
		int update = jdbcTemplate.update(statusUpdate, details);
		logger.info("Update status Cart : " + update);
	}

	// ---Cart List--------
	public List<Cart> cartList(int customerId) {
		String getCartList = " select id,customer_id,product_id,image,product_name,price,size,product_type,quantity,total_amount,is_available from cart where customer_id=? and is_available=?";
		List<Cart> queryForObject = jdbcTemplate.query(getCartList, new CartMapper(), customerId, "Available");
		return queryForObject;
	}

	public List<Cart> inActiveCartList(int customerId) {
		String getInActiveCartList = "select id,customer_id,product_id,image,product_name,price,size,product_type,quantity,total_amount,is_available from cart where customer_id=? and is_available=?";
		List<Cart> query = jdbcTemplate.query(getInActiveCartList, new CartMapper(), customerId, "Not Available");
		return query;
	}

	// ---Filter--
	public List<Product> allProductList(String category) {
		String find = "select id,name,price,category,size,quantity,fabric,gender,image from product where is_available='Available' and category=?";
		List<Product> productList = jdbcTemplate.query(find, new ProductMapperAll(), category);
		return productList;
	}

	// --------------------Orders CRUD----------------

	Order order = new Order();
	// ---Save Order Details---
	public int saveOrderDetails(int userId) {
		// --find cart details and by using session id	
		String getCartList = " select id,customer_id,product_id,image,product_name,price,size,product_type,quantity,total_amount,is_available from cart where customer_id=? and is_available=?";
		List<Cart> cartlist = jdbcTemplate.query(getCartList, new CartMapper(), userId, "Available");	
		
		for (Cart cartModel : cartlist) 
		{
			
			
			String insert = "insert into orders(customer_id,cart_id,product_id,image,productsname,price,size,category,quantity,total_amount,is_available)values(?,?,?,?,?,?,?,?,?,?,?)";
			Object[] details = { userId, cartModel.getId(), cartModel.getProductId(), cartModel.getImage(),
					cartModel.getProductName(), cartModel.getPrice(), cartModel.getSize(), cartModel.getProduct_type(),
					cartModel.getQuantity(), cartModel.getAmount(), "Available" };
			int insertRows = jdbcTemplate.update(insert, details);
			logger.info("Inserted Order : " + insertRows);

		}
		return 0;
	}

	public void saveAlterTable(int userId) {
		String listQuery = "select id,customer_id,product_id,image,productsname,price,size,category,quantity,total_amount,is_available from orders where customer_id=? and is_available=?";
		List<Order> getOrderList = jdbcTemplate.query(listQuery, new OrderMapper(), userId, "Available");
		for (Order cartModel : getOrderList) {
			String inserts = "insert into altercart(customer_id,product_id,image,productsname ,price,size ,category ,quantity ,total_amount ,is_available )values(?,?,?,?,?,?,?,?,?,?)";
			Object[] detail = { userId, cartModel.getProductId(), cartModel.getImage(), cartModel.getProductName(),
					cartModel.getPrice(), cartModel.getSize(), cartModel.getCategory(), cartModel.getQuantity(),
					cartModel.getAmount(), "Available" };
			int update = jdbcTemplate.update(inserts, detail);
			logger.info("Insert Alter : " + update);
		}

	}
	
	public void saveSales(int userId)
	{
		String listQuery = "select id,customer_id,product_id,image,productsname,price,size,category,quantity,total_amount,is_available from orders where customer_id=? and is_available=?";
		List<Order> getOrderList = jdbcTemplate.query(listQuery, new OrderMapper(), userId, "Available");
		for (Order orderModel : getOrderList)
		{
		 String inserts="id category_name,product_id,quantity,Date";	
		}
	}

	// ----Cancel Order Details-----
	public int cancelOrder(int id) {
		String cancel = "update orders set is_available=? where id=?";
		Object[] details = { "Not Available", id };
		int cancelRows = jdbcTemplate.update(cancel, details);
		logger.info("Cancel Order Rows: " + cancelRows);
		return 1;
	}

	// CLick to ReOrder
	public int reOrder(int id) {
		String reOrder = "update orders set is_available=? where id=?";
		Object[] details = { "Available", id };
		int cancelRows = jdbcTemplate.update(reOrder, details);
		logger.info("Re Order Rows: " + cancelRows);
		return 1;
	}

	// --Find Order Details Using orderId
	public Order getorderUpdateDetails(int orderId) {
		String find = "select id,size,quantity,total_amount from orders where id=? and is_available=? ";
		Order queryForObject = jdbcTemplate.queryForObject(find, new UpdateOrderMapper(), orderId, "Available");
		return queryForObject;
	}

	// ---- Get Orders List (Admin)
	public List<Order> getOrdersList(int userId) {
		String listQuery = "select id,customer_id,product_id,image,productsname,price,size,category,quantity,total_amount,is_available from orders where customer_id=? and is_available=?";
		List<Order> getOrderList = jdbcTemplate.query(listQuery, new OrderMapper(), userId, "Available");
		return getOrderList;
	}

	public List<Order> getCancelledOrdersList(int userId) {
		String listQuery = "select id,customer_id,product_id,image,productsname,price,size,category,quantity,total_amount,is_available from orders where customer_id=? and is_available=?";
		List<Order> getOrderList = jdbcTemplate.query(listQuery, new OrderMapper(), userId, "Not Available");
		return getOrderList;
	}

	public List<Order> getTotalAmountOrder(int userId, HttpSession session) {
		String query = "SELECT SUM(total_amount) from orders where is_available=?;";
		List<Order> query2 = jdbcTemplate.query(query, new OrderAmountMappper(), "Available");
		for (Order orderModel : query2) {
			session.setAttribute("amount", orderModel.getAmount());
		}
		return query2;
	}

	// ---get order History-----

	public List<Order> getOrderHistoryList(int UserId) {
		String query = "select id,customer_id,product_id,image,productsname,price,size ,category,quantity,total_amount,is_available from altercart where customer_id=? and  is_available=?";
		List<Order> query2 = jdbcTemplate.query(query, new OrderMapper(), UserId, "Available");
		return query2;
	}

	// --------Wish List CRUD--------

	WishList wish = new WishList();

	// ----Save Wish List
	public int saveWishList(int id, int userId) throws IOException {
		String find = "select id,name,price,category,size,quantity,fabric,gender,image from product where id=?";
		Product getDetails = jdbcTemplate.queryForObject(find, new ProductMapperAll(), id);
		int id2 = getDetails.getId();
		String image = getDetails.getImage();
		String name = getDetails.getName();
		int price = getDetails.getPrice();
		String size = getDetails.getSize();
		String type = getDetails.getType();
		String insert = "insert into wish_list(customer_id,image,product_id,product_name,price,size,category,is_available)values(?,?,?,?,?,?,?,?)";
		Object[] details = { userId, image, id2, name, price, size, type, "Available" };
		int insertRow = jdbcTemplate.update(insert, details);
		logger.info("Insert Wish List : " + insertRow);
		return 1;
	}

	// ---- Find wish list using customer ID---
	public List<WishList> getWishListById(int customerId) {
		String getRow = "select customer_id,product_id,image,product_name,price,size,category from wish_list where customer_id=?";
		List<WishList> query = jdbcTemplate.query(getRow, new WishListMapper(), customerId);
		return query;
	}

	// ---Active and In active in wish List---
	public int activeAndInActiveWishList(int wishListId) {
		String statusUpdate = "update wish_list set is_available=? where id=?";
		Object[] details = { wish.getStatus(), wishListId };
		int rows = jdbcTemplate.update(statusUpdate, details);
		logger.info("Wish list status updated :" + rows);
		return 1;
	}

	// -------------------Payment CRUD--------------------
	// ---Save Payment Details---
	public void savePaymentDetails(Payment payment, HttpSession session) {
		int userId = (int) session.getAttribute("id");
		LocalDate today = LocalDate.now();
		String insert = "insert into payment(user_name,email,amount,payment_type,card_number,cvv,month,year,Date)values(?,?,?,?,?,?,?,?,?)";
		Object[] details = { payment.getUserName(), payment.getEmail(), payment.getAmount(), payment.getPaymentType(),
				payment.getCardNumber(), payment.getCvv(), payment.getMonth(), payment.getYear(), today };
		int numberOfRows = jdbcTemplate.update(insert, details);
		logger.info("Payment Inserted Rows : " + numberOfRows);
		
		  String delete="delete from orders where customer_id=?"; 
		  Object[] detail={userId}; int update = jdbcTemplate.update(delete,detail);
		  logger.info(" After Payment Clear Orders : "+update);
		  
		  String clearCart="delete from cart where customer_id=?";
		  Object[] deleteCart={userId};
		  int deletesCart=jdbcTemplate.update(clearCart,deleteCart);
		  logger.info("After Payment Clear Cart : "+deletesCart);
		
	}

	// ---Get Payment Details (Admin)
	public List<Payment> paymentList() {
		String listQuery = "select order_id,amount,payment_type,Date from payment where order_id=?";
		List<Payment> listPayment = jdbcTemplate.query(listQuery, new PaymentMapper());
		return listPayment;
	}
}
