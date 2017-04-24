package services.login.impl;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import entities.User;
import model.UserDto;
import repository.UserRepository;
import services.login.LoginService;
import services.util.Utils;

@Stateless
@Remote(LoginService.class)
public class LoginServiceImpl implements LoginService {

	@EJB
	UserRepository user;

	@Override
	public UserDto getUser(String username) {

		User userEntity = user.getUserByName(username);
		UserDto userDto = Utils.getUserFromEntity(userEntity);
		
		return userDto;
	}
	
}
