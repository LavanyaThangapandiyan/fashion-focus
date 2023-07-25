package com.project.fashion.service;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import com.fasterxml.jackson.core.JsonProcessingException;
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

@Service
public class UserService  
{
	UserDao userDao=new UserDao();
	public int saveDetails(User user,Model model) throws ExistMailIdException, ExistMobileException, JsonProcessingException
	{
		return userDao.saveDetails(user, model);
	}
	public int  findUserDetails(User user) throws InvalidEmailException
	{
		return userDao.findUserDetails(user);
	}
	public void  findIdByEmail(String email, HttpSession session)
	{
		userDao.findIdByEmail(email, session);
	}
	
	public int  updateUserPassword(User user,Model model) throws InvalidEmailException, JsonProcessingException
	{
		return userDao.updateUserPassword(user, model);
	}
	
	
	
	public List<User> userList(Model model) throws JsonProcessingException
	{
		return userDao.userList(model);
	}
	
	public void deleteUserDetails(User user)
	{
		userDao.deleteUserDetails(user);
	}
	
	
	public void updateOrderDetails(int id, String size, int quantity, int amount, int userId)
	{
		userDao.updateOrderDetails(id, size, quantity, amount, userId);
	}
	
	public Cart getcartUpdateDetails(int cartId)
	{
		return userDao.getcartUpdateDetails(cartId);
	}
	
	public void  saveCartDetails(int userId, int id, String productName, int price, String type, int quantity,
			String size)
	{
		userDao.saveCartDetails(userId, id, productName, price, type, quantity, size);
	}
	
	public void cancelCartDetails(int id)
	{
		userDao.cancelCartDetails(id);
	}
	
	public void clicktoActiveCartDetails(int id)
	{
		userDao.clicktoActiveCartDetails(id);
	}
	public List<Cart> cartList(int customerId)
	{
		return userDao.cartList(customerId);
	}
	
	public List<Cart> inActiveCartList(int customerId)
	{
		return userDao.inActiveCartList(customerId);
	}
	
	public List<Product> allProductList(String category)
	{
		return userDao.allProductList(category);
	}
	public void saveOrderDetails(int userId)
	{
		userDao.saveOrderDetails(userId);
	}
	
	public void cancelOrder(int id)
	{
		userDao.cancelOrder(id);
	}
	public Order getorderUpdateDetails(int orderId)
	{
		return userDao.getorderUpdateDetails(orderId);
	}
	
	public List<Order> getOrdersList(int userId)
	{
		return userDao.getOrdersList(userId);
	}
	public List<Order> getCancelledOrdersList(int userId) {
		return userDao.getCancelledOrdersList(userId);
		
	}
	public int saveWishList(int  id,int userId) throws IOException
	{
		return userDao.saveWishList(id, userId);
		
	}
	
	public List<WishList> getWishListById(int customerId)
	{
		return userDao.getWishListById(customerId);
	}

	public void activeAndInActiveWishList(int wishListId)
	{
		userDao.activeAndInActiveWishList(wishListId);
	}
	public List<Order> getTotalAmountOrder(int userId,HttpSession session)
	{
		return userDao.getTotalAmountOrder(userId, session);
	}
	
	public void saveAlterTable(int userId)
	{
		userDao.saveAlterTable(userId);
	}
	public int reOrder(int id)
	{
		return userDao.reOrder(id);
	}
	
	public void savePaymentDetails(Payment payment, HttpSession session)
	{
		userDao.savePaymentDetails(payment, session);
	}
	public  void paymentList() 
	{
		userDao.paymentList();
	}
	public List<Order> getOrderHistoryList(int UserId)
	{
		return userDao.getOrderHistoryList(UserId);
		
	}
}
