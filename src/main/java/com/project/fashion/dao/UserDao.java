package com.project.fashion.dao;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import com.project.fashion.exception.ExistMailIdException;
import com.project.fashion.exception.ExistMobileException;
import com.project.fashion.exception.InvalidEmailException;
import com.project.fashion.interfaces.UserInterface;
import com.project.fashion.mapper.CartMapper;
import com.project.fashion.mapper.OrderMapper;
import com.project.fashion.mapper.PaymentMapper;
import com.project.fashion.mapper.ProductMapperAll;
import com.project.fashion.mapper.UpdateCartMapper;
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
	public int saveDetails(User user) throws ExistMailIdException, ExistMobileException {
		List<User> userList = userList();
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

			String insert = "insert into admin_user(username,email,password,phone_number,gender)values(?,?,?,?,?)";
			boolean name = valid.nameValidation(user.getName());
			boolean email1 = valid.emailValidation(user.getEmail());
			boolean password1 = valid.passwordValidation(user.getPassword());
			boolean phone = valid.phoneNumberValidation(user.getMobile());
			if (name == true && email1 == true && phone == true && password1 == true) {
				String input = user.getName();
				String userName = input.substring(0, 1).toUpperCase() + input.substring(1);
				Object[] details = { userName, user.getEmail(), encodedPassword, user.getMobile(), user.getGender() };
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
		String find = "select password,email from admin_user";
		List<User> listUser = jdbcTemplate.query(find, new UserMapperSingle());
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
	public List<User> userList() {
		String userList = "select id,username,email,password,phone_number,gender from admin_user";
		List<User> listUser = jdbcTemplate.query(userList, new UserMapper());
		return listUser;
	}

	// ---User List----
	public List<User> userDetails(String email, HttpSession session) {
		String userList = "select id,username,email,password,phone_number,gender from admin_user where email=?";
		List<User> listUser = jdbcTemplate.query(userList, new UserMapper(), email);
		session.setAttribute("userList", listUser);
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
	public int updateUserPassword(User user) throws InvalidEmailException {
		String password = user.getPassword();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(password);
		List<User> userList = userList();
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

	// -------------------Payment CRUD--------------------
	// ---Save Payment Details---
	public int savePaymentDetails(Payment payment) {
		String insert = "insert into payment(order_id,amount,payment_type,Date)values(?,?,?,?)";
		Object[] details = { payment.getOrderId(), payment.getAmount(), payment.getPaymentType(), payment.getDate() };
		int numberOfRows = jdbcTemplate.update(insert, details);
		logger.info("Payment Inserted Rows : " + numberOfRows);
		return 1;
	}

	// ---Find Payment Details By Order Id--
	public Payment findPaymentDetails(int orderId) {
		String find = "select order_id,amount,payment_type,Date from payment where order_id=?";
		Payment paymentList = jdbcTemplate.queryForObject(find, new PaymentMapper(), orderId);
		return paymentList;
	}

	// ---Get Payment Details (Admin)
	public List<Payment> paymentList() {
		String listQuery = "select order_id,amount,payment_type,Date from payment where order_id=?";
		List<Payment> listPayment = jdbcTemplate.query(listQuery, new PaymentMapper());
		return listPayment;
	}

	// --------------------Orders CRUD----------------

	Order order = new Order();

	// ---Save Order Details---
	public int saveOrderDetails(int userId)
	{
		//--find cart details and by using session id
		String getCartList = " select id,customer_id,product_id,product_name,price,size,product_type,quantity,total_amount,is_available from cart where customer_id=? and is_available=?";
		List<Cart> cartlist= jdbcTemplate.query(getCartList, new CartMapper(), userId,"Available");
	      for (Cart cartModel : cartlist)
		  {
				String insert = "insert into orders(customer_id,cart_id,product_id,productsname,price,size,category,quantity,total_amount,is_available)values(?,?,?,?,?,?,?,?,?,?)";
				Object[] details = { userId, cartModel.getId(), cartModel.getProductId(), cartModel.getProductName(), cartModel.getPrice(), cartModel.getSize(), cartModel.getProduct_type(), cartModel.getQuantity(),cartModel.getAmount(),"Available"};
				int insertRows = jdbcTemplate.update(insert, details);
				logger.info("Inserted Order : " + insertRows);
		}
		return 0;
	}

	// ----Cancel Order Details-----
	public int cancelOrder(int id) {
		String cancel = "update orders set is_available=? where id=?";
		Object[] details = { order.getStatus() };
		int cancelRows = jdbcTemplate.update(cancel, details);
		logger.info("Cancel Order Rows: " + cancelRows);
		return 1;
	}

	// ----Find Order Details Using Order ID
	public Order findByOrderId(int orderId) {
		String find = "select customer_id,product_id,product_name,price,size,category,quantity,is_available from orders where id=?";
		Order getRow = jdbcTemplate.queryForObject(find, new OrderMapper(), orderId);
		return getRow;
	}

	// ---- Get Orders List (Admin)
	 public List<Order> getOrdersList(int userId) {
		String listQuery = "select id,customer_id,product_id,productsname,price,size,category,quantity,total_amount,is_available from orders where customer_id=? and is_available=?";
		List<Order> getOrderList = jdbcTemplate.query(listQuery, new OrderMapper(),userId,"Available");
	      for (Order orderModel : getOrderList)
		  {
	    	  int amount=0;
	    	  amount = amount+ orderModel.getAmount();
	    	  System.out.println(amount);
		  }
		
		
		return getOrderList;
	}

	// --------Wish List CRUD--------

	WishList wish = new WishList();

	// ----Save Wish List
	public int saveWishList(WishList wish) {
		String insert = "insert into wish_list(customer_id,product_id,product_name,price,size,category,is_available)values(?,?,?,?,?,?,?)";
		Object[] details = { wish.getCustomerId(), wish.getProductId(), wish.getProductName(), wish.getPrice(),
				wish.getSize(), wish.getCategory(), wish.getStatus() };
		int insertRow = jdbcTemplate.update(insert, details);
		logger.info("Insert Wish List : " + insertRow);
		return 1;
	}

	// ---- Find wish list using customer ID---
	public WishList getWishListById(int customerId) {
		String getRow = "select customer_id,product_id,product_name,price,size,category,is_available from wish_list where customer_id=?";
		WishList getWishList = jdbcTemplate.queryForObject(getRow, new WishListMapper());
		return getWishList;
	}

	// ---Active and In active in wish List---
	public int activeAndInActiveWishList(int wishListId) {
		String statusUpdate = "update wish_list set is_available=? where id=?";
		Object[] details = { wish.getStatus(), wishListId };
		int rows = jdbcTemplate.update(statusUpdate, details);
		logger.info("Wish list status updated :" + rows);
		return 1;
	}
	// ----Cart CRUD----

	// ----save Cart details--
	Cart cart = new Cart();
	
	//----update cart details------
	public int updateCartDetails(int cartId,String size,int quantity,int amount,int userId)
	{	
		   String find="select id,size,quantity,total_amount from cart where id=?";
		   Cart queryForObject = jdbcTemplate.queryForObject(find, new UpdateCartMapper(),cartId);
		   int quantity2 = queryForObject.getQuantity();
		   int amount1=queryForObject.getAmount();
		   if(quantity2==quantity)
		   {
			 String update="update cart set size=? where id=?";
			 Object[] details= {size,cartId};
			 int update2 = jdbcTemplate.update(update,details);
			 logger.info("Updated Size  : "+update2);
		   }
		   else if(quantity2<quantity)
		   {
		    int addAmount=quantity*amount1;
			String update="update cart set size=?,quantity=?,total_amount=? where id=?";
			Object[] details= {size,quantity,addAmount,cartId};
			int update2 = jdbcTemplate.update(update,details);
			logger.info("Update size and Quantity and Amount in Cart : "+update2);
		    return 1;	
		   }
		   else if(quantity2>quantity)
		   {
			   int divideAmount=amount1%quantity2;
			   int reduceAmount=divideAmount*quantity;
			   String reduce="update cart set size=?,quantity=?,total_amount=? where id=? ";
			   Object[] details= {size,quantity,reduceAmount,cartId};
			   int update = jdbcTemplate.update(reduce,details);
			   logger.info("Reduce Total Amount : "+update);
		   }
		return 0;
		   
	}
	
	
	
	public List<Cart> getcartUpdateDetails(int cartId)
	{
		String findCart = "select id,size,quantity,total_amount from cart where id=? and is_available=? ";
		List<Cart> query = jdbcTemplate.query(findCart, new UpdateCartMapper(),cartId,"Available");
		return query;
	}
	

	public int saveCartDetails(int userId, int id, String productName, int price, String type, int quantity,
			String size) {
		String findCart = "select id,customer_id,product_id,product_name,price,size,product_type,quantity,total_amount,is_available from cart where customer_id=? ";
		List<Cart> query = jdbcTemplate.query(findCart, new CartMapper(),userId);
		for (Cart cartModel : query) {
			int productId = cartModel.getProductId();
			int quantity2 = cartModel.getQuantity();
			int amount = cartModel.getAmount();
			int addQuantity = quantity + quantity2;
			int calculateCurrentAmount = addQuantity * amount;
			if (productId == id)
			{
				String update = "update cart set quantity=?,total_amount=? where product_id=? and customer_id=? ";
				Object[] details = { addQuantity, calculateCurrentAmount, productId,userId};
				int update2 = jdbcTemplate.update(update, details);
				logger.info("Updated Quantity : " + update2);
				return 1;
			}

		}
		int amount = price;
		int totalAmount = amount * quantity;
		String inserts = "insert into cart(customer_id ,product_id ,product_name ,price,size ,product_type ,quantity ,total_amount ,is_available )values(?,?,?,?,?,?,?,?,?)";
		Object[] details = { userId, id, productName, price, size, type, quantity, totalAmount, "Available" };
		int rows = jdbcTemplate.update(inserts, details);
		logger.info("Insert Cart details : " + rows);
		return 2;
	}

	// ---- In active cart details---
	public void cancelCartDetails(int id) 
	{
		String statusUpdate = "update cart set is_available=? where id=?";
		Object[] details = {"Not Available", id };
		int update = jdbcTemplate.update(statusUpdate, details);
		logger.info("Update status Cart : " + update);
	}
	
	public void clicktoActiveCartDetails(int id)
	{
		String statusUpdate = "update cart set is_available=? where id=?";
		Object[] details = {"Available", id };
		int update = jdbcTemplate.update(statusUpdate, details);
		logger.info("Update status Cart : " + update);
	}
	
	// ---Cart List
	public List<Cart> cartList(int customerId) {
		String getCartList = " select id,customer_id,product_id,product_name,price,size,product_type,quantity,total_amount,is_available from cart where customer_id=? and is_available=?";
		List<Cart> queryForObject = jdbcTemplate.query(getCartList, new CartMapper(), customerId,"Available");
		return queryForObject;
	}
	public List<Cart> inActiveCartList(int customerId)
	{
		String getInActiveCartList="select id,customer_id,product_id,product_name,price,size,product_type,quantity,total_amount,is_available from cart where customer_id=? and is_available=?";
		List<Cart> query = jdbcTemplate.query(getInActiveCartList, new CartMapper(),customerId,"Not Available");
		return query;
	}

	// ---Filter--
	public List<Product> allProductList(String category) {
		String find = "select id,name,price,category,size,quantity,fabric,gender,image from product where is_available='Available' and category=?";
		List<Product> productList = jdbcTemplate.query(find, new ProductMapperAll(), category);
		return productList;
	}
}
