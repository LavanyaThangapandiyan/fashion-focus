package com.project.fashion.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.fashion.model.Item;
import com.project.fashion.model.Product;
import com.project.fashion.validation.Validation;

@Repository
public class ProductDao {
Validation valid=new Validation();
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	//----Insert Product Details
	
	public int saveProductDetails(Product product)
	{
		String insert="insert into product(name,price,type,size,quantity,fabric,gender,image)values(?,?,?,?,?,?,?,?)";
		boolean name=valid.nameValidation(product.getName());
		boolean size=valid.nameValidation(product.getSize());
		boolean fabric=valid.nameValidation(product.getFabric());
		if(name==true&&size==true&&fabric==true)
		{
			Object[] details= {product.getName(),product.getPrice(),product.getType(),product.getSize(),product.getQuantity(),
					product.getFabric(),product.getGender(),product.getImage()};
			int numberOfRows=jdbcTemplate.update(insert,details);
			System.out.println("Inserted Rows : " + numberOfRows);
			
			String update ="update item set quantity=? where type=?"; 
			Object[] detailsUpdate = { product.getQuantity(),product.getType() };
			int numberOfRow = jdbcTemplate.update(update, detailsUpdate);
			System.out.println("Updated rows : " + numberOfRow);
			return 1;
		}
		else 
			System.out.println("Invalid Data");
		return 0;	
	}
	
	
}
