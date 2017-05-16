package services.category;

import java.util.List;

import model.CategoryDto;
import utils.exceptions.CategoryException;

public interface CategoryService {

	public List<CategoryDto> getAllCategories();
	public CategoryDto getRootCategory() throws CategoryException;
	public List<CategoryDto> getChildrenList(CategoryDto parent) throws CategoryException;
	public void insertNewCategory(CategoryDto category) throws CategoryException;
	public CategoryDto findCategoryByName(String name) throws CategoryException;
	public CategoryDto findCategoryById(Long id) throws CategoryException;
	public void removeCategory(CategoryDto category) throws CategoryException;
	public void updateCategory(CategoryDto category) throws CategoryException;
	
}
