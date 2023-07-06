package com.project.fashion.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.project.fashion.mapper.CategoryMapper;
import com.project.fashion.mapper.CategoryMapperSingle;
import com.project.fashion.mapper.ProductMapper;
import com.project.fashion.mapper.ProductMapperAll;
import com.project.fashion.model.Category;
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
		String insert="insert into product(name,price,category,size,quantity,fabric,gender,image)values(?,?,?,?,?,?,?,?)";
		boolean name=valid.nameValidation(product.getName());
		boolean size=valid.nameValidation(product.getSize());
		boolean fabric=valid.nameValidation(product.getFabric());
		if(name==true&&size==true&&fabric==true)
		{
			Object[] details= {product.getName(),product.getPrice(),product.getType(),product.getSize(),product.getQuantity(),
					product.getFabric(),product.getGender(),product.getImage()};
			int numberOfRows=jdbcTemplate.update(insert,details);
			System.out.println("Inserted Rows : " + numberOfRows);
			return 1;
		}
		else 
			System.out.println("Invalid Data");
		return 0;	
	}
	
    //----Update Product Details-----
	public int updateProductDetails(Product product)
	{	
		String update="update product set name=?,price=?,category=?,size=?,quantity=?,fabric=?,gender=? where id=?";
		Object[] details= {product.getName(),product.getPrice(),product.getType(),product.getSize(),product.getQuantity(),product.getFabric(),product.getGender()};
		int numberOfRows=jdbcTemplate.update(update,details);
		System.out.println("Updated rows : " + numberOfRows);
		return 1;	
	}
	
	//-----product List ----
	public List<Product> productList()
	{
		String find="select name,price,category,size,fabric,gender,image from product";
		List<Product> productList=jdbcTemplate.query(find, new ProductMapper());
		return productList;
	}
	//Get Product List
	public List<Product> allProductList()
	{
		String find="select name,price,category,size,quantity,fabric,gender,image from product";
		List<Product> productList=jdbcTemplate.query(find, new ProductMapperAll());
		return productList;
	}
	//------save category details----
	public int saveCategoryDetails(Category category)
	{	
		List<Category> categoryList=categoryList();
		String getCategory=categoryList.toString();
		String getName=category.getCategoryName();
		boolean name=valid.nameValidation(getName);
		boolean contains=getCategory.contains(getName);
		if(contains==true)
		{
			System.out.println("Category Already Exist");
			return 2;
		}else if(name==true)
		   {
               String save="insert into category(category_name, is_available)values(?,?)";
				Object[] details= {category.getCategoryName(),"Available"};
				int numberOfRows=jdbcTemplate.update(save,details);
				System.out.println("Inserted Rows : "+numberOfRows);
				return 1;
		   }else
				System.out.println("Invalid Data");
			return 0;
	}
	
	//---- Active Category List---------
	public List<Category> categoryList()
	{
		String categoryList ="select id,category_name,is_available from category where is_available='Available'";
		List<Category> listCategory=jdbcTemplate.query(categoryList, new CategoryMapper());
		return listCategory;
	}
	//----Un Active Category List---------
		public List<Category> unActiveCategoryList()
		{
			String categoryList ="select id,category_name,is_available from category where is_available='Not Available'";
			List<Category> listCategory=jdbcTemplate.query(categoryList, new CategoryMapper());
			return listCategory;
		}
	
	//--Category Updated---
	public void updateCategoryName(int id,String name)
	{
		String updateName="update category set category_name=? where id=?";
		Object[] details= {name,id};
		int updateRows=jdbcTemplate.update(updateName,details);
		System.out.println("Update Category : "+updateRows);	
	}
	
	//---Get category By ID---
	public Category findCategoryById(int id)
	{
		String find="select id,category_name,is_available from category where id=?";
		Category listCategory=jdbcTemplate.queryForObject(find, new CategoryMapperSingle(),id);
		return listCategory;	
	}
	
	//--Delete category Details---
	public int deleteCategoryDetails(int id)
	{
		String delete="update category set is_available=? where id=?";
		Object[] details={"Not Available",id};
		int deleteRows=jdbcTemplate.update(delete,details);
		System.out.println("Deleted Rows :"+deleteRows);
		return 1;	
	}	
	//---Active Category ---
	public int activeCategoryDetails(int id)
	{
		String active="update category set is_available=? where id=?";
		Object[] details= {"Available",id};
		int activeRows=jdbcTemplate.update(active,details);
		System.out.println("Activated Product : "+activeRows);
		return 1;
	}
}
