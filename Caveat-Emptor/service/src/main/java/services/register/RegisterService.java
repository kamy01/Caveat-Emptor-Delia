package services.register;

import model.RegistrationDto;
import model.UserDto;

public interface RegisterService {
	
	public UserDto insertNewUser(RegistrationDto registration);
	
	public boolean checkExistingUser(String userName);
	
	public boolean checkExistingEmail(String email);

	public boolean enableUser(Long userId, String code);

	public void createNewUser(RegistrationDto registration);
	
}
