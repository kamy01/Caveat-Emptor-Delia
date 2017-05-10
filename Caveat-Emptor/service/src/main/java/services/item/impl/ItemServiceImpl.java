package services.item.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import entities.Item;
import model.ItemDto;
import repository.ItemRepository;
import services.item.ItemService;
import services.util.Utils;
import utils.exceptions.ItemException;

@Stateless
@Remote(ItemService.class)
public class ItemServiceImpl implements ItemService{

	@EJB
	ItemRepository repository;
	
	@Override
	public List<ItemDto> getAllItems() throws ItemException {
		
		List<Item> entities = repository.getAllItems();
		return Utils.getItemFromEntity(entities);
	}

}
