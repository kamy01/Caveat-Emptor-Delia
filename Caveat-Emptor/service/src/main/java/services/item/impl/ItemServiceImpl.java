package services.item.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import entities.Category;
import entities.Item;
import model.CategoryDto;
import model.ItemDto;
import model.UserDto;
import repository.ItemRepository;
import services.item.ItemService;
import services.util.EntityDtoMapper;
import utils.exceptions.ItemException;

@Stateless
@Remote(ItemService.class)
public class ItemServiceImpl implements ItemService{

	@EJB
	ItemRepository repository;
	
	@Override
	public List<ItemDto> getAllItems() throws ItemException {
		
		List<Item> entities = repository.getAllItems();
		return EntityDtoMapper.getItemFromEntity(entities);
	}

	@Override
	public List<ItemDto> getAllItemsForUser(UserDto owner) throws ItemException {

		List<Item> entities = repository.getAllItemsForUser(EntityDtoMapper.getUserFromDto(owner));
		return EntityDtoMapper.getItemFromEntity(entities);
	}

	@Override
	public void updateItem(ItemDto item) throws ItemException {

		Item entity = EntityDtoMapper.getItemFromDto(item);
		repository.updateItem(entity);
	}

	@Override
	public void addNewItem(ItemDto item) throws ItemException {

		Item entity = EntityDtoMapper.getItemFromDto(item);
		repository.addItem(entity);
		
	}

	@Override
	public List<ItemDto> getItemsForCategories(List<Long> ids) throws ItemException {
		
		return EntityDtoMapper.getItemFromEntity(repository.getItemsForCategories(ids));
		
	}

}
