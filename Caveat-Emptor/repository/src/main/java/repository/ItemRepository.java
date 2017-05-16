package repository;

import java.util.List;

import entities.Item;
import entities.User;
import utils.exceptions.ItemException;

public interface ItemRepository {
	
	public List<Item> getAllItems() throws ItemException;
	public List<Item> getAllItemsForUser(User owner) throws ItemException;
	public void updateItem(Item item) throws ItemException;
	public void addItem(Item item) throws ItemException;
	
}
