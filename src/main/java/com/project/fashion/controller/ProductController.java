package com.project.fashion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.fashion.dao.ProductDao;
import com.project.fashion.model.Product;



@Controller
public class ProductController {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	ProductDao productDao;
	
	Product product=new Product();
	
	@GetMapping(path="/product")
	public String getRegisterForm()
	{
		return "product";
		
	}
	@GetMapping(path="/slick")
	public String showHome()
	{	
		System.out.println("Start slick");
		return "slick";
	}
	
	@GetMapping(path="/product-submit")
	public String saveProduct(@RequestParam("name")String name,@RequestParam("price")int price,@RequestParam("type")String type,
			@RequestParam("size")String size,@RequestParam("quantity")int quantity,@RequestParam("gender")String gender,@RequestParam("fabric")String fabric
			,@RequestParam("file")String file)
	{
		System.out.println("Start Product Inserted ");
		product.setName(name);
		product.setPrice(price);
		product.setType(type);
		product.setSize(size);
		product.setQuantity(quantity);
		product.setFabric(fabric);
		product.setImage(file);
		product.setGender(gender);
		int number=productDao.saveProductDetails(product);
		
		if(number==1)
			return "list";
		else
		return "product";
		
	}
	

}
