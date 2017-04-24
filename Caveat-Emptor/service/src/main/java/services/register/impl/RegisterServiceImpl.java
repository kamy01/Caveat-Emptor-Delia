package services.register.impl;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.apache.openjpa.util.UserException;

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
	public UserDto insertNewUser(RegistrationDto registration) throws utils.UserException{

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
	public boolean enableUser(Long userId, String code) throws utils.UserException {

		RegistrationDto registration = getRegistrationById(userId);

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

	public RegistrationDto getRegistrationById(long userId) throws utils.UserException {

		RegistrationDto registrationDto = new RegistrationDto();

		UserRegistration registration = user.getRegistrationByUserId(userId);
		
		registrationDto = Utils.getRegistrationFromEntity(registration);
		
		return registrationDto;

	}

	@Override
	public void createNewUser(RegistrationDto registration) throws utils.UserException {

		registration.setUser(insertNewUser(registration));
		ConfirmationEmail.sendEmail(registration);

	}

}
