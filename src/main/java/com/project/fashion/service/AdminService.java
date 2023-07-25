package com.project.fashion.service;


import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.fashion.dao.AdminDao;
import com.project.fashion.exception.ExistCategoryException;
import com.project.fashion.exception.ExistProductException;
import com.project.fashion.model.Category;
import com.project.fashion.model.Product;

@Service

public class AdminService {
	AdminDao adminDao=new AdminDao();
	
	public void saveProducts(Product product) throws ExistProductException
	{
		adminDao.saveProductDetails(product);
	}
	public void updateProductDetails(int id, String name, int price, String size, int quantity, String fabric,
			String gender)
	{
		adminDao.updateProductDetails(id, name, price, size, quantity, fabric, gender);
	}
	
	public void allProductList()
	{
		adminDao.allProductList();
	}
	public void getProductById(int productId)
	{
		adminDao.getProductById(productId);
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
	public void categoryList()
	{
		adminDao.categoryList();
	}
	
	public void unActiveCategoryList()
	{
		adminDao.unActiveCategoryList();
	}
	
	public void getCategoryName()
	{
		adminDao.getCategoryName();	
	}
	
	public void updateCategoryName(int id, String name)
	{
		adminDao.updateCategoryName(id, name);
	}
	public void findCategoryById(int id)
	{
		adminDao.findCategoryById(id);
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
	public void getSalesList(Model model) throws JsonProcessingException
	{
		adminDao.getSalesList(model);
	}
}