package com.project.fashion.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.fashion.model.Payment;

public class PaymentMapper implements RowMapper<Payment>
{

	@Override
	public Payment mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Payment payment=new Payment();
		
		int orderId=rs.getInt("order_id");
		int amount=rs.getInt("amount");
		String paymentType=rs.getString("payment_type");
		Date date=rs.getDate("Date");
		payment.setOrderId(orderId);
		payment.setAmount(amount);
		payment.setPaymentType(paymentType);
		payment.setDate(date);
		return payment;
	}
	
	

}
