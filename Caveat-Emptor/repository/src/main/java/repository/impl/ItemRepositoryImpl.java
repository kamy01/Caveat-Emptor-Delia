package repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

import entities.Item;
import entities.User;
import repository.ItemRepository;
import utils.exceptions.ItemException;

@Stateless
@Remote(ItemRepository.class)
public class ItemRepositoryImpl implements ItemRepository {

	private final static String GET_ALL_ITEMS_ERROR = " inside getAllItems() ";
	private final static String GET_ITEMS_ERROR = " inside getItems() for categories: ";
	private final static String UPDATE_ITEM_ERROR = " inside updateItem() for parameter: ";
	private final static String CREATE_ITEM_ERROR = " inside addItem() for parameter: ";
	
	@PersistenceContext(unitName = "myapp")
	EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Item> getAllItems() throws ItemException {
		
		List<Item> itemList = new ArrayList<Item>();
		try {
			Query query = em.createNamedQuery(Item.GET_ALL_ITEMS);
			itemList =  (List<Item>) query.getResultList();
			return itemList;

		} catch (IllegalArgumentException e) {
			throw new ItemException(e.getMessage() + GET_ALL_ITEMS_ERROR);
		} catch (Exception e) {
			throw new ItemException(e.getMessage() + GET_ALL_ITEMS_ERROR);
		}
	}

	@Override
	public List<Item> getAllItemsForUser(User owner) throws ItemException {
		
		List<Item> itemList = new ArrayList<Item>();
		try {
			Query query = em.createNamedQuery(Item.GET_ITEMS_FOR_USER);
			query.setParameter("id", owner.getId());
			itemList =  (List<Item>) query.getResultList();
			return itemList;

		} catch (IllegalArgumentException e) {
			throw new ItemException(e.getMessage() + GET_ALL_ITEMS_ERROR);
		} catch (Exception e) {
			throw new ItemException(e.getMessage() + GET_ALL_ITEMS_ERROR);
		}
	}

	@Override
	public void updateItem(Item item) throws ItemException {
		
		try {
			em.merge(item);

		} catch (IllegalArgumentException e) {
			throw new ItemException(e.getMessage() + UPDATE_ITEM_ERROR + item.toString());
		} catch (TransactionRequiredException e) {
			throw new ItemException(e.getMessage() + UPDATE_ITEM_ERROR + item.toString());
		} catch (Exception e) {
			throw new ItemException(e.getMessage() + UPDATE_ITEM_ERROR + item.toString());
		}

	}

	@Override
	public void addItem(Item item) throws ItemException {

		try {
			em.persist(item);
		} catch (IllegalArgumentException e) {
			throw new ItemException(e.getMessage() + CREATE_ITEM_ERROR + item.toString());
		} catch (TransactionRequiredException e) {
			throw new ItemException(e.getMessage() + CREATE_ITEM_ERROR + item.toString());
		} catch (Exception e) {
			throw new ItemException(e.getMessage() + CREATE_ITEM_ERROR + item.toString());
		}
		
	}

	@Override
	public List<Item> getItemsForCategories(List<Long> parentList) throws ItemException {

		List<Item> itemList = new ArrayList<Item>();
		try {
			Query query = em.createNamedQuery(Item.GET_ITEMS_FOR_CATEGORIES);
			query.setParameter("idList", parentList);
			itemList =  (List<Item>) query.getResultList();
			return itemList;

		} catch (IllegalArgumentException e) {
			throw new ItemException(e.getMessage() + GET_ITEMS_ERROR + parentList);
		} catch (Exception e) {
			throw new ItemException(e.getMessage() + GET_ITEMS_ERROR + parentList);
		}
		
	}
}
