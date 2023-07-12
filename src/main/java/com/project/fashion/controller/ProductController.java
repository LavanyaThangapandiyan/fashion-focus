package com.project.fashion.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.project.fashion.dao.AdminDao;
import com.project.fashion.exception.ExistCategoryException;
import com.project.fashion.exception.ExistProductException;
import com.project.fashion.model.Category;
import com.project.fashion.model.Product;

@Controller
public class ProductController
{   
	AdminDao productDao=new AdminDao();
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
	@PostMapping(path="/productsubmit")
	public String saveProduct(@RequestParam("name")String name,@RequestParam("price")int price,@RequestParam("category")String type,
			@RequestParam("size")String size,@RequestParam("quantity")int quantity,@RequestParam("gender")String gender,@RequestParam("fabric")String fabric
			,@RequestParam("file")String file,@ModelAttribute("Product") Product product) throws ExistProductException
	{
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
			return "redirect:/allproduct";
		else
		return "product";
	}
	
	//---Handling Exist Product Exception ----
	        @ExceptionHandler(ExistProductException.class)
			public String existProductException(ExistProductException exception,Model model)
			{
				model.addAttribute("existproduct","Product Already Exist");
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

	@GetMapping("/newcategory")
	public String showNewCategoryForm(Model model)
	{
		// create model attribute to bind form data
		model.addAttribute("category",category);
		return "/new_category";
	}
	
	@PostMapping("/savesubmit")
	public String saveCategory(@ModelAttribute("category") Category category,Model model) throws ExistCategoryException
	{
		//save category to database
		productDao.saveCategoryDetails(category);
		return "redirect:category";
	}
	
	//----Handling Exist Category Exception ----
	@ExceptionHandler(ExistCategoryException.class)
	public String existCategoryException(ExistCategoryException exception,Model model)
	{
		model.addAttribute("existcategory","Category Already Exist.");
		return "new_category";
		
	}
	
	@GetMapping("/updatecategory/{id}")
	public String showFormForCategoryUpdate(@PathVariable(value="id")int id,Model model)
	{
		Category category=productDao.findCategoryById(id);
		model.addAttribute("category",category);
		return "update";	
	}
	
	@PostMapping(path="/updatesubmit")
	public String updateCategoryName(@RequestParam("categoryName")String name,@RequestParam("id")int id)
	{
		category.setId(id);
		category.setCategoryName(name);
		productDao.updateCategoryName(id, name);
		return "redirect:/category";	
	}
	@GetMapping("/allproduct")
	public String viewProductPage(Model model)
	{
		model.addAttribute("allproduct",productDao.allProductList());
		model.addAttribute("inActiveproducts",productDao.unActiveProductList());
		return "/allproduct";
	}
	@GetMapping("/update/{id}")
	public String showFormProductUpdate(@PathVariable(value="id")int id,Model model)
	{
	    Product product=productDao.getProductById(id);
		model.addAttribute("product", product);
		return "/update_product";
	}
	@GetMapping(path="/updateproduct")
	public String updateProduct(@RequestParam("name")String name,@RequestParam("price")int price,
			@RequestParam("type")String type,@RequestParam("size")String size,@RequestParam("quantity")int quantity,
			@RequestParam("fabric")String fabric,@RequestParam("gender")String gender,@RequestParam("id")int id)
	{
		product.setId(id);
		product.setName(name);
		product.setPrice(price);
		product.setType(type);
		product.setSize(size);
		product.setQuantity(quantity);
		product.setFabric(fabric);
		product.setGender(gender);
		productDao.updateProductDetails(id, name, price, size, quantity, fabric, gender);
		return "redirect:allproduct";
	}
	
     @GetMapping("/active/{id}")
	public String activeProduct(@PathVariable(value="id")int id)
	{
		this.productDao.activeProduct(id);
		return "redirect:/allproduct";
	}
	
	@GetMapping("/deleteproduct/{id}")
	public String unActiveProduct(@PathVariable(value="id")int id)
	{
		this.productDao.deleteProduct(id);
		return "redirect:/allproduct";
	}
	
	
	@GetMapping("/deletecategory/{id}")
	public String deleteCategoryById(@PathVariable(value="id")int id)
	{
		 // call delete Category method 
		this.productDao.deleteCategoryDetails(id);
		return "redirect:/category";
	}
	@GetMapping("/activecategory/{id}")
	public String activeCategory(@PathVariable(value="id")int id)
	{
		this.productDao.activeCategoryDetails(id);
		return "redirect:/category";	
	}
	
}
