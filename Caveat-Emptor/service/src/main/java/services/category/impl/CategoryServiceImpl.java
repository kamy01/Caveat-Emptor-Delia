package services.category.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import entities.Category;
import model.CategoryDto;
import repository.CategoryRepository;
import services.category.CategoryService;
import services.util.Utils;
import utils.exceptions.CategoryException;

@Stateless
@Remote(CategoryService.class)
public class CategoryServiceImpl implements CategoryService {

	@EJB
	CategoryRepository service;
	
	@Override
	public List<CategoryDto> getAllCategories() {

		List<Category> entities = service.getAllCategories();
		List<CategoryDto> categories = Utils.getCategoriesFromEntities(entities);
		return categories;
	}
	
	public CategoryDto getRootCategory() throws CategoryException {
		
		Category entity = service.getCategoryById(0);
		CategoryDto root = Utils.getCategoryFromEntity(entity);
		return root;
	}
	
	@Override
	public List<CategoryDto> getChildrenList(CategoryDto parent) throws CategoryException {
		
		Category parentEntity = Utils.getCategoryFromDto(parent);
		List<Category> categories = service.getChildren(parentEntity);
		List<CategoryDto> categoriesDto = Utils.getCategoriesFromEntities(categories);
		return categoriesDto;
	}

	@Override
	public void insertNewCategory(CategoryDto category) throws CategoryException {
		
		Category entity = Utils.getCategoryFromDto(category);
		service.insertNewCategory(entity);
		
	}

	@Override
	public CategoryDto findCategoryByName(String name) throws CategoryException {
 
		Category entity = service.getCategoryByName(name);
		return Utils.getCategoryFromEntity(entity);

	}

	@Override
	public CategoryDto findCategoryById(Long id) throws CategoryException {
		Category entity = service.getCategoryById(id);
		return Utils.getCategoryFromEntity(entity);
	}

	@Override
	public void removeCategory(CategoryDto category) throws CategoryException {
		
		Category entity = Utils.getCategoryFromDto(category);
		
		List<Category> children = service.getChildren(entity);
		Long parentId = category.getParentId();
		
		for(Category child: children)
		{
			child.setParentId(parentId);
			service.updateCategory(child);
		}
		
		service.deleteCategory(entity);
	}

	@Override
	public void removeCategory(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCategory(CategoryDto category) throws CategoryException {

		Category entity = Utils.getCategoryFromDto(category);
		service.updateCategory(entity);
		
	}

}
