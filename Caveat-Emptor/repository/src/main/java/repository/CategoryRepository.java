package repository;

import java.util.List;

import entities.Category;
import utils.exceptions.CategoryException;

public interface CategoryRepository {
	
	public Category getCategoryById(long id) throws CategoryException;
	public List<Category> getChildren(Category parent) throws CategoryException;
	public void insertNewCategory(Category category) throws CategoryException;
	public void updateCategory(Category category) throws CategoryException;
	public List<Category> getAllCategories();
	public Category getCategoryByName(String name) throws CategoryException;
	public void deleteCategory(Category category) throws CategoryException;
	
}
