package com.project.fashion.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.fashion.dao.UserDao;
import com.project.fashion.exception.ExistMailIdException;
import com.project.fashion.exception.ExistMobileException;
import com.project.fashion.exception.ExistProductException;
import com.project.fashion.exception.InvalidEmailException;
import com.project.fashion.model.Cart;
import com.project.fashion.model.Order;
import com.project.fashion.model.Payment;
import com.project.fashion.model.Product;
import com.project.fashion.model.User;
import com.project.fashion.model.WishList;

@Service
public class UserService  {
	UserDao userDao=new UserDao();
	public void saveDetails(User user,Model model) throws ExistMailIdException, ExistMobileException, JsonProcessingException
	{
		userDao.saveDetails(user, model);
	}
	public void  findUserDetails(User user) throws InvalidEmailException
	{
		userDao.findUserDetails(user);
	}
	
	public void userList(Model model) throws JsonProcessingException
	{
		userDao.userList(model);
	}
	
	public void deleteUserDetails(User user)
	{
		userDao.deleteUserDetails(user);
	}
	
	public void  updateUserPassword(User user,Model model) throws InvalidEmailException, JsonProcessingException
	{
		userDao.updateUserPassword(user, model);
	}
	
	public void  findIdByEmail(String email, HttpSession session)
	{
		userDao.findIdByEmail(email, session);
	}
	
	public void updateOrderDetails(int id, String size, int quantity, int amount, int userId)
	{
		userDao.updateOrderDetails(id, size, quantity, amount, userId);
	}
	
	public void getcartUpdateDetails(int cartId)
	{
		userDao.getcartUpdateDetails(cartId);
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
	public void cartList(int customerId)
	{
		userDao.cartList(customerId);
	}
	
	public void inActiveCartList(int customerId)
	{
		userDao.inActiveCartList(customerId);
	}
	
	public void allProductList(String category)
	{
		userDao.allProductList(category);
	}
	public void saveOrderDetails(int userId)
	{
		userDao.saveOrderDetails(userId);
	}
	
	public void cancelOrder(int id)
	{
		userDao.cancelOrder(id);
	}
	public void getorderUpdateDetails(int orderId)
	{
		userDao.getorderUpdateDetails(orderId);
	}
	
	public void getOrdersList(int userId)
	{
		userDao.getOrdersList(userId);
	}
	
	public void saveWishList(WishList wish)
	{
		userDao.saveWishList(wish);
	}
	
	public void getWishListById(int customerId)
	{
		userDao.getWishListById(customerId);
	}

	public void activeAndInActiveWishList(int wishListId)
	{
		userDao.activeAndInActiveWishList(wishListId);
	}
	public void savePaymentDetails(Payment payment)
	{
		userDao.savePaymentDetails(payment);
	}
	public void findPaymentDetails(int orderId)
	{
		userDao.findPaymentDetails(orderId);
	}
	public  void paymentList() 
	{
		userDao.paymentList();
	}
}
