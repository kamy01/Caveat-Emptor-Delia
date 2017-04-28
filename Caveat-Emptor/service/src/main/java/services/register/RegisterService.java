package services.register;

import model.RegistrationDto;
import model.UserDto;
import utils.exceptions.UserException;

public interface RegisterService {

	public UserDto insertNewUser(RegistrationDto registration) throws UserException;

	public boolean checkExistingUser(String userName);

	public boolean checkExistingEmail(String email);

	public boolean enableUser(Long userId, String code) throws UserException;

	public void createNewUser(RegistrationDto registration) throws UserException;

}
