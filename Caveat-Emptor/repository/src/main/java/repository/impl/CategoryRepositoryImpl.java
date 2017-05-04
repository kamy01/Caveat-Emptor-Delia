package repository.impl;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

import entities.Category;
import model.CategoryDto;
import repository.CategoryRepository;
import utils.exceptions.CategoryException;
import utils.exceptions.UserException;

@Stateless
@Remote(CategoryRepository.class)
public class CategoryRepositoryImpl implements CategoryRepository {

	private static final String GET_CATEGORY_ERROR = " inside getCategory method with parameter = ";
	private static final String INSERT_CATEGORY_ERROR = " inside insertNewCategory() with parameter = ";
	private final static String UPDATE_CATEGORY_ERROR = " inside updateCategory() with parameter = ";
	private static final String DELETE_CATEGORY_ERROR = " inside deleteCategory() with parameter = ";

	@PersistenceContext(unitName = "myapp")
	EntityManager em;

	@Override
	public Category getCategoryById(long id) throws CategoryException {

		Category category = new Category();

		try {
			Query query = em.createNamedQuery(Category.FIND_CATEGORY_BY_ID);
			category = (Category) query.setParameter("id", id).getSingleResult();
			return category;

		} catch (IllegalArgumentException e) {
			throw new CategoryException(e + GET_CATEGORY_ERROR + id);
		} catch (Exception e) {
			throw new CategoryException(e + GET_CATEGORY_ERROR + id);
		}

	}

	@Override
	public void insertNewCategory(Category category) throws CategoryException {

		try {
			em.persist(category);
			em.flush();

		} catch (IllegalArgumentException e) {
			throw new CategoryException(e + INSERT_CATEGORY_ERROR + category.toString());
		} catch (EntityExistsException e) {
			throw new CategoryException(e + INSERT_CATEGORY_ERROR + category.toString());
		} catch (TransactionRequiredException e) {
			throw new CategoryException(e + INSERT_CATEGORY_ERROR + category.toString());
		} catch (Exception e) {
			throw new CategoryException(e + INSERT_CATEGORY_ERROR + category.toString());
		}
	}

	@Override
	public void updateCategory(Category category) throws CategoryException {

		try {
			em.merge(category);

		} catch (IllegalArgumentException e) {
			throw new CategoryException(e + UPDATE_CATEGORY_ERROR + category.toString());
		} catch (TransactionRequiredException e) {
			throw new CategoryException(e + UPDATE_CATEGORY_ERROR + category.toString());
		} catch (Exception e) {
			throw new CategoryException(e + UPDATE_CATEGORY_ERROR + category.toString());
		}

	}

	@Override
	public List<Category> getAllCategories() {

		Query query = em.createNamedQuery("SELECT e FROM Category e");

		List<Category> resultList = (List<Category>) query.getResultList();
		return resultList;

	}

	@Override
	public List<Category> getChildren(Category parent) throws CategoryException {

		Query query = em.createNamedQuery(Category.GET_CHILDREN_OF_CATEGORY);
		List<Category> singleResult = (List<Category>) query.setParameter("id", parent.getId()).getResultList();
		List<Category> children = singleResult;
		return children;
	}

	@Override
	public Category getCategoryByName(String name) throws CategoryException {

		Category category = new Category();

		try {
			Query query = em.createNamedQuery(Category.FIND_CATEGORY_BY_NAME);
			category = (Category) query.setParameter("name", name).getSingleResult();
			return category;

		} catch (IllegalArgumentException e) {
			throw new CategoryException(e + GET_CATEGORY_ERROR + name);
		} catch (Exception e) {
			throw new CategoryException(e + GET_CATEGORY_ERROR + name);
		}
	}

	@Override
	public void deleteCategory(Category category) throws CategoryException {
		
		try {
			em.remove(em.contains(category) ? category : em.merge(category));

		} catch (IllegalArgumentException e) {
			throw new CategoryException(e + DELETE_CATEGORY_ERROR + category.toString());
		} catch (TransactionRequiredException e) {
			throw new CategoryException(e + DELETE_CATEGORY_ERROR + category.toString());
		} catch (Exception e) {
			throw new CategoryException(e + DELETE_CATEGORY_ERROR + category.toString());
		}
	}

}
