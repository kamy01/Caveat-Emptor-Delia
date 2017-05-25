package services.item;

import java.util.List;

import model.ItemDto;
import model.UserDto;
import utils.exceptions.ItemException;

public interface ItemService {

	public List<ItemDto> getAllItems() throws ItemException;
	public List<ItemDto> getAllItemsForUser(UserDto owner) throws ItemException;
	public void updateItem(ItemDto item) throws ItemException;
	public void addNewItem(ItemDto item) throws ItemException;
	public List<ItemDto> getItemsForCategories(List<Long> ids) throws ItemException;
	
}
