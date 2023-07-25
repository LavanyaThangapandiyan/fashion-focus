package com.project.fashion.controller;

import java.io.IOException;
import java.util.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.fashion.exception.ExistCategoryException;
import com.project.fashion.exception.ExistProductException;
import com.project.fashion.model.Category;
import com.project.fashion.model.Product;
import com.project.fashion.service.AdminService;

@Controller
public class ProductController {	
	
	AdminService admin=new AdminService();
	Product product = new Product();
	Category category = new Category();

	@PostMapping(path = "/productsubmit")
	public String saveProduct(@RequestParam("images") MultipartFile images, @RequestParam("name") String name,
			@RequestParam("price") int price, @RequestParam("category") String type, @RequestParam("size") String size,
			@RequestParam("quantity") int quantity, @RequestParam("gender") String gender,
			@RequestParam("fabric") String fabric, @ModelAttribute("Product") Product product)
	  throws  IOException, ExistProductException {
		
	    product.setName(name);
		product.setPrice(price);
		product.setType(type);
		product.setSize(size);
		product.setQuantity(quantity);
		product.setFabric(fabric);
		product.setGender(gender);
	    product.setImage(Base64.getEncoder().encodeToString(images.getBytes()));
	        int number = admin.saveProducts(product);
			if (number == 1)
				return "redirect:/allproduct";
			else
				return "product";		
	}
	
	@GetMapping(path = "/updateproduct")
	public String updateProduct(@RequestParam("name") String name, @RequestParam("price") int price,
			@RequestParam("type") String type, @RequestParam("size") String size,
			@RequestParam("quantity") int quantity, @RequestParam("fabric") String fabric,
			@RequestParam("gender") String gender, @RequestParam("id") int id) {
		product.setId(id);
		product.setName(name);
		product.setPrice(price);
		product.setType(type);
		product.setSize(size);
		product.setQuantity(quantity);
		product.setFabric(fabric);
		product.setGender(gender);
		admin.updateProductDetails(id, name, price, size, quantity, fabric, gender);
		return "redirect:allproduct";
	}
	
	@GetMapping("/allproduct")
	public String viewProductPage(Model model) 
	{	
		model.addAttribute("allproduct", admin.allProductList());
		model.addAttribute("inActiveproducts", admin.inActiveProductList());
		return "/allproduct";
	}
	
	@GetMapping("/update/{id}")
	public String showFormProductUpdate(@PathVariable(value = "id") int id, Model model) {
		Product product = admin.getProductById(id);
		model.addAttribute("product", product);
		return "/update_product";
	}
   
	@GetMapping("/deleteproduct/{id}")
	public String unActiveProduct(@PathVariable(value = "id") int id) {
		this.admin.deleteProduct(id);
		return "redirect:/allproduct";
	}
    
	@GetMapping("/active/{id}")
	public String activeProduct(@PathVariable(value = "id") int id) {
		this.admin.activeProduct(id);
		return "redirect:/allproduct";
	}

	//---
	@GetMapping(path = "/product")
	public String getCategoryForm(Model model)
	{
		model.addAttribute("nameList", admin.getCategoryName());
		return "product";
	}
	
	// ---Handling Exist Product Exception ----
		@ExceptionHandler(ExistProductException.class)
		public String existProductException(ExistProductException exception, Model model) {
			model.addAttribute("existproduct", "Product Already Exist");
			return "error";
		}

		// --Display List of Category
		@GetMapping("/category")
		public String viewCategoryPage(Model model) {
			model.addAttribute("listCategory", admin.categoryList());
			model.addAttribute("unActiveList", admin.inActiveCategoryList());
			return "category";
		}

		@PostMapping(path = "/updatesubmit")
		public String updateCategoryName(@RequestParam("categoryName") String name, @RequestParam("id") int id) {
			category.setId(id);
			category.setCategoryName(name);
			admin.updateCategoryName(id, name);
			return "redirect:/category";
		}
	
	@GetMapping("/newcategory")
	public String showNewCategoryForm(Model model) {
		// create model attribute to bind form data
		model.addAttribute("category", category);
		return "/new_category";
	}

	@PostMapping("/savesubmit")
	public String saveCategory(@ModelAttribute("category") Category category, Model model)
			throws ExistCategoryException {
		// save category to database
		admin.saveCategoryDetails(category);
		return "redirect:category";
	}

	// ----Handling Exist Category Exception ----
	@ExceptionHandler(ExistCategoryException.class)
	public String existCategoryException(ExistCategoryException exception, Model model) {
		model.addAttribute("existcategory", "Category Already Exist.");
		return "new_category";

	}

	@GetMapping("/updatecategory/{id}")
	public String showFormForCategoryUpdate(@PathVariable(value = "id") int id, Model model) {
		Category category = admin.findCategoryById(id);
		model.addAttribute("category", category);
		return "update";
	}


	@GetMapping("/deletecategory/{id}")
	public String deleteCategoryById(@PathVariable(value = "id") int id) {
		// call delete Category method
		this.admin.deleteCategoryDetails(id);
		return "redirect:/category";
	}

	@GetMapping("/activecategory/{id}")
	public String activeCategory(@PathVariable(value = "id") int id) {
		this.admin.activeCategoryDetails(id);
		return "redirect:/category";
	}
	
	@GetMapping(path = "/sales")
	public String showSales(Model model) throws JsonProcessingException 
	{
		model.addAttribute("salesList",admin.getSalesList(model));
		return "sales";
	}

	

}
