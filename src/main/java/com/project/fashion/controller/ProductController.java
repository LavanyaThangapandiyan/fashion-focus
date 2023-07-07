package com.project.fashion.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.fashion.dao.AdminDao;
import com.project.fashion.model.Category;
import com.project.fashion.model.Product;

@Controller
public class ProductController {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	AdminDao productDao;
	
	Product product=new Product();
	Category category=new Category();
	
	@GetMapping(path="/product")
	public String getRegisterForm(Model model)
	{
	    model.addAttribute("nameList",productDao.getCategoryName());
		return "product";
		
	}
	@GetMapping(path="/customer")
	public String showCustomer()
	{
		return "customer";
		
	}
	@GetMapping(path="/sales")
	public String showSales()
	{
		return "sales";
		
	}
	@GetMapping(path="/item")
	public String showProduct()
	{
		return "item";
		
	}	
	@GetMapping(path="/product-submit")
	public String saveProduct(@RequestParam("name")String name,@RequestParam("price")int price,@RequestParam("nameList")String type,
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
		product.setGender(gender);
		product.setImage(file);
		int number=productDao.saveProductDetails(product);
		
		if(number==1)
			return "list";
		else
		return "product";
	}
	
	
	//--Display List of Category
	@GetMapping("/category")
	public String viewCategoryPage(Model model)
	{
		model.addAttribute("listCategory",productDao.categoryList());
		model.addAttribute("unActiveList",productDao.unActiveCategoryList());
		return "category";
	}
	
	@GetMapping("/allproduct")
	public String viewProductPage(Model model)
	{
		model.addAttribute("products",productDao.allProductList());
		return "allproduct";
	}
	
	@GetMapping("/showNewProductForm")
	public String showNewProductForm(Model model)
	{
		model.addAttribute("product",product);
		return "new_product";
	}

	@GetMapping("/showNewCategoryForm")
	public String showNewCategoryForm(Model model)
	{
		// create model attribute to bind form data
		model.addAttribute("category",category);
		return "/new_category";
	}
	
	@PostMapping("/saveCategory")
	public String saveCategory(@ModelAttribute("category") Category category)
	{
		//save category to database
		productDao.saveCategoryDetails(category);
		return "redirect:category";
	}
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable(value="id")int id,Model model)
	{
		Category category=productDao.findCategoryById(id);
		model.addAttribute("category",category);
		return "update";	
	}
	@GetMapping(path="/update-submit")
	public String updateProductName(@RequestParam("categoryName")String name,@RequestParam("id")int id)
	{
		category.setId(id);
		category.setCategoryName(name);
		productDao.updateCategoryName(id, name);
		return "redirect:/category";	
	}
	
	@GetMapping("/deleteCategory/{id}")
	public String deleteCategory(@PathVariable(value="id")int id)
	{
		 // call delete Category method 
		this.productDao.deleteCategoryDetails(id);
		return "redirect:/category";
		
	}
	
	@GetMapping("/activeCategory/{id}")
	public String activeCategory(@PathVariable(value="id")int id)
	{
		this.productDao.activeCategoryDetails(id);
		return "redirect:/category";	
	}
	
	
}
