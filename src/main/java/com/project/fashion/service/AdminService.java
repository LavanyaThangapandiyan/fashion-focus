package com.project.fashion.service;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.fashion.dao.AdminDao;
import com.project.fashion.exception.ExistCategoryException;
import com.project.fashion.exception.ExistProductException;
import com.project.fashion.model.Category;
import com.project.fashion.model.Product;
import com.project.fashion.model.Sales;
@Service

public class AdminService 
{
	AdminDao adminDao=new AdminDao();
	
	public int saveProducts(Product product) throws ExistProductException
	{
		return adminDao.saveProductDetails(product);
	}
	
	
	public void updateProductDetails(int id, String name, int price, String size, int quantity, String fabric,
			String gender)
	{
		adminDao.updateProductDetails(id, name, price, size, quantity, fabric, gender);
	}
	public List<Product> inActiveProductList()
	{
		return adminDao.inActiveProductList();
		
	}
		
	public List<Product> allProductList()
	{
		return adminDao.allProductList();
	}
	
	
	public Product getProductById(int productId)
	{
		return adminDao.getProductById(productId);
	}
	
	public void deleteProduct(int id)
	{
		adminDao.deleteProduct(id);
	}
	
	public void activeProduct(int id)
	{
		adminDao.activeProduct(id);
	}
	
	public void saveCategoryDetails(Category category) throws ExistCategoryException 
	{
		adminDao.saveCategoryDetails(category);
	}
	public List<Category> categoryList()
	{
		return adminDao.categoryList();
	}
	
	public List<Category> inActiveCategoryList()
	{
		return adminDao.inActiveCategoryList();
	}
	
	public List<Category> getCategoryName()
	{
		return adminDao.getCategoryName();	
	}
	
	public void updateCategoryName(int id, String name)
	{
		adminDao.updateCategoryName(id, name);
	}
	public Category findCategoryById(int id)
	{
		return adminDao.findCategoryById(id);
	}
	
	public void deleteCategoryDetails(int id)
	{
		adminDao.deleteCategoryDetails(id);
	}
	public void activeCategoryDetails(int id)
	{
		adminDao.activeCategoryDetails(id);
	}
	public void activeAndUnActiveProduct(int id)
	{
		adminDao.activeAndUnActiveProduct(id);
	}
	public void saveSalesDetails(int productId,int quantity)
	{
		adminDao.saveSalesDetails(productId, quantity);
	}
	public List<Sales> getSalesList(Model model) throws JsonProcessingException
	{
		return adminDao.getSalesList(model);
	}
}