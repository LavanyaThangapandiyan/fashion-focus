package com.project.fashion.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.fashion.model.Product;
import com.project.fashion.validation.Validation;

@Repository
public class ProductDao {
Validation valid=new Validation();
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public int saveProductDetails(Product product)
	{
		String insert="insert into product(name,price,type,size,quantity,fabric,gender,image)values(?,?,?,?,?,?,?,?)";
		System.out.println(product.getName());
		System.out.println(product.getImage());
		boolean name=valid.nameValidation(product.getName());
		boolean type=valid.nameValidation(product.getType());
		boolean size=valid.nameValidation(product.getSize());
		boolean fabric=valid.nameValidation(product.getFabric());
		if(name==true&&type==true&&size==true&&fabric==true)
		{
			Object[] details= {product.getName(),product.getPrice(),product.getType(),product.getSize(),product.getQuantity(),product.getFabric(),product.getGender(),product.getImage()};
			int numberOfRows=jdbcTemplate.update(insert,details);
			System.out.println("Inserted Rows : " + numberOfRows);
			return 1;
		}
		else 
			System.out.println("Invalid Data");
		return 0;	
	}
}
