package com.project.fashion.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.project.fashion.mapper.CategoryMapper;
import com.project.fashion.mapper.CategoryMapperSingle;
import com.project.fashion.mapper.CategoryNameMapper;
import com.project.fashion.mapper.ProductMapperAll;
import com.project.fashion.mapper.SingleProductMapper;
import com.project.fashion.model.Category;
import com.project.fashion.model.Product;
import com.project.fashion.validation.Validation;

@Repository
public class AdminDao {
Validation valid=new Validation();
    
    JdbcTemplate jdbcTemplate=new JdbcTemplate();
	Product product=new Product();
	
	//----Insert Product Details
	public int saveProductDetails(Product product)
	{
		String insert="insert into product(name,price,category,size,quantity,fabric,gender,image,is_available)values(?,?,?,?,?,?,?,?,?)";
		boolean name=valid.nameValidation(product.getName());
		boolean size=valid.nameValidation(product.getSize());
		boolean fabric=valid.nameValidation(product.getFabric());
		if(name==true&&size==true&&fabric==true)
		{
			Object[] details= {product.getName(),product.getPrice(),product.getType(),product.getSize(),product.getQuantity(),
					product.getFabric(),product.getGender(),product.getImage(),"Available"};
			int numberOfRows=jdbcTemplate.update(insert,details);
			System.out.println("Inserted Rows : " + numberOfRows);
			return 1;
		}
		else 
			System.out.println("Invalid Data");
		return 0;	
	}
	
    //----Update Product Details-----
	public void updateProductDetails(int id,String name,int price,String size,int quantity,String fabric,String gender)
	{	
		String update="update product set name=?,price=?,size=?,quantity=?,fabric=?,gender=? where id=?";
		Object[] details= {name,price,size,quantity,fabric,gender,id};
		int numberOfRows=jdbcTemplate.update(update,details);
		System.out.println("Updated rows : " + numberOfRows);
	}
	
	//Get Product List
	public List<Product> allProductList()
	{
		String find="select id,name,price,category,size,quantity,fabric,gender,image from product where is_available='Available'";
		List<Product> productList=jdbcTemplate.query(find, new ProductMapperAll());
		return productList;
	}
	
	//Get Unactive Product List
	public List<Product> unActiveProductList()
	{
		String find="select id,name,price,category,size,quantity,fabric,gender,image from product where is_available='Not Available'";
		List<Product> productList=jdbcTemplate.query(find, new ProductMapperAll());
		return productList;
	}
	
	//--Get Product Details Using Product ID---
	public Product getProductById(int productId)
	{
		String find="select id,name,price,category,size,quantity,fabric,gender from product where id=?";
		Product getDetails=jdbcTemplate.queryForObject(find, new SingleProductMapper(),productId);
		return getDetails;
	}
	
	//--Delete Product---
	public int deleteProduct(int id)
	{
		String delete="update product set is_available=? where id=?";
		Object[] details= {"Not Available",id};
		int update = jdbcTemplate.update(delete,details);
		System.out.println("Delete Product : "+update);
		return update;
	}
	//---Active Product By Id---
	public int activeProduct(int id)
	{
		String active="update product set is_available=? where id=?";
		Object[] details= {"Available",id};
		int update=jdbcTemplate.update(active,details);
		System.out.println("Active Product : "+update);
		return update;
		
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
	//---- Un Active Category List---------
		public List<Category> unActiveCategoryList()
		{
			String categoryList ="select id,category_name,is_available from category where is_available='Not Available'";
			List<Category> listCategory=jdbcTemplate.query(categoryList, new CategoryMapper());
			return listCategory;
		}
		public List<Category> getCategoryName()
		{
			String getCategoryName="select category_name from category where is_available='Available'";
			List<Category> getCategoryNameList=jdbcTemplate.query(getCategoryName, new CategoryNameMapper());
			return getCategoryNameList;	
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
	//---Update Un Active to Active Category ---
	public int activeCategoryDetails(int id)
	{
		String active="update category set is_available=? where id=?";
		Object[] details= {"Available",id};
		int activeRows=jdbcTemplate.update(active,details);
		System.out.println("Activated Product : "+activeRows);
		return 1;
	}
	
	//Active and Un Active Product
	public int activeAndUnActiveProduct(int id)
	{
		String changeStatus="update product set is_available=? where id=?";
		Object[] details= {product.getAvailable(),id};
		int rows=jdbcTemplate.update(changeStatus,details);
		System.out.println("status updated : "+rows);
		return 1;
	}
}
