package services.impl;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import model.UserDto;
import repository.UserRepository;
import services.UserService;

@Stateless
@Remote(UserService.class)
public class UserServiceImpl implements UserService {

	@EJB
	UserRepository user;

	public UserDto getUser(String username) {
		
		return user.getUserByName(username);
	}
}
