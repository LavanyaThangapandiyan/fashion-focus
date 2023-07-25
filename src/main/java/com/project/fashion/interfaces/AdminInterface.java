package com.project.fashion.interfaces;

import java.util.List;

import com.project.fashion.exception.ExistCategoryException;
import com.project.fashion.exception.ExistProductException;
import com.project.fashion.model.Category;
import com.project.fashion.model.Product;

public interface AdminInterface
{
	public int saveProductDetails(Product product) throws ExistProductException;
	public void updateProductDetails(int id, String name, int price, String size, int quantity, String fabric,
			String gender);
	public List<Product> allProductList();
	public List<Product> inActiveProductList();
	public Product getProductById(int productId);
	public int deleteProduct(int id);
	public int activeProduct(int id);
	public void saveCategoryDetails(Category category) throws ExistCategoryException;
	public List<Category> categoryList();
	public List<Category> inActiveCategoryList();
	public List<Category> getCategoryName();
	public void updateCategoryName(int id, String name);
	public Category findCategoryById(int id);
	public int deleteCategoryDetails(int id);

}
