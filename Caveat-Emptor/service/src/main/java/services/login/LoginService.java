package services.login;

import model.UserDto;
import utils.UserException;

public interface LoginService {

	public UserDto getUser(String username) throws UserException;

}
