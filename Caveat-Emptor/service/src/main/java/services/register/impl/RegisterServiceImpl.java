package services.register.impl;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import entities.User;
import entities.UserRegistration;
import model.RegistrationDto;
import model.UserDto;
import repository.UserRepository;
import services.register.RegisterService;
import services.register.email.ConfirmationEmail;
import services.util.Utils;
import utils.UserStateEnum;

@Stateless
@Remote(RegisterService.class)
public class RegisterServiceImpl implements RegisterService {

	@EJB
	UserRepository user;

	@Override
	public UserDto insertNewUser(RegistrationDto registration) {

		UserRegistration userRegistration = Utils.getRegistrationFromDto(registration);
		long userId = user.insertNewUser(userRegistration);
		User newUser = user.getUserById(userId);

		return Utils.getUserFromEntity(newUser);
	}

	@Override
	public boolean checkExistingUser(String userName) {

		return user.checkExistingUser(userName);
	}

	@Override
	public boolean checkExistingEmail(String email) {

		return user.checkExistingEmail(email);
	}

	@Override
	public boolean enableUser(Long userId, String code) {

		RegistrationDto registration = getRegistrationById(userId);
		
		if(registration.getId() == -1)
		{
			return false;
		}
		
		if ((registration.getUser().getId() == userId) && (registration.getValidationCode().equals(code))) {
			
			UserDto userDto = registration.getUser();
			userDto.setState(UserStateEnum.ENABLED.getState());
			
			User userEntity = (Utils.getUserFromDto(userDto));
			user.updateUser(userEntity);
			
			UserRegistration userReg = Utils.getRegistrationFromDto(registration);
			userReg.setId(registration.getId());
			
			user.deleteRegistration(userReg);
			return true;
		}

		return false;

	}

	public RegistrationDto getRegistrationById(long userId) {

		RegistrationDto registrationDto = new RegistrationDto();

		UserRegistration registration = user.getRegistrationByUserId(userId);
		if (registration.getId() != -1) {
			registrationDto = Utils.getRegistrationFromEntity(registration);
		} else {
			registrationDto.setId((long) -1);
		}

		return registrationDto;

	}

	@Override
	public void createNewUser(RegistrationDto registration) {

		registration.setUser(insertNewUser(registration));
		ConfirmationEmail.sendEmail(registration);

	}

}