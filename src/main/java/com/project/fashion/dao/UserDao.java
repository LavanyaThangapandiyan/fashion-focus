package com.project.fashion.dao;

import java.util.List;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.project.fashion.mapper.OrderMapper;
import com.project.fashion.mapper.PaymentMapper;
import com.project.fashion.mapper.UserMapper;
import com.project.fashion.mapper.UserMapperSingle;
import com.project.fashion.mapper.WishListMapper;
import com.project.fashion.model.Cart;
import com.project.fashion.model.Order;
import com.project.fashion.model.Payment;
import com.project.fashion.model.User;
import com.project.fashion.model.WishList;
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
			return 3;}

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
	
	//-------------------Payment CRUD--------------------
	//---Save Payment Details---
		public int savePaymentDetails(Payment payment)
		{
			String insert="insert into payment(order_id,amount,payment_type,Date)values(?,?,?,?)";
			Object[] details= {payment.getOrderId(),payment.getAmount(),payment.getPaymentType(),payment.getDate()};
			int numberOfRows=jdbcTemplate.update(insert,details);
			System.out.println(numberOfRows);
			return 1;
		}
		
		//---Find Payment Details By Order Id--
		public Payment findPaymentDetails(int orderId)
		{
			String find="select order_id,amount,payment_type,Date from payment where order_id=?";
			Payment paymentList=jdbcTemplate.queryForObject(find, new PaymentMapper(),orderId);
			return paymentList;
		}
		
		//---Get Payment Details (Admin)
		public List<Payment> paymentList()
		{
			String listQuery="select order_id,amount,payment_type,Date from payment where order_id=?";
			List<Payment> listPayment=jdbcTemplate.query(listQuery, new PaymentMapper());
			return listPayment;
		}
		
		//--------------------Orders CRUD----------------
		
		Order order=new Order();
		//---Save Order Details---
		public int saveOrderDetails(Order order)
		{
			String insert="insert into orders(customer_id,product_id,product_name,price,size,category,quantity,is_available)values(?,?,?,?,?,?,?)";
			Object[] details= {order.getCustomerId(),order.getProductId(),order.getPrice(),order.getSize(),order.getCategory(),order.getQuantity(),order.getStatus()};
			int insertRows=jdbcTemplate.update(insert,details);
			System.out.println("Inserted Order : "+insertRows);
			return 1;
		}
		
		//----Cancel Order Details-----
		public int cancelOrder(int id)
		{
			String cancel="update orders set is_available=? where id=?";
			Object[] details= {order.getStatus()};
			int cancelRows=jdbcTemplate.update(cancel,details);
			System.out.println("Cancel Order Rows: "+cancelRows);
			return 1;
		}
		
		//----Update Size Using Order Id----
		public int updateSize(int orderId)
		{
			String updateSize="update orders set size=? where id=?";
			Object[] details= {order.getSize(),order.getOrderId()};
			int updatedSize=jdbcTemplate.update(updateSize,details);
			System.out.println("Updated Size :"+updatedSize);
			return orderId;
		}
		
		//-----Update Quantity Using Order Id-----
		public int updateQuantity(int OrderId)
		{
			String updateQuantity="update orders set quantity=? where id=?";
			Object[] details= {order.getQuantity(),order.getOrderId()};
			int updatedQuantity=jdbcTemplate.update(updateQuantity,details);
			System.out.println("Updated Quantity : "+updatedQuantity);
			return OrderId;
		}
		
		//----Find Order Details Using Order ID
		public Order findByOrderId(int orderId)
		{
			String find="select customer_id,product_id,product_name,price,size,category,quantity,is_available from orders where id=?";
		    Order getRow=jdbcTemplate.queryForObject(find, new OrderMapper(),orderId);
			return getRow;
		}
		
		//---- Get Orders List (Admin)
		public List<Order> getOrdersList()
		{
			String listQuery="select customer_id,product_id,product_name,price,size,category,quantity,is_available from orders";
			List<Order> getOrderList=jdbcTemplate.query(listQuery, new OrderMapper());
			return getOrderList;
		}
		
		//--------Wish List CRUD--------
		
		WishList wish=new WishList();
		//----Save Wish List
		public int saveWishList(WishList wish)
		{
			String insert="insert into wish_list(customer_id,product_id,product_name,price,size,category,is_available)values(?,?,?,?,?,?,?)";
			Object[] details= {wish.getCustomerId(),wish.getProductId(),wish.getProductName(),wish.getPrice(),wish.getSize(),wish.getCategory(),wish.getStatus()};
			int insertRow=jdbcTemplate.update(insert,details);
			System.out.println("Insert Wish List : "+insertRow);
			return 1;
		}
		
		//----  Find wish list using customer ID---
		public WishList getWishListById(int customerId)
		{
			String getRow="select customer_id,product_id,product_name,price,size,category,is_available from wish_list where customer_id=?";
		    WishList getWishList=jdbcTemplate.queryForObject(getRow, new WishListMapper());
			return getWishList;
		}
       
		//---Active and Un active in wish List---
	    public int activeAndUnActiveWishList(int wishListId)
	    {
	    	String statusUpdate="update wish_list set is_available=? where id=?";
	    	Object[] details= {wish.getStatus(),wishListId};
	    	int rows=jdbcTemplate.update(statusUpdate,details);
	    	System.out.println("Wish list status updated :"+rows);
			return 1;
	    }
	    
	    //----Cart CRUD----
	    //----save Cart details--
         Cart cart=new Cart();
	    public int saveCartDetails(Cart cart)
	    {
	    	String insert="insert into cart(order_id,customer_id,product_id,product_name,price,size,product_type,quantity,amount,is_available)values(?,?,?,?,?,?,?,?,?,?)";
	    	Object[] details= {cart.getOrderId(),cart.getCustomerId(),cart.getProductId(),cart.getProductName(),cart.getPrice(),cart.getSize(),cart.getProduct_type(),cart.getQuantity(),cart.getAmount(),cart.getStatus()};
	    	int rows=jdbcTemplate.update(insert,details);
	    	System.out.println("Insert Cart details : "+rows);
			return 1;
	    }
	    //----Active and Un active cart details---
	    public void activeAndUnActiveDetails(int id)
	    {
	    	Cart cart=new Cart();
	    	String statusUpdate="update cart set is_available=? where id=?";
	    	Object[] details= {cart.getStatus(),id};
	    	int update = jdbcTemplate.update(statusUpdate,details);
	    	System.out.println("Update status Cart : "+update);
	    }
	    
	    ///----update Size----
	    public Cart updateProductSize(int cartId)
	    {
	    	String updateSize="update cart set size=? where id=? ";
	    	Object[] details= {cart.getSize(),cartId};
	    	int update = jdbcTemplate.update(updateSize,details);
	    	System.out.println("Update Product Size :"+update);
			return cart;
	    }
	    
	    //-----update quantity----
	    public Cart updateProductquantity(int cartId)
	    {
	    	String updatequantity="update cart set quantity=? where id=?";
	    	Object[] details= {cart.getQuantity(),cartId};
	    	int update = jdbcTemplate.update(updatequantity,details);
	    	System.out.println("Update Quantity : "+update);
			return cart;
	    }
}
