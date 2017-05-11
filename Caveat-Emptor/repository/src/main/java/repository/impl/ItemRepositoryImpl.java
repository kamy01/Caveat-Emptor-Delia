package repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.Item;
import repository.ItemRepository;
import utils.exceptions.ItemException;

@Stateless
@Remote(ItemRepository.class)
public class ItemRepositoryImpl implements ItemRepository {

	private final static String GET_ALL_ITEMS_ERROR = " inside getAllItems() ";
	
	@PersistenceContext(unitName = "myapp")
	EntityManager em;
	
	@Override
	public List<Item> getAllItems() throws ItemException {
		List<Item> itemList = new ArrayList<Item>();
		try {
			Query query = em.createNamedQuery(Item.GET_ALL_ITEMS);
			itemList =  (List<Item>) query.getResultList();
			return itemList;

		} catch (IllegalArgumentException e) {
			throw new ItemException(e + GET_ALL_ITEMS_ERROR);
		} catch (Exception e) {
			throw new ItemException(e + GET_ALL_ITEMS_ERROR);
		}
	}

}
