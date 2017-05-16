package services.login.impl;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import entities.User;
import model.UserDto;
import repository.UserRepository;
import services.login.LoginService;
import services.util.EntityDtoMapper;
import utils.exceptions.UserException;

@Stateless
@Remote(LoginService.class)
public class LoginServiceImpl implements LoginService {

	@EJB
	UserRepository repository;

	@Override
	public UserDto getUser(String username) throws UserException {

		User userEntity = repository.getUserByName(username);
		UserDto userDto = EntityDtoMapper.getUserFromEntity(userEntity);

		return userDto;
	}

}
