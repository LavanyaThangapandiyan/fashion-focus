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

import com.project.fashion.dao.ProductDao;
import com.project.fashion.model.Category;
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
	
	@GetMapping("productlist-submit")
	public String showAll(Model model) {
	    model.addAttribute("products", productDao.productList());
	    return "productlist";
	}
	
	@GetMapping("allproduct-submit")
	public String showAllProduct(Model model) {
	    model.addAttribute("products", productDao.allProductList());
	    return "allproduct";
	}
	
	@GetMapping(path="/allproduct")
	public String getProduct()
	{
		return "allproduct";
		
	}
		
	@GetMapping(path="/item")
	public String showProduct()
	{
		return "item";
		
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
	
	@GetMapping("/showNewCategoryForm")
	public String showNewCategoryForm(Model model)
	{
		// create model attribute to bind form data
		Category category=new Category();
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
		Category category=new Category();
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
	
	@GetMapping("/activeCategory{id}")
	public String activeCategory(@PathVariable(value="id")int id)
	{
		this.productDao.activeCategoryDetails(id);
		return "redirect:/category";
		
	}
}
