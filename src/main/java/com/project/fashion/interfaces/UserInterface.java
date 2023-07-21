package com.project.fashion.interfaces;

import java.util.List;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.fashion.exception.ExistMailIdException;
import com.project.fashion.exception.ExistMobileException;
import com.project.fashion.exception.InvalidEmailException;
import com.project.fashion.model.Cart;
import com.project.fashion.model.Order;
import com.project.fashion.model.Payment;
import com.project.fashion.model.User;
import com.project.fashion.model.WishList;

public interface UserInterface {
	public int saveDetails(User user) throws ExistMailIdException, ExistMobileException, JsonProcessingException ;
	public int findUserDetails(User user) throws InvalidEmailException;
	public List<User> userList(Model model) throws JsonProcessingException;
	public int deleteUserDetails(User user);
	public int updateUserPassword(User user) throws InvalidEmailException, JsonProcessingException;
	public int findIdByEmail(String email,HttpSession session);
	public int savePaymentDetails(Payment payment);
	public Payment findPaymentDetails(int orderId);
	public List<Payment> paymentList();
	public int saveOrderDetails(int userId);
	public int cancelOrder(int id);
	 public List<Order> getOrdersList(int userId);
	public int saveWishList(WishList wish);
	public WishList getWishListById(int customerId);
	public int activeAndInActiveWishList(int wishListId);
	public int saveCartDetails(int userId,int id,String name,int price,String type,int quantity,String size);
	public void cancelCartDetails(int id) ;
	public List<Cart> cartList(int customerId);
}
