package services.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import entities.Bid;
import entities.Category;
import entities.Item;
import entities.User;
import entities.UserAddress;
import entities.UserRegistration;
import model.AddressDto;
import model.BidDto;
import model.CategoryDto;
import model.ItemDto;
import model.RegistrationDto;
import model.UserDto;

public class EntityDtoMapper {

	public static UserRegistration getRegistrationFromDto(RegistrationDto dto) {

		UserRegistration register = new UserRegistration();
		register.setUser(getUserFromDto(dto.getUser()));
		register.setValidationCode(dto.getValidationCode());

		Long time = dto.getExpirationDate().getTime();

		time += TimeUnit.HOURS.toMillis(24);

		Timestamp expDate = new Timestamp(time);

		register.setExpirationDate(expDate);

		return register;
	}

	public static RegistrationDto getRegistrationFromEntity(UserRegistration entity) {

		RegistrationDto register = new RegistrationDto();
		register.setId(entity.getId());
		register.setUser(getUserFromEntity(entity.getUser()));
		register.setValidationCode(entity.getValidationCode());

		Date time = entity.getExpirationDate();
		register.setExpirationDate(time);

		return register;
	}

	public static CategoryDto getCategoryFromEntity(Category category) {

		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setId(category.getId());
		categoryDto.setName(category.getName());
		categoryDto.setDescription(category.getDescription());
		categoryDto.setParentId(category.getParentId());

		return categoryDto;
	}

	public static Category getCategoryFromDto(CategoryDto categoryDto) {

		Category category = new Category();
		try {
			category.setId(categoryDto.getId());
		} catch (Exception e) {}
		category.setName(categoryDto.getName());
		category.setDescription(categoryDto.getDescription());
		category.setParentId(categoryDto.getParentId());

		return category;
	}

	public static UserDto getUserFromEntity(User entity) {

		UserDto user = new UserDto();

		try {
			user.setId(entity.getId());
		} catch (Exception e) {}

		user.setFirstName(entity.getFirstName());
		user.setLastName(entity.getLastName());
		user.setPassword(entity.getPassword());
		user.setEmail(entity.getEmail());
		user.setUsername(entity.getUsername());
		user.setAddress(getAddressFromEntity(entity.getAddress()));
		user.setState(entity.getState());

		return user;
	}

	public static AddressDto getAddressFromEntity(UserAddress entity) {

		AddressDto address = new AddressDto();
		address.setId(entity.getId());
		address.setCity(entity.getCity());
		address.setStreetName(entity.getStreetName());
		address.setZipcode(entity.getZipcode());

		return address;
	}

	public static User getUserFromDto(UserDto user) {

		User entity = new User();
		entity.setId(user.getId());
		entity.setFirstName(user.getFirstName());
		entity.setLastName(user.getLastName());
		entity.setUsername(user.getUsername());
		entity.setEmail(user.getEmail());
		entity.setPassword(user.getPassword());
		entity.setAddress(getAdressFromDto(user.getAddress()));
		entity.setState(user.getState());

		return entity;
	}

	public static UserAddress getAdressFromDto(AddressDto address) {

		UserAddress entity = new UserAddress();
		entity.setId(address.getId());
		entity.setStreetName(address.getStreetName());
		entity.setZipcode(address.getZipcode());
		entity.setCity(address.getCity());

		return entity;
	}

	public static List<CategoryDto> getCategoriesFromEntities(List<Category> entities) {

		List<CategoryDto> categories = new ArrayList<CategoryDto>();
		for (Category category : entities) {
			categories.add(getCategoryFromEntity(category));
		}

		return categories;
	}
	
	public static List<Category> getCategoriesFromDtos(List<CategoryDto> categories) {

		List<Category> entities = new ArrayList<Category>();
		for (CategoryDto category : categories) {
			entities.add(getCategoryFromDto(category));
		}

		return entities;
	}

	public static List<ItemDto> getItemFromEntity(List<Item> entities) {

		List<ItemDto> items = new ArrayList<ItemDto>();
		for (Item entity : entities) {
			items.add(getItemFromEntity(entity));
		}

		return items;

	}

	public static ItemDto getItemFromEntity(Item entity) {

		ItemDto item = new ItemDto();

		item.setId(entity.getId());
		item.setCategory(EntityDtoMapper.getCategoryFromEntity(entity.getCategory()));
		item.setOwner(EntityDtoMapper.getUserFromEntity(entity.getOwner()));
		item.setName(entity.getName());
		item.setDescription(entity.getDescription());
		item.setInitialPrice(entity.getInitialPrice());
		item.setOpeningDate(entity.getOpeningDate());
		item.setClosingDate(entity.getClosingDate());
		item.setStatus(entity.getStatus());
		
		try {
			User winner = entity.getWinner();
			item.setWinner(EntityDtoMapper.getUserFromEntity(winner));
		} catch (NullPointerException e) {}

		return item;
	}

	public static Item getItemFromDto(ItemDto item) {

		Item entity = new Item();

		try {
			entity.setId(item.getId());
		} catch (NullPointerException e) {}
		entity.setCategory(EntityDtoMapper.getCategoryFromDto(item.getCategory()));
		entity.setOwner(EntityDtoMapper.getUserFromDto(item.getOwner()));
		entity.setName(item.getName());
		entity.setDescription(item.getDescription());
		entity.setInitialPrice(item.getInitialPrice());

		Long openingDate = item.getOpeningDate().getTime();
		Long closingDate = item.getClosingDate().getTime();

		Timestamp openingTimestamp = new Timestamp(openingDate);
		Timestamp closingTimestamp = new Timestamp(closingDate);

		entity.setOpeningDate(openingTimestamp);
		entity.setClosingDate(closingTimestamp);

		entity.setStatus(item.getStatus());
		try {
			UserDto winner = item.getWinner();
			entity.setWinner(EntityDtoMapper.getUserFromDto(winner));
		} catch(NullPointerException e) {}
		return entity;
	}

	public static List<BidDto> getBidsFromEntity(List<Bid> entities) {

		List<BidDto> bid = new ArrayList<BidDto>();

		for (Bid entity : entities) {
			bid.add(getBidFromEntity(entity));
		}

		return bid;

	}

	public static Bid getBidFromDto(BidDto bid) {

		Bid entity = new Bid();
		try {
			bid.setId(entity.getId());
		} catch (Exception e) {}

		entity.setItem(getItemFromDto(bid.getItem()));
		entity.setPrice(bid.getPrice());
		entity.setUser(getUserFromDto(bid.getUser()));
		entity.setValid(bid.getValid());

		return entity;
	}

	public static BidDto getBidFromEntity(Bid entity) {

		BidDto bid = new BidDto();
		try {
			bid.setId(entity.getId());
		} catch (Exception e) {}

		bid.setItem(getItemFromEntity(entity.getItem()));
		bid.setPrice(entity.getPrice());
		bid.setUser(getUserFromEntity(entity.getUser()));
		bid.setValid(entity.getValid());

		return bid;
	}

}
