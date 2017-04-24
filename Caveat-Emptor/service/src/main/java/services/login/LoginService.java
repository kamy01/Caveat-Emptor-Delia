package services.login;

import model.UserDto;

public interface LoginService {
	
	public UserDto getUser(String username);

}
