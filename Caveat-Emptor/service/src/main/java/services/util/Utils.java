package services.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import entities.Category;
import entities.User;
import entities.UserAddress;
import entities.UserRegistration;
import model.AddressDto;
import model.CategoryDto;
import model.RegistrationDto;
import model.UserDto;

public class Utils {

	public static final Category NULL_CATEGORY = new Category();

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
		category.setId(categoryDto.getId()); }
		catch (Exception e)
		{
			System.out.println(e);
		}
		category.setName(categoryDto.getName());
		category.setDescription(categoryDto.getDescription());
		category.setParentId(categoryDto.getParentId());
		
		return category;
	}

	public static UserDto getUserFromEntity(User entity) {

		UserDto user = new UserDto();

		try {
			user.setId(entity.getId());
		} catch (Exception e) {
			System.out.println(e);
		}

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

}
