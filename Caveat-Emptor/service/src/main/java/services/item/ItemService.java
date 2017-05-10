package services.item;

import java.util.List;

import model.ItemDto;
import utils.exceptions.ItemException;

public interface ItemService {

	List<ItemDto> getAllItems() throws ItemException;

}
