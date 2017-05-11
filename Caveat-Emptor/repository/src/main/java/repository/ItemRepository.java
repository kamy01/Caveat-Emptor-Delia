package repository;

import java.util.List;

import entities.Item;
import utils.exceptions.ItemException;

public interface ItemRepository {
	
	public List<Item> getAllItems() throws ItemException;
	
}
